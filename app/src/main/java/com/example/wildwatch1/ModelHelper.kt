package com.example.wildwatch1

import android.content.Context
import org.pytorch.IValue
import org.pytorch.LiteModuleLoader
import org.pytorch.Module
import org.pytorch.Tensor
import java.nio.ByteBuffer

object ModelHelper {
    private var animalDetectionModel: Module? = null
    private var workerFaceDetectionModel: Module? = null

    fun loadModels(context: Context) {
        try {
            animalDetectionModel = LiteModuleLoader.load(assetFilePath(context, "animal_detection.ptl"))
            workerFaceDetectionModel = LiteModuleLoader.load(assetFilePath(context, "worker_face_detection.ptl"))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun runAnimalDetection(imageTensor: Tensor): IValue? {
        return animalDetectionModel?.forward(IValue.from(imageTensor))
    }

    fun runWorkerFaceDetection(imageTensor: Tensor): IValue? {
        return workerFaceDetectionModel?.forward(IValue.from(imageTensor))
    }

    private fun assetFilePath(context: Context, assetName: String): String {
        val file = java.io.File(context.filesDir, assetName)
        return file.absolutePath
    }
}
