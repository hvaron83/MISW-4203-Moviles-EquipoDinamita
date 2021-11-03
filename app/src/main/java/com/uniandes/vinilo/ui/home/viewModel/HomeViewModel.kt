package com.uniandes.vinilo.ui.home.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uniandes.vinilo.common.dto.Album
import com.uniandes.vinilo.common.utils.Constants
import com.uniandes.vinilo.ui.home.model.HomeInteractor

class HomeViewModel : ViewModel() {

    private var albumList: MutableList<Album>
    private var interactor: HomeInteractor

    init {
        albumList = mutableListOf()
        interactor = HomeInteractor()
    }

    private val showProgress: MutableLiveData<Boolean> = MutableLiveData()

    private val albumes: MutableLiveData<MutableList<Album>> by lazy {
        MutableLiveData<MutableList<Album>>().also {
            loadAlbumes()
        }
    }

    fun getAlbumes(): LiveData<MutableList<Album>>{
        return albumes
    }

    fun isShowProgress():LiveData<Boolean>{
        return showProgress
    }

    private fun loadAlbumes(){
        showProgress.value = Constants.SHOW
        interactor.getAlbums {
            showProgress.value = Constants.HIDE
            albumes.value = it
            albumList = it
        }
    }


}