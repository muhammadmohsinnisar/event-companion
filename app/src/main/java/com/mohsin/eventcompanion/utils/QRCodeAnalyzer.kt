package com.mohsin.eventcompanion.utils

import android.graphics.ImageFormat
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.zxing.*
import com.google.zxing.common.HybridBinarizer

class QRCodeAnalyzer(private val onQRCodeFound: (String) -> Unit) : ImageAnalysis.Analyzer {

    private var found = false
    private val reader = MultiFormatReader().apply {
        setHints(
            mapOf(
                DecodeHintType.POSSIBLE_FORMATS to listOf(BarcodeFormat.QR_CODE),
                DecodeHintType.TRY_HARDER to true
            )
        )
    }

    @OptIn(ExperimentalGetImage::class)
    override fun analyze(imageProxy: ImageProxy) {
        val image = imageProxy.image ?: run {
            imageProxy.close()
            return
        }

        if (found || image.format != ImageFormat.YUV_420_888) {
            imageProxy.close()
            return
        }

        val buffer = image.planes[0].buffer
        val bytes = ByteArray(buffer.remaining())
        buffer.get(bytes)

        val width = image.width
        val height = image.height

        val source = PlanarYUVLuminanceSource(
            bytes, width, height, 0, 0, width, height, false
        )
        val bitmap = BinaryBitmap(HybridBinarizer(source))

        try {
            val result = reader.decodeWithState(bitmap)
            reader.reset() // avoid stale decoding state

            Log.d("QRCodeAnalyzer", "QR Code found: ${result.text}")
            if (!found) {
                found = true
                Handler(Looper.getMainLooper()).post {
                    onQRCodeFound(result.text)
                }
            }
        } catch (e: NotFoundException) {
            Log.d("QRCodeAnalyzer", "No QR code found in frame.")
        } catch (e: Exception) {
            Log.e("QRCodeAnalyzer", "Unexpected error: ${e.message}", e)
        } finally {
            imageProxy.close()
        }
    }
}
