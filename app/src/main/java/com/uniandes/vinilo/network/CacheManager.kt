package com.uniandes.vinilo.network

import android.content.Context
import android.content.SharedPreferences

class CacheManager(context: Context) {
    companion object{
        var instance: CacheManager? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: CacheManager(context).also {
                    instance = it
                }
            }
        const val APP_SPREFS = "com.uniandes.vinilo.app"
        const val ALBUMS_SPREFS = "com.uniandes.vinilo.albums"
        fun getPrefs(context: Context, name:String): SharedPreferences {
            return context.getSharedPreferences(name,
                Context.MODE_PRIVATE
            )
        }
    }

}