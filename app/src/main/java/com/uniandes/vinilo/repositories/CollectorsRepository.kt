package com.uniandes.vinilo.repositories

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import com.uniandes.vinilo.models.Album
import com.uniandes.vinilo.models.Collector
import com.uniandes.vinilo.network.CacheManager
import com.uniandes.vinilo.network.NetworkServiceAdapter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.json.JSONArray

class CollectorsRepository (val application: Application){
    private val format = Json {  }

    suspend fun refreshData(): List<Collector>{
        var collectors = getCollectors()
        return if(collectors.isNullOrEmpty()){
            val cm = application.baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if( cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_WIFI && cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_MOBILE){
                emptyList()
            } else {
                Log.d("Cache decision collect", "get from network")
                collectors = NetworkServiceAdapter.getInstance(application).getCollectors()
                addCollectors(collectors)
                collectors
            }
        } else collectors
    }

    suspend fun refreshDataDetail(collectorId: Int): Collector {
        //Determinar la fuente de datos que se va a utilizar. Si es necesario consultar la red, ejecutar el siguiente código
        return NetworkServiceAdapter.getInstance(application).getCollector(collectorId)
    }

    private fun getCollectors(): List<Collector> {
        val prefs = CacheManager.getPrefs(application.baseContext, CacheManager.ALBUMS_SPREFS)
        if(prefs.contains("collectors")){
            val storedVal = prefs.getString("collectors", "")
            if(!storedVal.isNullOrBlank()){
                val resp = JSONArray(storedVal)
                Log.d("deserialize", resp.toString())
                Log.d("Cache decision collecto", "return ${resp.length()} elements from cache")
                return format.decodeFromString(storedVal)
            }
        }
        return listOf()
    }

    private fun addCollectors(collectors: List<Collector>) {
        val prefs = CacheManager.getPrefs(application.baseContext, CacheManager.ALBUMS_SPREFS)
        if(!prefs.contains("collectors")) {
            val store = format.encodeToString(collectors)
            with(prefs.edit(), {
                putString("collectors", store)
                apply()
            })
        }
    }

}