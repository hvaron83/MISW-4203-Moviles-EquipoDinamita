package com.uniandes.vinilo.models

import kotlinx.serialization.Serializable

@Serializable
data class Album (
    val albumId:Int?,
    var name:String="",
    var cover:String="",
    var releaseDate:String="",
    var description:String="",
    var genre:String="",
    var recordLabel:String="",
): java.io.Serializable
