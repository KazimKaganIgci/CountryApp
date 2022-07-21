package com.kazim.countryapp.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.kazim.countryapp.R

fun ImageView.downloadUrl(url:String?,progressDrawable: CircularProgressDrawable){
    val option= RequestOptions()
        .placeholder(progressDrawable)
        .error(R.mipmap.ic_launcher_round)

    Glide.with(context)
        .setDefaultRequestOptions(option)
        .load(url)
        .into(this)

}
fun placeHolderProgressBar(context:Context):CircularProgressDrawable{
    return CircularProgressDrawable(context).apply {
        strokeWidth =8f
        centerRadius=50f
        start()
    }

}
@BindingAdapter("android:downloadUrl")
fun downloadImage(imageView: ImageView,url: String?){
    imageView.downloadUrl(url, placeHolderProgressBar(imageView.context))
}