package com.example.wildwatch1

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

class HardwareManagment : AppCompatActivity() {

    private lateinit var edtSerialNumber: EditText
    private lateinit var edtCameraName: EditText
    private lateinit var edtIpAddress: EditText
    private lateinit var edtCameraLocation: EditText
    private lateinit var btnAddCamera: Button
    private lateinit var btnRemoveCamera: Button

    private val pythonServerUrl = "http://192.168.1.100:5000" // Update with your actual server IP

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hardware_managment)

        edtSerialNumber = findViewById(R.id.edtSerialNumber)
        edtCameraName = findViewById(R.id.edtCameraName)
        edtIpAddress = findViewById(R.id.edtIpAddress)
        edtCameraLocation = findViewById(R.id.edtCameraLocation)
        btnAddCamera = findViewById(R.id.btnAddCamera)
        btnRemoveCamera = findViewById(R.id.btnRemoveCamera)

        btnAddCamera.setOnClickListener {
            sendCameraInfoToPython()
        }
    }

    private fun sendCameraInfoToPython() {
        val serialNumber = edtSerialNumber.text.toString().trim()
        val cameraName = edtCameraName.text.toString().trim()
        val ipAddress = edtIpAddress.text.toString().trim()
        val location = edtCameraLocation.text.toString().trim()

        if (serialNumber.isEmpty() || cameraName.isEmpty() || ipAddress.isEmpty() || location.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val jsonObject = JSONObject().apply {
            put("serialNumber", serialNumber)
            put("cameraName", cameraName)
            put("ipAddress", ipAddress)
            put("location", location)
        }

        val requestBody = jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())

        val request = Request.Builder()
            .url("$pythonServerUrl/start_detection")
            .post(requestBody)
            .build()

        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@HardwareManagment, "Failed to connect to Python server: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                runOnUiThread {
                    if (response.isSuccessful) {
                        Toast.makeText(this@HardwareManagment, "Camera added, detection started!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@HardwareManagment, "Server Error: ${response.message}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }
}
