package com.kazim.countryapp.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class CustomSharredPreferences {

    companion object{
        private val PREFERENCES_TIME ="preference_time"
        private var sharredPreferences:SharedPreferences ?=null
        @Volatile private var instance:CustomSharredPreferences?=null

        private val lock =Any()
        operator fun invoke(context:Context):CustomSharredPreferences = instance ?: synchronized(lock){
            instance ?: makeCustomSharredPreferences(context).also {
                instance=it
            }
        }
        private fun makeCustomSharredPreferences(context: Context):CustomSharredPreferences{
            sharredPreferences =PreferenceManager.getDefaultSharedPreferences(context)
            return CustomSharredPreferences()
        }

    }

    fun saveTime(time:Long){
        sharredPreferences?.edit(commit = true){
            putLong(PREFERENCES_TIME,time)
        }

    }
    fun getTime()= sharredPreferences?.getLong(PREFERENCES_TIME,0)

}