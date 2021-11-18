package com.uniandes.vinilo.network

import android.content.Context
import com.uniandes.vinilo.models.Artista
import com.uniandes.vinilo.models.Comment
import android.content.SharedPreferences

class CacheManager(context: Context) {
    companion object{
        private var instance: CacheManager? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: CacheManager(context).also {
                    instance = it
                }
            }
        const val APP_SPREFS = "com.uniandes.vinilo.app"
        const val ALBUMS_SPREFS = "com.uniandes.vinilo.albums"
        fun getPrefs(context: Context, name:String): SharedPreferences {
            return context.getSharedPreferences(name,
                Context.MODE_PRIVATE
            )
        }
    }

    fun getComments(albumId: Int) : List<Comment>{
        return if (comments.containsKey(albumId)) comments[albumId]!! else listOf()
    }

    private var c_bands: HashMap<Int, List<Artista>> = hashMapOf()
    fun addBands(bands: List<Artista>){
        c_bands[1] = bands
    }
    fun getBands() : List<Artista>{
        return if (c_bands.containsKey(1)) c_bands[1]!! else listOf()
    }

    private var c_musicians: HashMap<Int, List<Artista>> = hashMapOf()
    fun addMusicians(musicians: List<Artista>){
        c_musicians[1] = musicians
    }
    fun getMusicians() : List<Artista>{
        return if (c_musicians.containsKey(1)) c_musicians[1]!! else listOf()
    }
}