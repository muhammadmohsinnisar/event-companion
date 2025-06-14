package com.mohsin.eventcompanion

import android.graphics.ImageFormat
import android.os.Handler
import android.os.Looper
import android.util.Log
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

    override fun analyze(imageProxy: ImageProxy) {
        if (found) {
            imageProxy.close()
            return
        }

        val rotationDegrees = imageProxy.imageInfo.rotationDegrees
        Log.d("QRCodeAnalyzer", "analyze() called - frame received")
        Log.d("QRCodeAnalyzer", "rotationDegrees = $rotationDegrees")

        val image = imageProxy.image ?: run {
            imageProxy.close()
            return
        }

        if (image.format != ImageFormat.YUV_420_888) {
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
        val binaryBitmap = BinaryBitmap(HybridBinarizer(source))

        try {
            val result = reader.decodeWithState(binaryBitmap)
            Log.d("QRCodeAnalyzer", "QR found: ${result.text}")
            if (!found) {
                found = true

                //Otherwise illegal for navigation call
                Handler(Looper.getMainLooper()).post {
                    onQRCodeFound(result.text)
                }
            }
        } catch (e: NotFoundException) {
            Log.d("QRCodeAnalyzer", "No QR found in frame")
        } finally {
            imageProxy.close()
        }
    }
}
