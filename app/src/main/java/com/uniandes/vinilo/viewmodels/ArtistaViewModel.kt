package com.uniandes.vinilo.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.uniandes.vinilo.models.Artista
import com.uniandes.vinilo.repositories.ArtistaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArtistaViewModel(application: Application) :  AndroidViewModel(application) {

    private val artistasRepository = ArtistaRepository(application)

    private val _artistas = MutableLiveData<List<Artista>>()

    val artistas: LiveData<List<Artista>>
        get() = _artistas

    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    init {
        refreshDataFromNetwork()
    }

    private fun refreshDataFromNetwork() {
        try {
            viewModelScope.launch(Dispatchers.Default){
                withContext(Dispatchers.IO){
                    var musicians = artistasRepository.refreshDataMusicians()
                    var bands = artistasRepository.refreshDataBands()
                    _artistas.postValue(musicians + bands)
                }
                _eventNetworkError.postValue(false)
                _isNetworkErrorShown.postValue(false)
            }
        }
        catch (e:Exception){
            _eventNetworkError.value = true
        }
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ArtistaViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ArtistaViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
