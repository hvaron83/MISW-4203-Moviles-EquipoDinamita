package com.uniandes.vinilo.common.dto

data class Artista(var id: Long = 0,
                 var name: String = "",
                 var image: String,
                 var creationDate: String,
                   var birthDate: String,
                 var description: String)