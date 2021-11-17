package com.uniandes.vinilo.ui.album.model

import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.uniandes.vinilo.ViniloApplication
import com.uniandes.vinilo.common.dto.Album
import com.uniandes.vinilo.common.utils.Constants
import org.json.JSONException


class AlbumInteractor {

    private val TAG = "AlbumInteractor"

    fun getAlbums(callback: (MutableList<Album>) -> Unit){
        var url = Constants.VINILOS_URL + Constants.ALBUMS_PATH
        var albumList = mutableListOf<Album>()

        val jsonObjectRequest = JsonArrayRequest(Request.Method.GET, url, null, { response ->
            Log.i("response", response.toString())

            if (response.length() > 0){
                //val mutableListType = object : TypeToken<MutableList<Album>>(){}.type
                for (i in 0 until response.length()) {
                    try {
                        val jsonObject = Gson().fromJson<Album>(
                            response.get(i).toString(), Album::class.java)
                        albumList.add(jsonObject)
                    } catch (e: JSONException) {
                        Log.e(TAG, "Error de parsing: " + e.message)
                    }
                }

                callback(albumList)
                return@JsonArrayRequest
            }


            callback(albumList)

        },{
            it.printStackTrace()
            callback(albumList)
        })

        ViniloApplication.networkServiceAdapter.addToRequestQueue(jsonObjectRequest)
    }
}