package com.example.locus.utils

import android.content.Context
import android.util.Log
import java.io.IOException


class LoadJson(private val context: Context) {

    fun loadJSONFromAsset(fileName: String): String? {

        val json: String? = try {
            val inputStream = context.assets.open(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, Charsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }

        Log.e("data", json.toString())
        return json
    }

}