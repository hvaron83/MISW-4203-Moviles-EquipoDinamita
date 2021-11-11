package com.uniandes.vinilo.ui.artista.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uniandes.vinilo.common.dto.Artista

class DetailArtistaViewModel : ViewModel() {

    private val artistaSelected = MutableLiveData<Artista>()


    fun setArtistaSelected(artista: Artista){
        artistaSelected.value = artista
    }

    fun getArtistaSelected(): MutableLiveData<Artista>{
        return artistaSelected
    }


}