package com.uniandes.vinilo.models

import kotlinx.serialization.Serializable

@Serializable
data class Artista(
    var artistaId: Int,
    var name: String = "",
    var image: String,
    var date: String,
    var description: String
): java.io.Serializable