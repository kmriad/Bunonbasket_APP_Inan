package com.example.bunonbasket.ui.component.bazar


import android.Manifest
import android.content.pm.PackageManager
import android.hardware.camera2.CameraManager
import android.os.Bundle
import android.view.SurfaceView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.bunonbasket.R
import com.example.bunonbasket.databinding.ActivityUploadBazarBinding
import com.tbruyelle.rxpermissions3.RxPermissions
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable


class UploadBazarActivity : AppCompatActivity() {

    lateinit var binding: ActivityUploadBazarBinding
    private val camera: CameraManager? = null

    val rxPermissions = RxPermissions(this)
    private val surfaceView: SurfaceView? = null
    private val disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_BunonBasket)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_upload_bazar)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        binding.btnCamera.setOnClickListener {
            Observable.just(checkIsGranted(Manifest.permission.CAMERA))
                .flatMap { granted ->
                    if (granted)
                        Observable.just(true)
                    else rxPermissions.request(Manifest.permission.CAMERA)
                }
                .subscribe {

                }
        }

        binding.btnGallery.setOnClickListener {
            Observable.just(
                checkIsGranted(Manifest.permission.READ_EXTERNAL_STORAGE) && checkIsGranted(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            )
                .flatMap { granted ->
                    if (granted)
                        Observable.just(true)
                    else rxPermissions.request(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                }
                .subscribe {

                }
        }
    }

    fun checkIsGranted(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            permission
        ) == PackageManager.PERMISSION_GRANTED;
    }
}