package com.uniandes.vinilo.ui.artista

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.uniandes.vinilo.MainActivity
import com.uniandes.vinilo.R
import com.uniandes.vinilo.common.dto.Artista
// import com.uniandes.vinilo.databinding.FragmentDetailArtistaBinding
// import com.uniandes.vinilo.ui.album.viewModel.DetailArtistaViewModel
import java.text.SimpleDateFormat
import java.util.*

class DetailArtistaFragment : Fragment() {

    // private lateinit var mBinding: FragmentDetailArtistaBinding
    //mvvm
    // private lateinit var mDetailAlbumViewModel: DetailArtistaViewModel

    private var mActivity: MainActivity? = null
    private lateinit var mArtista: Artista

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}