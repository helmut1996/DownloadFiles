package com.helcode.downloaderfiles

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val downloader= AndroidDownloader(this)

        downloader.downloadFile("http://192.168.0.12/apk/facsitio.apk")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}