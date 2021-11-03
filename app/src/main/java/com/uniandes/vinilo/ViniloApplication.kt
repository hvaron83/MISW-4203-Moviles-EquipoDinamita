package com.uniandes.vinilo

import android.app.Application
import com.uniandes.vinilo.common.api.AlbumAPI

class ViniloApplication : Application() {
    companion object{
        lateinit var albumApi: AlbumAPI
    }

    override fun onCreate() {
        super.onCreate()
        //Volley
        albumApi = AlbumAPI.getInstance(this)
    }
}