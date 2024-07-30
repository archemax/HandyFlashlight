package com.arche.handyflashlight.user_interface

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.arche.handyflashlight.flashlight.Flashlight

class PowerButtonReceiver(context: Context) : BroadcastReceiver() {
    private val myFlashlight = Flashlight(context.applicationContext)
    private var lastPressTime: Long = 0
    private var screenToggleCount = 0


    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        Log.d("AAA My Action", "My Action: $action")

        if (action == Intent.ACTION_SCREEN_ON || action == Intent.ACTION_SCREEN_OFF) {

            val currentTime = System.currentTimeMillis()
            Log.d("AAA Current Time ", "Current Time: $currentTime")

            val interval = currentTime - lastPressTime
            Log.d("AAA Interval ", "Interval: $interval")

            if (interval < DOUBLE_CLICK_TIME ) {

                screenToggleCount++

                if (screenToggleCount == 2) {
                    myFlashlight.turnOnFlashlight()
                    Log.d(
                        "AAA PowerButtonReceiver",
                        "Double power button click detected, toggling flashlight."
                    )
                    screenToggleCount = 0
                }



            } else {
                screenToggleCount = 1
            }

            lastPressTime = currentTime

        }
    }

    companion object {
        private const val DOUBLE_CLICK_TIME = 600L // Adjust as needed
    }
}

