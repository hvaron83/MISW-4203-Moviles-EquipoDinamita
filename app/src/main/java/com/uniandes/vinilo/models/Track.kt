package com.uniandes.vinilo.models

import kotlinx.serialization.Serializable

@Serializable
data class Track (
    var albumId:Int?,
    val id:Int?=null,
    var name:String="",
    var duration:String=""
): java.io.Serializable
