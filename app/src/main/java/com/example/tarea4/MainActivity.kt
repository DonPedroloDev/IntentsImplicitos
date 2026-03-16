package com.example.tarea4

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val buttonGoToA = findViewById<Button>(R.id.buttonGoToA)
        buttonGoToA.setOnClickListener {
            val intent = Intent(this, SendTextActivity::class.java)
            startActivity(intent)
        }

        // Punto C: abrir la pantalla que simula ser un manejador de SMS
        val buttonGoToC = findViewById<Button>(R.id.buttonGoToC)
        buttonGoToC.isEnabled = true
        buttonGoToC.setOnClickListener {
            val intent = Intent(this, SmsHandlerActivity::class.java)
            startActivity(intent)
        }
    }
}