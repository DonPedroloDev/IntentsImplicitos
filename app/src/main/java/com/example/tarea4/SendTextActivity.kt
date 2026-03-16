package com.example.tarea4

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SendTextActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_send_text)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_send_text)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val editTextMessage = findViewById<EditText>(R.id.editTextMessage)
        val buttonSend = findViewById<Button>(R.id.buttonSend)

        buttonSend.setOnClickListener {
            val message = editTextMessage.text.toString()
            if (message.isNotEmpty()) {
                val sendIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, message)
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, "Enviar mensaje usando:")
                startActivity(shareIntent)
            }
        }
    }
}