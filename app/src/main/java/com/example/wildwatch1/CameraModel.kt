package com.example.wildwatch1

data class CameraModel(
    val serialNumber: String = "",
    val cameraName: String = "",
    val ipAddress: String = "",
    val cameraLocation: String = "",
    val timestamp: String = "" // Stores the date & time of camera addition
)
