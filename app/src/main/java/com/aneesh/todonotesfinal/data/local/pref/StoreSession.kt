package com.aneesh.todonotesfinal.data.local.pref

import android.content.Context
import android.content.SharedPreferences

object StoreSession {

    var sharedPreferences: SharedPreferences? = null

    fun init(context : Context){
        sharedPreferences = context.applicationContext.getSharedPreferences(PrefConstant.SHARED_PREFERENCES, Context.MODE_PRIVATE)
    }

    fun readString(key : String) : String{
        return sharedPreferences?.getString(key, "").toString()
    }

    fun readBoolean(key : String) : Boolean?{
        return sharedPreferences?.getBoolean(key, false)
    }

    fun write(key : String, value : String){
        val editor = sharedPreferences?.edit()
        editor?.putString(key, value)
        editor?.apply()
    }

    fun write(key : String, value : Boolean){
        val editor = sharedPreferences?.edit()
        editor?.putBoolean(key, value)
        editor?.apply()
    }

}