package com.uniandes.vinilo.models

import kotlinx.serialization.Serializable

@Serializable
data class Collector (
    val collectorId: Int,
    val name:String,
    val telephone:String,
    val email:String
): java.io.Serializable
