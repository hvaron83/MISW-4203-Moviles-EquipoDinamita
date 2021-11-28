package com.uniandes.vinilo.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.uniandes.vinilo.models.Album
import com.uniandes.vinilo.repositories.AlbumRepository

class AlbumRegisterViewModel(application: Application) : AndroidViewModel(application){
    private val albumsRepository = AlbumRepository(application)


    private var _eventNetworkError = MutableLiveData(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown


    suspend fun saveAlbum(album: Album){
        albumsRepository.guardarAlbum(album)
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AlbumDetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AlbumRegisterViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
