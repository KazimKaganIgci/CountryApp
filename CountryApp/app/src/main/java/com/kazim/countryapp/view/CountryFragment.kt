package com.kazim.countryapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.kazim.countryapp.Adapter.countryAdapter
import com.kazim.countryapp.R
import com.kazim.countryapp.viewModel.CountryViewModel
import kotlinx.android.synthetic.main.fragment_country.*

class CountryFragment : Fragment() {
    private lateinit var viewModel:CountryViewModel
    private val countryAdapter =countryAdapter(arrayListOf())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_country, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =ViewModelProviders.of(this).get(CountryViewModel::class.java)
        viewModel.refreshData()
        countryList.layoutManager =LinearLayoutManager(context)
        countryList.adapter=countryAdapter
        observeLiveData()

        swipeRefreshLayout.setOnRefreshListener {
            countryList.visibility =View.GONE
            countryError.visibility =View.GONE
            countryLoad.visibility=View.VISIBLE
            viewModel.refreshFromApi()
            swipeRefreshLayout.isRefreshing=false

        }

    }
    private fun observeLiveData(){
        viewModel.countries.observe(viewLifecycleOwner, Observer {countries->
            countries?.let {
                countryList.visibility =View.VISIBLE
                countryAdapter.updateCountryList(countries)
            }

        })
        viewModel.countryError.observe(viewLifecycleOwner, Observer {
            if (it){
                countryError.visibility =View.VISIBLE
            }
            else{
                countryError.visibility =View.GONE

            }
        })
        viewModel.countryLoading.observe(viewLifecycleOwner, Observer {
            if (it){
                countryLoad.visibility=View.VISIBLE
                countryList.visibility=View.GONE
                countryError.visibility=View.GONE

            }else{
                countryLoad.visibility=View.GONE

            }
        })
    }


}