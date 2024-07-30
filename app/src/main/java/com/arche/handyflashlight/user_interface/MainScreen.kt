package com.arche.handyflashlight.user_interface


import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.arche.handyflashlight.R
import com.arche.handyflashlight.flashlight.Flashlight
import com.arche.handyflashlight.flashlight.FlashlightService
import com.arche.handyflashlight.ui.theme.lightGreyText
import com.arche.handyflashlight.ui.theme.specialColor

import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView


@Composable
fun MainScreen() {
    val context = LocalContext.current
    val flashlight = remember { Flashlight(context) }
    var isServiceRunning = remember {
        false
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Cyan)
    ) {
        Image(
            painter = painterResource(id = R.drawable.background), contentDescription = "",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                //.background(Color.Green)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Button(
                colors = ButtonDefaults.buttonColors(containerColor = specialColor),
                onClick = { flashlight.toggleFlashlight() }
            ) {
                Text(
                    text = "ON/OFF",
                    style = MaterialTheme.typography.titleLarge,
                    color = lightGreyText
                )
                if (flashlight.isFlashlightOn()) {
                    Image(
                        painter = painterResource(id = R.drawable.green_new),
                        contentDescription = ""
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.reb_new),
                        contentDescription = ""
                    )
                }

            }

            Spacer(modifier = Modifier.height(64.dp))

            Button(
                colors = ButtonDefaults.buttonColors(containerColor = specialColor),
                modifier = Modifier
                    .height(64.dp)
                    .width(256.dp),

                onClick = {
                    // Start the flashlight service
                    val intent = Intent(context, FlashlightService::class.java)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        context.startForegroundService(intent)
                    }
                    Toast.makeText(context, "Service Stared", Toast.LENGTH_SHORT).show()
                    isServiceRunning = true
                }
            ) {
                Text(
                    text = "START Service",
                    style = MaterialTheme.typography.titleLarge,
                    color = lightGreyText
                )
            }
            Spacer(modifier = Modifier.height(16.dp))


            Button(
                colors = ButtonDefaults.buttonColors(containerColor = specialColor),
                modifier = Modifier
                    .height(64.dp)
                    .width(256.dp),

                onClick = {
                    // Stop the flashlight service
                    val intent = Intent(context, FlashlightService::class.java)
                    context.stopService(intent)
                    Toast.makeText(context, "Service Stopped", Toast.LENGTH_SHORT).show()
                }
            ) {
                Text(
                    text = "STOP Service",
                    style = MaterialTheme.typography.titleLarge,
                    color = lightGreyText
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                //.background(Color.Red)
                .height(70.dp),

            ) {
            ShowBannedAd(
                modifier = Modifier
                    .fillMaxWidth(),

                // REAL UNIT ID
                adId = "ca-app-pub-4187563943965256/9477715676"

                  // TEST UNIT ID
                //adId = "ca-app-pub-3940256099942544/9214589741"
            )
        }
    }
}


@Composable
fun ShowBannedAd(modifier: Modifier, adId: String) {
    AndroidView(modifier = modifier.fillMaxSize(),
        factory = { context ->
            AdView(context).apply {
                setAdSize(AdSize.BANNER)
                adUnitId = adId

                loadAd(AdRequest.Builder().build())
            }
        })
}
