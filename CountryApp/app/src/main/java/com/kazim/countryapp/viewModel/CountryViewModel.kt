package com.kazim.countryapp.viewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.kazim.countryapp.model.Country
import com.kazim.countryapp.service.CountryAPIService
import com.kazim.countryapp.service.CountryDatabase
import com.kazim.countryapp.util.CustomSharredPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class CountryViewModel(application: Application):BaseViewModel(application){
    private val countryAPIService=CountryAPIService()
    private val disposable =CompositeDisposable()
    private var customPreferences =CustomSharredPreferences(getApplication())
    private var refreshTime = 10*60*1000*1000*1000L

    val countries =MutableLiveData<List<Country>>()
    val countryError =MutableLiveData<Boolean>()
    val countryLoading =MutableLiveData<Boolean>()

    fun refreshData(){
        val updateTime=customPreferences.getTime()
        if(updateTime !=null && updateTime !=0L &&System.nanoTime()-updateTime<refreshTime){
            getDataFromSQLite()
        }
        else{
           getDataFromApi()
        }

    }
    fun refreshFromApi(){
        getDataFromApi()


    }
    private fun getDataFromSQLite(){
        countryLoading.value=true
        launch {
            val countries =CountryDatabase(getApplication()).countryDao().getAllCountries()
            showCountries(countries)
            Toast.makeText(getApplication(),"Countries From SqLite",Toast.LENGTH_LONG).show()

        }

    }
    private  fun getDataFromApi(){
        countryLoading.value =true
        disposable.add(
            countryAPIService.getData().
                    subscribeOn(Schedulers.newThread()).
                    observeOn(AndroidSchedulers.mainThread()).
                    subscribeWith(object : DisposableSingleObserver<List<Country>>(){
                        override fun onSuccess(t: List<Country>) {
                            storeInSQLite(t)
                            Toast.makeText(getApplication(),"Countries From API",Toast.LENGTH_LONG).show()

                        }

                        override fun onError(e: Throwable) {
                            countryLoading.value =false
                            countryError.value=true
                            e.printStackTrace()

                        }


                    })

        )

    }
    private fun showCountries(countryList:List<Country>){
        countries.value =countryList
        countryError.value =false
        countryLoading.value=false
    }
    private fun storeInSQLite(list :List<Country>){
        launch {
            val dao =CountryDatabase(getApplication()).countryDao()
            dao.deleteAllCountries()
            val listLong = dao.insertAll(*list.toTypedArray())
            var i =0
            while (i<list.size){
                list[i].uuid =listLong[i].toInt()
                i +=1
                //room da uuid ler tanımsızdı bu sayede uuid değerlerini vermiş olduk
            }
            showCountries(list)
        }
        customPreferences.saveTime(System.nanoTime())


    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}