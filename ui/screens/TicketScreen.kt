package com.mohsin.eventcompanion.ui.screens

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.mohsin.eventcompanion.viewmodel.TicketViewModel
import com.mohsin.eventcompanion.viewmodel.TicketViewModelFactory
import androidx.core.graphics.createBitmap
import androidx.core.graphics.set

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketScreen() {
    val viewModel: TicketViewModel = viewModel(factory = TicketViewModelFactory())
    val qrCodeData by viewModel.qrCodeData.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.refreshTicket()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("My Ticket") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (qrCodeData.isNotEmpty()) {
                val qrBitmap = generateQrCodeBitmap(qrCodeData)
                qrBitmap?.let {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = "Ticket QR Code",
                        modifier = Modifier.size(250.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "QR Data: $qrCodeData")
            } else {
                Text(text = "Loading ticket...")
            }
        }
    }
}

fun generateQrCodeBitmap(content: String): Bitmap? {
    return try {
        val writer = QRCodeWriter()
        val bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, 512, 512)
        val width = bitMatrix.width
        val height = bitMatrix.height
        val bitmap = createBitmap(width, height, Bitmap.Config.RGB_565)
        for (x in 0 until width) {
            for (y in 0 until height) {
                bitmap[x, y] =
                    if (bitMatrix[x, y]) android.graphics.Color.BLACK else android.graphics.Color.WHITE
            }
        }
        bitmap
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
