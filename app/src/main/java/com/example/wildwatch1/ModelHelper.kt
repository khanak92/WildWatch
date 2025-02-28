package com.example.wildwatch1

import android.content.Context
import org.pytorch.Module
import org.pytorch.IValue
import org.pytorch.Tensor
import org.pytorch.torchvision.TensorImageUtils
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

object ModelHelper {
    private lateinit var animalDetectionModel: Module
    private lateinit var workerFaceDetectionModel: Module

    fun loadModels(context: Context) {
        try {
            animalDetectionModel = Module.load(assetFilePath(context, "animal_detection.pt"))
            workerFaceDetectionModel = Module.load(assetFilePath(context, "worker_face_detection.pt"))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun runAnimalDetection(inputTensor: Tensor): Tensor {
        return animalDetectionModel.forward(IValue.from(inputTensor)).toTensor()
    }

    fun runWorkerFaceDetection(inputTensor: Tensor): Tensor {
        return workerFaceDetectionModel.forward(IValue.from(inputTensor)).toTensor()
    }

    private fun assetFilePath(context: Context, assetName: String): String {
        val file = File(context.filesDir, assetName)
        if (file.exists()) return file.absolutePath

        try {
            val inputStream: InputStream = context.assets.open(assetName)
            val outputStream = FileOutputStream(file)
            val buffer = ByteArray(4 * 1024)
            var read: Int

            while (inputStream.read(buffer).also { read = it } != -1) {
                outputStream.write(buffer, 0, read)
            }

            outputStream.flush()
            outputStream.close()
            inputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return file.absolutePath
    }
}
