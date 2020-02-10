package com.dalamsyah.accons.lib

import android.app.Activity
import android.content.Context

class DASession(activity: Activity){

    val sharedPref = activity?.getSharedPreferences("keyapp", Context.MODE_PRIVATE)

    fun setValue(k: String, v: String){
        with (sharedPref.edit()) {
            putString(k, v)
            commit()
        }
    }

    fun get(s: String) : String?{
        return sharedPref.getString(s, "")
    }


}