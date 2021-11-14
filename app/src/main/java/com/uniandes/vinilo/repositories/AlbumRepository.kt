package com.uniandes.vinilo.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.uniandes.vinilo.models.Album
import com.uniandes.vinilo.network.NetworkServiceAdapter

class AlbumRepository (val application: Application){
    suspend fun refreshData(): List<Album>{
        //Determinar la fuente de datos que se va a utilizar. Si es necesario consultar la red, ejecutar el siguiente código
        return NetworkServiceAdapter.getInstance(application).getAlbums()
    }

    suspend fun refreshDataDetail(albumId: Int): Album{
        //Determinar la fuente de datos que se va a utilizar. Si es necesario consultar la red, ejecutar el siguiente código
        return NetworkServiceAdapter.getInstance(application).getAlbum(albumId)
    }

}