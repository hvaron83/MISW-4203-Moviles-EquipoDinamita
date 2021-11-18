package com.uniandes.vinilo.repositories

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import com.uniandes.vinilo.models.Album
import com.uniandes.vinilo.network.CacheManager
import com.uniandes.vinilo.network.NetworkServiceAdapter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.json.JSONArray

class AlbumRepository(val application: Application) {
    private val format = Json { }

    suspend fun refreshData(): List<Album>{
        var albums = getAlbums()
        return if(albums.isNullOrEmpty()){
            val cm = application.baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if( cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_WIFI && cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_MOBILE){
                emptyList()
            } else {
                Log.d("Cache decision albums", "get from network")
                albums = NetworkServiceAdapter.getInstance(application).getAlbums()
                addAlbums(albums)
                albums
            }
        } else albums
    }

    suspend fun refreshDataDetail(albumId: Int): Album {
        //Determinar la fuente de datos que se va a utilizar. Si es necesario consultar la red, ejecutar el siguiente código
        return NetworkServiceAdapter.getInstance(application).getAlbum(albumId)
    }

    private fun getAlbums(): List<Album> {
        val prefs = CacheManager.getPrefs(application.baseContext, CacheManager.ALBUMS_SPREFS)
        if(prefs.contains("albums")){
            val storedVal = prefs.getString("albums", "")
            if(!storedVal.isNullOrBlank()){
                val resp = JSONArray(storedVal)
                Log.d("deserialize", resp.toString())
                Log.d("Cache decision albums", "return ${resp.length()} elements from cache")
                return format.decodeFromString<List<Album>>(storedVal)
            }
        }
        return listOf<Album>()
    }

    private fun addAlbums(albums: List<Album>) {
        val prefs = CacheManager.getPrefs(application.baseContext, CacheManager.ALBUMS_SPREFS)
        if(!prefs.contains("albums")) {
            var store = format.encodeToString(albums)
            with(prefs.edit(), {
                putString("albums", store)
                apply()
            })
        }
    }

}