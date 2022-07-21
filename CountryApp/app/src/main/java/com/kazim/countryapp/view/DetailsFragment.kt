package com.kazim.countryapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.kazim.countryapp.R
import com.kazim.countryapp.databinding.FragmentDetailsBinding
import com.kazim.countryapp.util.downloadUrl
import com.kazim.countryapp.util.placeHolderProgressBar
import com.kazim.countryapp.viewModel.DetailsViewModel
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : Fragment() {
    private var data= 0
    private lateinit var viewModel: DetailsViewModel
    private lateinit var databinding:FragmentDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        databinding=DataBindingUtil.inflate(inflater,R.layout.fragment_details,container,false)
        return databinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            data =DetailsFragmentArgs.fromBundle(it).data
        }
        viewModel =ViewModelProviders.of(this).get(DetailsViewModel::class.java)
        viewModel.getDataRoom(data)

        observeLiveData()
    }
    private fun observeLiveData(){
        viewModel.countryLiveData.observe(viewLifecycleOwner, Observer {country->
            country?.let {
               /* countryName.text =country.countryName
                countryCapital.text=country.countryCapital
                countryCurrency.text=country.countryCurrrency
                countryLanguage.text=country.countryLanguage
                countryRegion.text=country.countryRegion
                context?.let {
                    countryImage.downloadUrl(country.imageUrl, placeHolderProgressBar(it))
                }
           }
                 */
                databinding.country=country




            }
        })

    }


}