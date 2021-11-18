package com.uniandes.vinilo.repositories

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import com.uniandes.vinilo.models.Comment
import com.uniandes.vinilo.network.CacheManager
import com.uniandes.vinilo.network.NetworkServiceAdapter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.json.JSONArray

class CommentsRepository (val application: Application){
    private val format = Json {  }

    @SuppressLint("ServiceCast")
    suspend fun refreshData(albumId: Int): List<Comment>{
        var comments = getComments(albumId)
        return if(comments.isNullOrEmpty()){
            val cm = application.baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if( cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_WIFI && cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_MOBILE){
                emptyList()
            } else {
                Log.d("Cache decision comments", "get from network")
                comments = NetworkServiceAdapter.getInstance(application).getComments(albumId)
                addComments(albumId, comments)
                comments
            }
        } else comments
    }

    private fun getComments(albumId:Int): List<Comment>{
        val prefs = CacheManager.getPrefs(application.baseContext, CacheManager.ALBUMS_SPREFS)
        if(prefs.contains(albumId.toString())){
            val storedVal = prefs.getString(albumId.toString(), "")
            if(!storedVal.isNullOrBlank()){
                val resp = JSONArray(storedVal)
                Log.d("deserialize", resp.toString())
                Log.d("Cache decision comments", "return ${resp.length()} elements from cache")
                return format.decodeFromString<List<Comment>>(storedVal)
            }
        }
        return listOf<Comment>()
    }
    private fun addComments(albumId:Int, comments: List<Comment>){
        val prefs = CacheManager.getPrefs(application.baseContext, CacheManager.ALBUMS_SPREFS)
        if(!prefs.contains(albumId.toString())){
            var store = format.encodeToString(comments)
            with(prefs.edit(),{
                putString(albumId.toString(), store)
                apply()
            })
        }
    }


}