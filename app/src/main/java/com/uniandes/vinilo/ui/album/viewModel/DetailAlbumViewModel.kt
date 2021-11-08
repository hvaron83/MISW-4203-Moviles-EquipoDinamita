package com.uniandes.vinilo.ui.album.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uniandes.vinilo.common.dto.Album

class DetailAlbumViewModel : ViewModel() {

    private val albumSelected = MutableLiveData<Album>()


    fun setAlbumSelected(album: Album){
        albumSelected.value = album
    }

    fun getAlbumSelected(): MutableLiveData<Album>{
        return albumSelected
    }


}