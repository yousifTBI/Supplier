package com.tbi.supplierplus.framework.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.tbi.supplierplus.R
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.*
import java.io.IOException

@AndroidEntryPoint
class NetSpeedActivity : AppCompatActivity() {

    private lateinit var downloadSpeedTextView: TextView
    private lateinit var uploadSpeedTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_net_speed)
        downloadSpeedTextView = findViewById(R.id.downloadSpeedTextView)

        val startTestButton: Button = findViewById(R.id.startTestButton)
        startTestButton.setOnClickListener { startSpeedTest() }
    }

    fun startSpeedTest() {
        val url = "https://dawar-api.unoerp.app/"
        val request = Request.Builder().url(url).build()

        val client = OkHttpClient()

        val startTime = System.currentTimeMillis()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.close()
                val endTime = System.currentTimeMillis()
                val duration = endTime - startTime
                val fileSize = response.body?.contentLength() ?: 0

                if (duration > 0) {
                    val downloadSpeed = (fileSize / duration) * 1000 // Convert to bytes per second
                    updateSpeedText(downloadSpeed.toDouble(), downloadSpeedTextView)
                }
            }
        })
    }

    private fun updateSpeedText(speed: Double, textView: TextView) {
        runOnUiThread {
            val formattedSpeed = formatSpeed(speed)
            textView.text = "Download Speed: $formattedSpeed"
        }
    }

    private fun formatSpeed(speedBytesPerSecond: Double): String {
        val units = arrayOf("B/s", "KB/s", "MB/s", "GB/s")
        var speed = speedBytesPerSecond

        var index = 0
        while (speed >= 1024 && index < units.size - 1) {
            speed /= 1024
            index++
        }

        return String.format("%.2f %s", speed, units[index])
    }
}