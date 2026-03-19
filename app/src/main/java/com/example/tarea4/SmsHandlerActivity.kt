package com.example.tarea4

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SmsHandlerActivity : AppCompatActivity() {

    private lateinit var tvEstado: TextView
    private lateinit var tvNumero: TextView
    private lateinit var tvMensaje: TextView
    private lateinit var layoutDatos: View
    private lateinit var btnSimularEnvio: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sms_handler)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_sms_handler)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tvEstado        = findViewById(R.id.tvEstado)
        tvNumero        = findViewById(R.id.tvNumero)
        tvMensaje       = findViewById(R.id.tvMensaje)
        layoutDatos     = findViewById(R.id.layoutDatos)
        btnSimularEnvio = findViewById(R.id.btnSimularEnvio)

        procesarIntent(intent)

        btnSimularEnvio.setOnClickListener {
            simularEnvio()
        }
    }

    // Por si la Activity ya estaba abierta y llega un nuevo intent
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        procesarIntent(intent)
    }


    private fun procesarIntent(intent: Intent?) {
        if (intent == null) {
            mostrarEspera()
            return
        }

        when (intent.action) {

            // SENDTO: intent estándar de SMS
            Intent.ACTION_SENDTO -> {
                val uri: Uri? = intent.data
                val numero  = uri?.schemeSpecificPart ?: "Sin número"
                val mensaje = intent.getStringExtra("sms_body") ?: "Sin mensaje"
                mostrarDatos(numero, mensaje)
            }

            //SEND: Otra app
            Intent.ACTION_SEND -> {
                if (intent.type == "text/plain") {
                    val mensaje = intent.getStringExtra(Intent.EXTRA_TEXT) ?: "Sin texto"
                    val numero  = intent.getStringExtra("address") ?: "Sin número"
                    mostrarDatos(numero, mensaje)
                }
            }

            // Se abrió directo desde MainActivity
            else -> mostrarEspera()
        }
    }

    private fun mostrarDatos(numero: String, mensaje: String) {
        tvEstado.text    = "Intent recibido desde otra app"
        tvNumero.text    = "Para: $numero"
        tvMensaje.text   = "Mensaje: $mensaje"
        layoutDatos.visibility      = View.VISIBLE
        btnSimularEnvio.visibility  = View.VISIBLE
    }

    private fun mostrarEspera() {
        tvEstado.text   = "Esta app está registrada para recibir intents de SMS.\n\nAbre otra app, intenta enviar un SMS y selecciona esta app en el selector."
        layoutDatos.visibility      = View.GONE
        btnSimularEnvio.visibility  = View.GONE
    }

    private fun simularEnvio() {
        val numero = tvNumero.text.toString().removePrefix("Para: ")
        Toast.makeText(this, "SMS simulado enviado a $numero", Toast.LENGTH_LONG).show()
        tvEstado.text = "✅ Mensaje enviado (simulado)"
    }
}