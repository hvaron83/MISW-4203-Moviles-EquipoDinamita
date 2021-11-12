package com.uniandes.vinilo.ui.artista.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uniandes.vinilo.common.dto.Artista
import com.uniandes.vinilo.common.utils.Constants
import com.uniandes.vinilo.ui.artista.model.ArtistaInteractor

class ArtistaViewModel : ViewModel() {

    private var artistaList: MutableList<Artista>
    private var interactor: ArtistaInteractor

    init {
        artistaList = mutableListOf()
        interactor = ArtistaInteractor()
    }

    private val showProgress: MutableLiveData<Boolean> = MutableLiveData()

    private val artistas: MutableLiveData<MutableList<Artista>> by lazy {
        MutableLiveData<MutableList<Artista>>().also {
            loadArtistas()
        }
    }

    fun getArtistas(): LiveData<MutableList<Artista>>{
        return artistas
    }

    fun isShowProgress():LiveData<Boolean>{
        return showProgress
    }

    private fun loadArtistas(){
        showProgress.value = Constants.SHOW
        interactor.getMusicians {
            showProgress.value = Constants.HIDE
            artistas.value = it
            artistaList = it
        }
    }


}