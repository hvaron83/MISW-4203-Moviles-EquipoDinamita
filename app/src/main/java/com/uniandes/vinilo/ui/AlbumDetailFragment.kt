package com.uniandes.vinilo.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.uniandes.vinilo.R
import com.uniandes.vinilo.databinding.AlbumDetailFragmentBinding
import com.uniandes.vinilo.databinding.AlbumFragmentBinding
import com.uniandes.vinilo.models.Album
import com.uniandes.vinilo.models.Collector
import com.uniandes.vinilo.models.Comment
import com.uniandes.vinilo.ui.adapters.AlbumsAdapter
import com.uniandes.vinilo.viewmodels.AlbumDetailViewModel
import com.uniandes.vinilo.viewmodels.AlbumViewModel
import com.uniandes.vinilo.viewmodels.CollectorViewModel
import com.uniandes.vinilo.viewmodels.CommentViewModel


class AlbumDetailFragment : Fragment() {
    private var _binding: AlbumDetailFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AlbumDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AlbumDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        activity.actionBar?.title = getString(R.string.title_comments)
        val args: CommentFragmentArgs  by navArgs()
        Log.d("Args", args.albumId.toString())
        viewModel = ViewModelProvider(this, AlbumDetailViewModel.Factory(activity.application, args.albumId)).get(
            AlbumDetailViewModel::class.java)
        viewModel.album.observe(viewLifecycleOwner, Observer<Album> {
            it.apply {
               binding.album = this
                loadImage(this)
            }
        })
        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })
        binding.comentariosButton.setOnClickListener{
            val action = AlbumDetailFragmentDirections.actionAlbumFragmentToCommentFragment(args.albumId)
            // Navigate using that action
            findNavController().navigate(action)
        }


    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onNetworkError() {
        if(!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }

    private fun loadImage(album: Album){
        Glide.with(this)
            .load(album.cover.toUri().buildUpon().scheme("https").build())
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_loading_animation)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.ic_broken_image))
            .into(binding.albumCover)
    }




}