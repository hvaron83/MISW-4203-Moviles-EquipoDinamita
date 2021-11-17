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
import com.uniandes.vinilo.databinding.ArtistaDetailFragmentBinding
import com.uniandes.vinilo.databinding.ArtistaFragmentBinding
import com.uniandes.vinilo.models.Artista
import com.uniandes.vinilo.models.Collector
import com.uniandes.vinilo.models.Comment
import com.uniandes.vinilo.ui.adapters.ArtistasAdapter
import com.uniandes.vinilo.viewmodels.ArtistaDetailViewModel
import com.uniandes.vinilo.viewmodels.ArtistaViewModel
import com.uniandes.vinilo.viewmodels.CollectorViewModel
import com.uniandes.vinilo.viewmodels.CommentViewModel


class ArtistaDetailFragment : Fragment() {
    private var _binding: ArtistaDetailFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ArtistaDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ArtistaDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.artista.observe(viewLifecycleOwner, Observer<Artista> {
            it.apply {
                binding.artista = this
                loadImage(this)
            }
        })
        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })

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

    private fun loadImage(artista: Artista){
        Glide.with(this)
            .load(artista.image.toUri().buildUpon().scheme("https").build())
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_artist)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.ic_artist))
            .into(binding.artistaImagen)
    }

}