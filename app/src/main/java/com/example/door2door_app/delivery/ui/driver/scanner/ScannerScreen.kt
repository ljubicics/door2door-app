package com.example.door2door_app.delivery.ui.driver.scanner

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.example.door2door_app.websockets.WebSocketClient
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import org.koin.androidx.compose.koinViewModel

@Composable
fun ScannerScreen(
    navController: NavController,
    viewModel: ScannerViewModel = koinViewModel()
) {
    val context = LocalContext.current

    val localContext = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember {
        ProcessCameraProvider.getInstance(localContext)
    }

    LaunchedEffect(key1 = null) {
        viewModel.nextScreen.collect {
            navController.popBackStack()
        }
    }

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            val previewView = PreviewView(context)
            val preview = Preview.Builder().build()
            val selector = CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build()

            preview.setSurfaceProvider(previewView.surfaceProvider)

            val analyzer = ImageAnalysis.Builder().build()
            analyzer.setAnalyzer(
                ContextCompat.getMainExecutor(context),
                BarcodeAnalyzer(context, navController = navController, viewModel = viewModel)
            )

            runCatching {
                cameraProviderFuture.get().bindToLifecycle(
                    lifecycleOwner,
                    selector,
                    preview,
                    analyzer
                )
            }.onFailure {
                Log.e("CAMERA", "Camera bind error ${it.localizedMessage}", it)
            }
            previewView
        }
    )
}

class BarcodeAnalyzer(
    private val context: Context,
    private val navController: NavController,
    private val viewModel: ScannerViewModel
) : ImageAnalysis.Analyzer {

    private var isBarcodeScanned: Boolean = false

    private val options = BarcodeScannerOptions.Builder()
        .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
        .build()

    private val scanner = BarcodeScanning.getClient(options)

    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        imageProxy.image?.let { image ->
            scanner.process(
                InputImage.fromMediaImage(
                    image, imageProxy.imageInfo.rotationDegrees
                )
            ).addOnSuccessListener { barcode ->
                barcode?.takeIf { it.isNotEmpty() }
                    ?.mapNotNull { it.rawValue }
                    ?.joinToString(",")
                    ?.let {
                        if (isBarcodeScanned.not()) {
                            viewModel.confirmDelivery(confirmPath = it)
                            isBarcodeScanned = true
                        }
                    }
            }.addOnCompleteListener {
                imageProxy.close()
            }
        }
    }
}