package com.uniandes.vinilo.repositories

import android.app.Application
import android.util.Log
import com.uniandes.vinilo.models.Artista
import com.uniandes.vinilo.network.CacheManager
import com.uniandes.vinilo.network.NetworkServiceAdapter

class ArtistaRepository (val application: Application){
    suspend fun refreshDataBands(): List<Artista>{
        var potentialResp = CacheManager.getInstance(application.applicationContext).getBands()
        if(potentialResp.isEmpty()){
            Log.d("Cache decision", "get from network")
            var bands = NetworkServiceAdapter.getInstance(application).getBands()
            CacheManager.getInstance(application.applicationContext).addBands(bands)
            return bands
        }
        else{
            Log.d("Cache decision", "return ${potentialResp.size} elements from cache")
            return potentialResp
        }
    }

    suspend fun refreshDataMusicians(): List<Artista>{
        var potentialResp = CacheManager.getInstance(application.applicationContext).getMusicians()
        if(potentialResp.isEmpty()){
            Log.d("Cache decision", "get from network")
            var musicians = NetworkServiceAdapter.getInstance(application).getMusicians()
            CacheManager.getInstance(application.applicationContext).addMusicians(musicians)
            return musicians
        }
        else{
            Log.d("Cache decision", "return ${potentialResp.size} elements from cache")
            return potentialResp
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