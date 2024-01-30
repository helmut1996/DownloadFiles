package com.helcode.downloaderfiles

interface Downloader {
    fun downloadFile(url:String):Long
}