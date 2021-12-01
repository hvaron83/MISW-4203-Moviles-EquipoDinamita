package com.uniandes.vinilo.models

import kotlinx.serialization.Serializable

@Serializable
data class Album (
    val albumId:Int,
    val name:String = "",
    val cover:String,
    val releaseDate:String = "",
    val description:String,
    val genre:String = "",
    val recordLabel:String = ""
): java.io.Serializable
