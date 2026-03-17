package com.example.tarea4

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class TakePhotoActivity : AppCompatActivity() {

    private lateinit var tvEstado: TextView
    private lateinit var ivFoto: ImageView
    private lateinit var btnTomarFoto: Button

    private val cameraLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->

        if (result.resultCode == RESULT_OK) {
            val extras = result.data?.extras
            val imageBitmap = extras?.get("data") as? Bitmap

            if (imageBitmap != null) {
                mostrarFoto(imageBitmap)
            } else {
                Toast.makeText(this, "No se pudo obtener la foto", Toast.LENGTH_SHORT).show()
                mostrarEspera()
            }
        } else {
            Toast.makeText(this, "Captura cancelada", Toast.LENGTH_SHORT).show()
            mostrarEspera()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_take_photo)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_take_photo)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tvEstado = findViewById(R.id.tvEstadoFoto)
        ivFoto = findViewById(R.id.ivFoto)
        btnTomarFoto = findViewById(R.id.btnTomarFoto)

        mostrarEspera()

        btnTomarFoto.setOnClickListener {
            abrirCamara()
        }
    }

    /**
     * Aquí está el núcleo del Punto B.
     *
     * Se usa un implicit intent con ACTION_IMAGE_CAPTURE para pedirle
     * a la app de cámara del dispositivo que tome una foto y la devuelva
     * a nuestra aplicación.
     */
    private fun abrirCamara() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        if (intent.resolveActivity(packageManager) != null) {
            cameraLauncher.launch(intent)
        } else {
            Toast.makeText(this, "No hay una app de cámara disponible", Toast.LENGTH_LONG).show()
        }
    }

    private fun mostrarFoto(bitmap: Bitmap) {
        tvEstado.text = "Intent recibido desde la cámara\n\n✅ Foto capturada correctamente"
        ivFoto.setImageBitmap(bitmap)
        ivFoto.visibility = View.VISIBLE
    }

    private fun mostrarEspera() {
        tvEstado.text = "Presiona el botón para abrir la cámara, tomar una foto y traerla a esta app."
        ivFoto.visibility = View.GONE
    }
}