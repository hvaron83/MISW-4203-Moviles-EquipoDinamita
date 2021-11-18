package com.uniandes.vinilo.network

import android.content.Context
import com.uniandes.vinilo.models.Comment

class CacheManager(context: Context) {
    companion object{
        private var instance: CacheManager? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: CacheManager(context).also {
                    instance = it
                }
            }
    }
    private var comments: HashMap<Int, List<Comment>> = hashMapOf()
    fun addComments(albumId: Int, comment: List<Comment>){
        if (!comments.containsKey(albumId)){
            comments[albumId] = comment
        }
    }
    fun getComments(albumId: Int) : List<Comment>{
        return if (comments.containsKey(albumId)) comments[albumId]!! else listOf()
    }
}