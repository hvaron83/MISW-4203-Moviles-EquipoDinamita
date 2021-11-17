package com.uniandes.vinilo.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.uniandes.vinilo.R
import com.uniandes.vinilo.databinding.ArtistaItemBinding
import com.uniandes.vinilo.models.Artista
import com.uniandes.vinilo.ui.ArtistaFragmentDirections

class ArtistasAdapter : RecyclerView.Adapter<ArtistasAdapter.ArtistaViewHolder>(){

    var artistas :List<Artista> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistaViewHolder {
        val withDataBinding: ArtistaItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            ArtistaViewHolder.LAYOUT,
            parent,
            false)
        return ArtistaViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: ArtistaViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.artista = artistas[position]
        }
        holder.bind(artistas[position])
        holder.viewDataBinding.root.setOnClickListener {
            val action = ArtistaFragmentDirections.actionArtistaFragmentToArtistaDetailFragment(artistas[position].artistaId)
            // Navigate using that action
            holder.viewDataBinding.root.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return artistas.size
    }


    class ArtistaViewHolder(val viewDataBinding: ArtistaItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.artista_item
        }
        fun bind(artista: Artista) {
            Glide.with(itemView)
                .load(artista.image.toUri().buildUpon().scheme("https").build())
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.ic_loading_animation)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.drawable.ic_broken_image))
                .into(viewDataBinding.artistaImagen)
        }
    }


}