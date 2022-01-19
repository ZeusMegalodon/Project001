package com.surelabsid.newmrjempoot.member

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.surelabsid.newmrjempoot.R
import com.surelabsid.newmrjempoot.databinding.ActivityAmbilKtpBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class AmbilKtpActivity : AppCompatActivity() {

    private val TAG = "CameraXBasic"
    private val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
    private var imageCapture: ImageCapture? = null
    private var outputDirectory: File? = null
    private var viewFinder: PreviewView? = null
    private var cameraExecutor: ExecutorService? = null
    private lateinit var binding: ActivityAmbilKtpBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAmbilKtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (allPermissionGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.CAMERA),
                200
            )
        }

        outputDirectory = getOutputDirectory()
        cameraExecutor = Executors.newSingleThreadExecutor()

        binding.finish.setOnClickListener {
            finish()
        }

        binding.take.setOnClickListener {
            takePhoto()
        }
    }

    private fun getOutputDirectory(): File? {
        val mediaDir = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).absolutePath + File.separator + resources.getString(
                R.string.app_name
            )
        )
        mediaDir.mkdirs()
        return if (mediaDir.exists())
            mediaDir else filesDir
    }

    private fun startCamera() {
        val cameraProviderFeature = ProcessCameraProvider.getInstance(this)
        cameraProviderFeature.addListener({
            val cameraProvider: ProcessCameraProvider
            try{
                cameraProvider = cameraProviderFeature.get()
                val preview = Preview.Builder().build()
                preview.setSurfaceProvider(binding.viewFinder.surfaceProvider)

                imageCapture = ImageCapture.Builder().build()
                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
            }catch (e: Throwable){
                e.printStackTrace()
            }
        },ContextCompat.getMainExecutor(this))
    }

    private fun allPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun takePhoto() {
        val file = File(
            outputDirectory,
            SimpleDateFormat(
                FILENAME_FORMAT,
                Locale.getDefault()
            ).format(System.currentTimeMillis()) + ".jpg"
        )
        val outputOptions = ImageCapture.OutputFileOptions.Builder(file).build()

        imageCapture?.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(file)
                    val ii = Intent()
                    ii.putExtra("path", file.absolutePath)
                    ii.putExtra("filename", file.name)
                    setResult(Activity.RESULT_OK, ii)
                    finish()
                }

                override fun onError(exception: ImageCaptureException) {
                    exception.printStackTrace()
                }

            })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 200) {
            if (allPermissionGranted()) {
                startCamera()
            } else {
                Toast.makeText(
                    this@AmbilKtpActivity,
                    "Permission not granted by user",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor?.shutdown()
    }
}