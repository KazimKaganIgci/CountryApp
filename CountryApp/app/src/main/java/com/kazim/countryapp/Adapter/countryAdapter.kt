package com.kazim.countryapp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.kazim.countryapp.R
import com.kazim.countryapp.databinding.RecyclerRowBinding
import com.kazim.countryapp.model.Country
import com.kazim.countryapp.util.CountryClickListener
import com.kazim.countryapp.view.CountryFragmentDirections
import kotlinx.android.synthetic.main.recycler_row.view.*

class countryAdapter(val countryList: ArrayList<Country>): RecyclerView.Adapter<countryAdapter.CountryViewHolder>(),CountryClickListener{



    class CountryViewHolder(var view: RecyclerRowBinding) : RecyclerView.ViewHolder(view.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        //val view = inflater.inflate(R.layout.recycler_row,parent,false)
        val view =DataBindingUtil.inflate<RecyclerRowBinding>(inflater,R.layout.recycler_row,parent,false)
        return CountryViewHolder(view)

    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.view.country=countryList[position]
        holder.view.listener=this



        /*
        holder.view.name.text = countryList[position].countryName
        holder.view.region.text = countryList[position].countryRegion
        holder.view.imageView.downloadUrl(countryList[position].imageUrl, placeHolderProgressBar(holder.view.context))

        holder.view.setOnClickListener{
            val action =CountryFragmentDirections.actionCountryFragmentToDetailsFragment(countryList[position].uuid)

            Navigation.findNavController(it).navigate(action)

        }*/




    }


    fun updateCountryList(newCountryList: List<Country>) {
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }

    override fun onCountryClicked(v: View) {
        val action =CountryFragmentDirections.actionCountryFragmentToDetailsFragment(v.countryUuidText.text.toString().toInt())
        Navigation.findNavController(v).navigate(action)

    }


}
