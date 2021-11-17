package com.uniandes.vinilo.models

data class Artista(
    var artistaId: Int,
    var name: String = "",
    var image: String,
    var date: String,
    var description: String)