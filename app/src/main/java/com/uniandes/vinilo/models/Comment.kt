package com.uniandes.vinilo.models

import kotlinx.serialization.Serializable

@Serializable
data class Comment (
    val description:String,
    val rating:String,
    val albumId:Int
): java.io.Serializable
