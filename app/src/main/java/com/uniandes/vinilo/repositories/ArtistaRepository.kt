package com.uniandes.vinilo.repositories

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import com.uniandes.vinilo.models.Artista
import com.uniandes.vinilo.network.CacheManager
import com.uniandes.vinilo.network.NetworkServiceAdapter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.json.JSONArray

class ArtistaRepository(val application: Application) {
    private val format = Json { }

    suspend fun refreshDataBands(): List<Artista>{
        var bands = getBands()
        return if(bands.isNullOrEmpty()){
            val cm = application.baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if( cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_WIFI && cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_MOBILE){
                emptyList()
            } else {
                Log.d("Cache decision bands", "get from network")
                bands = NetworkServiceAdapter.getInstance(application).getBands()
                addBands(bands)
                bands
            }
        } else bands
    }

    private fun getBands(): List<Artista> {
        val prefs = CacheManager.getPrefs(application.baseContext, CacheManager.BANDS_SPREFS)
        if(prefs.contains("bands")){
            val storedVal = prefs.getString("bands", "")
            if(!storedVal.isNullOrBlank()){
                val resp = JSONArray(storedVal)
                Log.d("deserialize", resp.toString())
                Log.d("Cache decision bands", "return ${resp.length()} elements from cache")
                return format.decodeFromString<List<Artista>>(storedVal)
            }
        }
        return listOf<Artista>()
    }

    private fun addBands(bands: List<Artista>) {
        val prefs = CacheManager.getPrefs(application.baseContext, CacheManager.BANDS_SPREFS)
        if(!prefs.contains("bands")) {
            var store = format.encodeToString(bands)
            with(prefs.edit(), {
                putString("bands", store)
                apply()
            })
        }
    }

    suspend fun refreshDataMusicians(): List<Artista>{
        var musicians = getMusicians()
        return if(musicians.isNullOrEmpty()){
            val cm = application.baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if( cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_WIFI && cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_MOBILE){
                emptyList()
            } else {
                Log.d("Cache decision music", "get from network")
                musicians = NetworkServiceAdapter.getInstance(application).getMusicians()
                addMusicians(musicians)
                musicians
            }
        } else musicians
    }
    private fun getMusicians(): List<Artista> {
        val prefs = CacheManager.getPrefs(application.baseContext, CacheManager.MUSICIANS_SPREFS)
        if(prefs.contains("musicians")){
            val storedVal = prefs.getString("musicians", "")
            if(!storedVal.isNullOrBlank()){
                val resp = JSONArray(storedVal)
                Log.d("deserialize", resp.toString())
                Log.d("Cache decision music", "return ${resp.length()} elements from cache")
                return format.decodeFromString<List<Artista>>(storedVal)
            }
        }
        return listOf<Artista>()
    }
    private fun addMusicians(musicians: List<Artista>) {
        val prefs = CacheManager.getPrefs(application.baseContext, CacheManager.MUSICIANS_SPREFS)
        if(!prefs.contains("musicians")) {
            var store = format.encodeToString(musicians)
            with(prefs.edit(), {
                putString("musicians", store)
                apply()
            })
        }
    }

    suspend fun refreshDataDetailBand(artistaId: Int): Artista{
        //Determinar la fuente de datos que se va a utilizar. Si es necesario consultar la red, ejecutar el siguiente código
        val band = NetworkServiceAdapter.getInstance(application).getBand(artistaId)
        return band
    }
    suspend fun refreshDataDetailMusician(artistaId: Int): Artista{
        //Determinar la fuente de datos que se va a utilizar. Si es necesario consultar la red, ejecutar el siguiente código
        val band = NetworkServiceAdapter.getInstance(application).getMusician(artistaId)
        return band
    }

}