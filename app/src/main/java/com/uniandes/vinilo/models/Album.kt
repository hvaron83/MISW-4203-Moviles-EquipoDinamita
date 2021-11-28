package com.uniandes.vinilo.models

import kotlinx.serialization.Serializable

@Serializable
data class Album (
    val albumId:Int,
    var name:String,
    var cover:String,
    val releaseDate:String,
    val description:String,
    var genre:String,
    val recordLabel:String
): java.io.Serializable
