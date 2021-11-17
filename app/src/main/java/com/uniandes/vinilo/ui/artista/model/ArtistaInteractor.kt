package com.uniandes.vinilo.ui.artista.model

import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.uniandes.vinilo.ViniloApplication
import com.uniandes.vinilo.common.dto.Artista
import com.uniandes.vinilo.common.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.anko.custom.async
import org.json.JSONException


class ArtistaInteractor {

    private val TAG = "ArtistaInteractor"

    fun getArtistas(callback: (MutableList<Artista>) -> Unit) {
        val artistaList = mutableListOf<Artista>()
        val url = Constants.VINILOS_URL + Constants.BANDS_PATH
        val jsonObjectRequest = JsonArrayRequest(Request.Method.GET, url, null, { response ->
            Log.i("response", response.toString())
            if (response.length() > 0) {
                //val mutableListType = object : TypeToken<MutableList<Album>>(){}.type
                for (i in 0 until response.length()) {
                    try {
                        val jsonObject = Gson().fromJson<Artista>(
                            response.get(i).toString(), Artista::class.java
                        )
                        artistaList.add(jsonObject)
                    } catch (e: JSONException) {
                        Log.e(TAG, "Error de parsing: " + e.message)
                    }
                }
                callback(artistaList)
                return@JsonArrayRequest
            }
        }, {
            it.printStackTrace()
            callback(artistaList)
        })
        ViniloApplication.networkServiceAdapter.addToRequestQueue(jsonObjectRequest)
    }

    /*
    fun getArtistas(callback: (MutableList<Artista>) -> Unit) {

        var m = fun (callback: (MutableList<Artista>) -> Unit) {
            val artistaList = mutableListOf<Artista>()
            val url = Constants.VINILOS_URL + Constants.MUSICIANS_PATH
            val jsonObjectRequest = JsonArrayRequest(Request.Method.GET, url, null, { response ->
                Log.i("response", response.toString())
                if (response.length() > 0) {
                    //val mutableListType = object : TypeToken<MutableList<Album>>(){}.type
                    for (i in 0 until response.length()) {
                        try {
                            val jsonObject = Gson().fromJson<Artista>(
                                response.get(i).toString(), Artista::class.java
                            )
                            artistaList.add(jsonObject)
                        } catch (e: JSONException) {
                            Log.e(TAG, "Error de parsing: " + e.message)
                        }
                    }
                    callback(artistaList)
                    return@JsonArrayRequest
                }
            }, {
                it.printStackTrace()
                callback(artistaList)
            })
            ViniloApplication.networkServiceAdapter.addToRequestQueue(jsonObjectRequest)
        }

        var b = fun (callback: (MutableList<Artista>) -> Unit) {
            val bandasList = mutableListOf<Artista>()
            val url = Constants.VINILOS_URL + Constants.BANDS_PATH
            val jsonObjectRequest = JsonArrayRequest(Request.Method.GET, url, null, { response ->
                Log.i("response", response.toString())
                if (response.length() > 0) {
                    for (i in 0 until response.length()) {
                        try {
                            val jsonObject = Gson().fromJson<Artista>(
                                response.get(i).toString(), Artista::class.java
                            )
                            bandasList.add(jsonObject)
                        } catch (e: JSONException) {
                            Log.e(TAG, "Error de parsing: " + e.message)
                        }
                    }
                    callback(bandasList)
                    return@JsonArrayRequest
                }
            }, {
                it.printStackTrace()
                callback(bandasList)
            })
            ViniloApplication.networkServiceAdapter.addToRequestQueue(jsonObjectRequest)
        }
    }*/
}