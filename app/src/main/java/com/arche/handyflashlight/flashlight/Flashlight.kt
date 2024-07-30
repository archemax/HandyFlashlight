package com.arche.handyflashlight.flashlight

import android.content.Context
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class Flashlight(context: Context) {

    //////////////////////////////////////////////////////////////////////////
    private val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    private var camId: String = ""

    var flashlightState by mutableStateOf(false)
        private set

    init {
        camId = cameraManager.cameraIdList[0]
            ?: throw IllegalStateException("No camera with flash found")
    }


    fun turnOnFlashlight() {
        try {
            cameraManager.setTorchMode(camId, true)
            flashlightState = true
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }


    private fun turnOffFlashlight() {
        try {
            cameraManager.setTorchMode(camId, false)
            flashlightState = false
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    fun toggleFlashlight() {
        if (flashlightState) {
            turnOffFlashlight()
            Log.d("AAA Flashlight", "Flashlight turned OFF")
        } else {
            turnOnFlashlight()
            Log.d("AAA Flashlight", "Flashlight turned ON")
        }
    }

    fun isFlashlightOn(): Boolean {
        return flashlightState
    }

}
