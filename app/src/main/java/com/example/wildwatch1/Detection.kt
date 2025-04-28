package com.example.wildwatch1

data class Detection(
    val detectionType: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val timestamp: Long = 0
)
