package com.uniandes.vinilo.network

import android.content.Context
import android.content.SharedPreferences
import com.uniandes.vinilo.models.Comment

class CacheManager(context: Context) {
    companion object{
        var instance: CacheManager? = null
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
    /*private var comments: HashMap<Int, List<Comment>> = hashMapOf()
    fun addComments(albumId: Int, comment: List<Comment>){
        if (!comments.containsKey(albumId)){
            comments[albumId] = comment
        }
    }
    fun getComments(albumId: Int) : List<Comment>{
        return if (comments.containsKey(albumId)) comments[albumId]!! else listOf<Comment>()
    }*/

}