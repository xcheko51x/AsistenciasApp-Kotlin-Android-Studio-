package com.xcheko51x.asistenciasapp

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import com.xcheko51x.asistenciasapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), RegistrosAdapter.OnItemClicked {

    lateinit var binding: ActivityMainBinding
    lateinit var adapter: RegistrosAdapter

    var listaRegistros = arrayListOf<String>()

    private val scanLauncher = registerForActivityResult<ScanOptions, ScanIntentResult>(
        ScanContract()
    ) { result: ScanIntentResult ->
        if (result.contents == null) {
            Toast.makeText(this, "CANCELADO", Toast.LENGTH_SHORT).show()
        } else {
            listaRegistros.add(result.contents)
            setupRecyclerView(
                binding.tvTotalRegistros
            )
        }
    }

    fun checkCamaraPermiso(activity: Activity): Boolean {
        return if (ContextCompat.checkSelfPermission(activity, android.Manifest.permission.CAMERA) !=
            PackageManager.PERMISSION_GRANTED) {
            false
        } else {
            ActivityCompat.requestPermissions(activity, arrayOf(android.Manifest.permission.CAMERA), 0)
            true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView(
            binding.tvTotalRegistros
        )

        binding.btnEscaner.setOnClickListener {
            val isPermiso = checkCamaraPermiso(this)
            if (isPermiso) {
                escanear()
            }
        }
    }

    fun setupRecyclerView(
        tvTotalRegistro: TextView
    ) {
        adapter = RegistrosAdapter(listaRegistros, tvTotalRegistro)
        adapter.setOnClick(this@MainActivity)
        binding.rvRegistros.adapter = adapter
    }

    fun escanear() {
        val options = ScanOptions()
        options.setDesiredBarcodeFormats(ScanOptions.ALL_CODE_TYPES)
        options.setCameraId(0)
        options.setOrientationLocked(false)
        options.setBeepEnabled(true)
        options.setCaptureActivity(CaptureActivityPortrait::class.java)

        scanLauncher.launch(options)
    }

    override fun borrarRegistro(nombre: String) {
        listaRegistros.remove(nombre)
        binding.rvRegistros.adapter!!.notifyDataSetChanged()
        if (listaRegistros.size == 0) {
            binding.tvTotalRegistros.text = "0 Registros"
        }
    }
}