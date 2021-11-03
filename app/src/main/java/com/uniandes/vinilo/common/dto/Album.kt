package com.uniandes.vinilo.common.dto

data class Album(var id: Long = 0,
                 var name: String = "",
                 var cover: String,
                 var releaseDate: String,
                 var description: String,
                 var genre: String,
                 var recordLabel: String)
