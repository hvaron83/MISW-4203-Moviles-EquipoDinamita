package com.uniandes.vinilo.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.uniandes.vinilo.models.Album
import com.uniandes.vinilo.models.Comment
import com.uniandes.vinilo.network.NetworkServiceAdapter

class CommentsRepository (val application: Application){
    suspend fun refreshData(albumId: Int): List<Comment>{
        //Determinar la fuente de datos que se va a utilizar. Si es necesario consultar la red, ejecutar el siguiente código
        return NetworkServiceAdapter.getInstance(application).getComments(albumId)
    }

}