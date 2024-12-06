package com.example.lookinapp.screen.pref

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

fun compressImage(file: File, maxSize: Long = 1 * 1024 * 1024): File {
    var compressedFile = file
    var quality = 100
    var bitmap: Bitmap? = BitmapFactory.decodeFile(file.absolutePath)

    while (compressedFile.length() > maxSize && quality > 10) {
        val baos = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG, quality, baos)
        val compressedBytes = baos.toByteArray()

        compressedFile = File(file.parent, "compressed_${file.name}")
        try {
            FileOutputStream(compressedFile).apply {
                write(compressedBytes)
                close()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        quality -= 10
    }

    bitmap?.recycle()
    return compressedFile
}
