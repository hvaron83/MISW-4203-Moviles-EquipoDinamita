package com.uniandes.vinilo.ui.artista.model

import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.uniandes.vinilo.ViniloApplication
import com.uniandes.vinilo.common.dto.Artista
import com.uniandes.vinilo.common.utils.Constants
import org.json.JSONException


class ArtistaInteractor {

    private val TAG = "ArtistaInteractor"

    fun getMusicians(callback: (MutableList<Artista>) -> Unit){

        val artistaList = mutableListOf<Artista>()

        val items : List<String> = listOf(Constants.MUSICIANS_PATH, Constants.BANDS_PATH)

        // for (item in items) {

            var url = Constants.VINILOS_URL + Constants.BANDS_PATH

            var jsonObjectRequest = JsonArrayRequest(Request.Method.GET, url, null, { response ->
                Log.i("response", response.toString())

                // https://stackoverflow.com/questions/60263584/calling-multiple-api-requests-and-waiting-for-response-using-kotlin-coroutines
                
                if (response.length() > 0){
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
            },{
                it.printStackTrace()
                callback(artistaList)
            })


            ViniloApplication.networkServiceAdapter.addToRequestQueue(jsonObjectRequest)
       // }
    }
}