package com.kazim.countryapp.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.kazim.countryapp.model.Country
import com.kazim.countryapp.service.CountryDatabase
import kotlinx.coroutines.launch

class DetailsViewModel(application: Application):BaseViewModel(application) {
    val countryLiveData =MutableLiveData<Country>()

    fun getDataRoom(uuid:Int){
        launch {
            val dao =CountryDatabase(getApplication()).countryDao()
            val country=dao.getCountry(uuid)
            countryLiveData.value=country
        }

    }

}