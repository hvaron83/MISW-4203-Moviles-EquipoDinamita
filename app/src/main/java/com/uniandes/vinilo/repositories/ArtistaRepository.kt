package com.uniandes.vinilo.repositories

import android.app.Application
import com.uniandes.vinilo.models.Artista
import com.uniandes.vinilo.network.NetworkServiceAdapter

class ArtistaRepository (val application: Application){
    suspend fun refreshDataBands(): List<Artista>{
        //Determinar la fuente de datos que se va a utilizar. Si es necesario consultar la red, ejecutar el siguiente código
        return NetworkServiceAdapter.getInstance(application).getBands()
    }

    suspend fun refreshDataMusicians(): List<Artista>{
        //Determinar la fuente de datos que se va a utilizar. Si es necesario consultar la red, ejecutar el siguiente código
        return NetworkServiceAdapter.getInstance(application).getMusicians()
    }

    suspend fun refreshDataDetail(artistaId: Int): Artista{
        //Determinar la fuente de datos que se va a utilizar. Si es necesario consultar la red, ejecutar el siguiente código
        val band = NetworkServiceAdapter.getInstance(application).getBand(artistaId)
        var musician = NetworkServiceAdapter.getInstance(application).getMusician(artistaId)
        return band
    }

}