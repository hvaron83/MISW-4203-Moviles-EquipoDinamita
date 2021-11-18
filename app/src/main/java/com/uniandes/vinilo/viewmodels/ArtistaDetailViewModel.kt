package com.uniandes.vinilo.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.uniandes.vinilo.models.Artista
import com.uniandes.vinilo.repositories.ArtistaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArtistaDetailViewModel(application: Application, artistaId: Int) :  AndroidViewModel(application) {

    private val artistasRepository = ArtistaRepository(application)

    private val _artista = MutableLiveData<Artista>()

    val artista: LiveData<Artista>
        get() = _artista

    private var _eventNetworkError = MutableLiveData(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    val id:Int = artistaId

    init {
        refreshDataFromNetwork()
    }

    private fun refreshDataFromNetwork() {
        try {
            viewModelScope.launch(Dispatchers.Default){
                withContext(Dispatchers.IO){
                    val data = artistasRepository.refreshDataDetail(id)
                    _artista.postValue(data)
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

    class Factory(val app: Application, private val artistaId: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ArtistaDetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ArtistaDetailViewModel(app, artistaId) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
