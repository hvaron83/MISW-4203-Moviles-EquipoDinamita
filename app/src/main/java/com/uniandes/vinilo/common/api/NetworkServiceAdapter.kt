package com.uniandes.vinilo.common.api

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class NetworkServiceAdapter constructor(context: Context) {

    companion object{
        @Volatile
        private var INSTANCE: NetworkServiceAdapter? = null

        fun getInstance(context: Context) = INSTANCE?: synchronized(this){
            INSTANCE ?: NetworkServiceAdapter(context).also { INSTANCE = it }
        }
    }

    val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }

    fun <T> addToRequestQueue(req:Request<T>){
        requestQueue.add(req)
    }
}