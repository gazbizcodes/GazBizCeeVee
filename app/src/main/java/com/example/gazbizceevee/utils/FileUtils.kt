package com.example.gazbizceevee.utils

import android.content.Context
import android.util.Log
import java.io.File

/**
 * Created by Gaz Biz on 10/5/23.
 */
class FileUtils {

    fun getFileFromAssets(context: Context, fileName: String): File? =
        try {
            File(context.cacheDir, fileName)
                .also {file ->
                    if (!file.exists()) {
                        file.outputStream().use { cache ->
                            context.assets.open(fileName).use { inputStream ->
                                inputStream.copyTo(cache)
                            }
                        }
                    }
                }
        } catch (e: Exception) {
            Log.e("FileUtils", "getFileFromAssets: ${e.message}")
            null
        }
}