package com.uniandes.vinilo.ui.artista.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.uniandes.vinilo.R
import com.uniandes.vinilo.common.dto.Artista
import com.uniandes.vinilo.databinding.ItemArtistaBinding
import com.uniandes.vinilo.ui.artista.ArtistaFragment
import kotlinx.coroutines.flow.merge
import java.text.SimpleDateFormat
import java.util.*

class ArtistaAdapter(private var artistas: MutableList<Artista>, private var listener: ArtistaFragment) :
    RecyclerView.Adapter<ArtistaAdapter.ViewHolder>() {

    private lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_artista, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val artista = artistas.get(position)

        with(holder){
            setListener(artista)

            binding.tvName.text = artista.name

            val dateFormat = SimpleDateFormat(
                "yyyy-MM-dd", Locale.ENGLISH
            )
            if (artista.creationDate != null ) {
                var date = dateFormat.parse(artista.creationDate)
                binding.tvCreateDate.text = dateFormat.format(date)
            }
            else {
                var date = dateFormat.parse(artista.birthDate)
                binding.tvCreateDate.text = dateFormat.format(date)
            }


            Glide.with(mContext)
                .load(artista.image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(binding.imgPhoto)
        }
    }

    override fun getItemCount(): Int = artistas.size

    fun setArtistas(artistas: MutableList<Artista>) {
        this.artistas = artistas
        notifyDataSetChanged()
    }

    fun <T> rmerge(first: List<T>, second: List<T>): List<T> {
        return first.plus(second)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val binding = ItemArtistaBinding.bind(view)

        fun setListener(artista: Artista){
            with(binding.root) {
            //     setOnClickListener { listener.onClick(artista) }
            }
        }

    }


}