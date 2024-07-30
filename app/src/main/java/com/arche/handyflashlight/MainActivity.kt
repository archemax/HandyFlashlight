package com.arche.handyflashlight

import android.content.BroadcastReceiver
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.app.ActivityCompat
import com.arche.handyflashlight.ui.theme.HandyFlashlightTheme
import com.arche.handyflashlight.user_interface.MainScreen
import com.google.android.gms.ads.MobileAds

class MainActivity : ComponentActivity() {

    private var permissions = arrayOf(android.Manifest.permission.CAMERA)
    private var permissionGranted = false
    private lateinit var powerButtonReceiver: BroadcastReceiver

    companion object {
        private const val REQUEST_CODE = 123
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MobileAds.initialize(this)
            HandyFlashlightTheme {
                MainScreen()
            }
        }
///////////////////////////////////////////////////////////////////////////

        permissionGranted = ActivityCompat.checkSelfPermission(
            this,
            permissions[0]
        ) == PackageManager.PERMISSION_GRANTED
        if (!permissionGranted)
            ActivityCompat.requestPermissions(
                this@MainActivity,
                permissions,
                REQUEST_CODE
            )
    ///////////////////////////////////////////////////////////////////////////////
    }


    // -- END OF onCreate()--

    override fun onDestroy() {
        super.onDestroy()
    }
}







