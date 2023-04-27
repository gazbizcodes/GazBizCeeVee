package com.example.gazbizceevee

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toUri
import com.example.gazbizceevee.ui.theme.GazBizCeeVeeTheme
import com.rizzi.bouquet.ResourceType
import com.rizzi.bouquet.VerticalPDFReader
import com.rizzi.bouquet.rememberVerticalPdfReaderState
import java.io.File

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            val file = getFileFromAssets(this, "cv.pdf")
            Log.e("MainActivity", "File exists: ${file.toURI()}")
        setContent {
            val pdfState = rememberVerticalPdfReaderState(
                resource = ResourceType.Local(file.toUri()),
                isZoomEnable = true
            )

            GazBizCeeVeeTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    VerticalPDFReader(
                        state = pdfState,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = MaterialTheme.colorScheme.background)
                    )
                }
            }
        }
    }

    private fun getFileFromAssets(context: Context, fileName: String): File = File(context.cacheDir, fileName)
        .also {
            if (!it.exists()) {
                it.outputStream().use { cache ->
                    context.assets.open(fileName).use { inputStream ->
                        inputStream.copyTo(cache)
                    }
                }
            }
        }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        GazBizCeeVeeTheme {

        }
    }
}