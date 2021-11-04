package com.uniandes.vinilo.ui.home

import android.os.Bundle
import android.text.Editable
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.uniandes.vinilo.MainActivity
import com.uniandes.vinilo.R
import com.uniandes.vinilo.common.dto.Album
import com.uniandes.vinilo.databinding.FragmentDetailAlbumBinding
import com.uniandes.vinilo.ui.home.viewModel.DetailAlbumViewModel

class DetailAlbumFragment : Fragment() {

    private lateinit var mBinding: FragmentDetailAlbumBinding
    //mvvm
    private lateinit var mDetailAlbumViewModel: DetailAlbumViewModel

    private var mActivity: MainActivity? = null
    private lateinit var mAlbum: Album

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle? ): View? {
        mBinding = FragmentDetailAlbumBinding.inflate(inflater, container, false)

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mDetailAlbumViewModel = ViewModelProvider(requireActivity()).get(DetailAlbumViewModel::class.java)
        //MVVM
        setupViewModel()

    }

    private fun setupViewModel() {
        mDetailAlbumViewModel.getAlbumSelected().observe(viewLifecycleOwner, {
            mAlbum = it
            setUiAlbum(it)
            setupActionBar()
        })
    }

    private fun setupActionBar() {
        mActivity = activity as? MainActivity
        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mActivity?.supportActionBar?.title = getString(R.string.detail_album_title)

        setHasOptionsMenu(true)
    }

    private fun loadImage(url: String){
        Glide.with(this)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(mBinding.imgPhotoDetail)
    }

    private fun setUiAlbum(album: Album) {
        with(mBinding){
            tvName.text = album.name
            tvGenre.text = album.genre + "-" + album.recordLabel
            tvDescription.text = album.description
            loadImage(album.cover)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onDestroy() {
        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        mActivity?.supportActionBar?.title = getString(R.string.app_name)

        setHasOptionsMenu(false)
        super.onDestroy()
    }
}