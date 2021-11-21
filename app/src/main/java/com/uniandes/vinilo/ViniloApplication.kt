package com.uniandes.vinilo

import android.app.Application
import com.uniandes.vinilo.common.api.NetworkServiceAdapter

class ViniloApplication : Application() {
    companion object{
        lateinit var networkServiceAdapter: NetworkServiceAdapter
    }

    override fun onCreate() {
        super.onCreate()
        //Volley
        networkServiceAdapter = NetworkServiceAdapter.getInstance(this)
    }
}