package com.uniandes.vinilo.ui

import com.uniandes.vinilo.models.Album


interface OnClickListener {
    fun onClick(album: Album)
}