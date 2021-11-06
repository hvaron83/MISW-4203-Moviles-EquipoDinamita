package com.uniandes.vinilo.ui.album.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.uniandes.vinilo.R
import com.uniandes.vinilo.common.dto.Album
import com.uniandes.vinilo.databinding.ItemAlbumBinding
import com.uniandes.vinilo.ui.album.OnClickListener

class AlbumAdapter(private var albumes: MutableList<Album>, private var listener: OnClickListener) :
    RecyclerView.Adapter<AlbumAdapter.ViewHolder>() {

    private lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_album, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val album = albumes.get(position)

        with(holder){
            setListener(album)

            binding.tvName.text = album.name
            binding.tvDescription.text = album.description

            Glide.with(mContext)
                .load(album.cover)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(binding.imgPhoto)
        }
    }

    override fun getItemCount(): Int = albumes.size

    fun setAlbumes(albumes: MutableList<Album>) {
        this.albumes = albumes
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val binding = ItemAlbumBinding.bind(view)

        fun setListener(album: Album){
            with(binding.root) {
                setOnClickListener { listener.onClick(album) }
            }
        }

    }


}