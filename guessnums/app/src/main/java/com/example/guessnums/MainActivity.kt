package com.example.guessnums

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etMin = findViewById<EditText>(R.id.etMin)
        val etMax = findViewById<EditText>(R.id.etMax)
        val btnStart = findViewById<Button>(R.id.btnStart)

        btnStart.setOnClickListener {
            val minStr = etMin.text.toString()
            val maxStr = etMax.text.toString()

            if (minStr.isBlank() || maxStr.isBlank()) {
                Toast.makeText(this, "Пожалуйста, заполните оба поля", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val min = minStr.toIntOrNull()
            val max = maxStr.toIntOrNull()

            if (min == null || max == null) {
                Toast.makeText(this, "Введите корректные целые числа", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (min >= max) {
                Toast.makeText(this, "Минимальное число должно быть строго меньше максимального", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val intent = Intent(this, GuessActivity::class.java)
            intent.putExtra("MIN", min)
            intent.putExtra("MAX", max)
            startActivity(intent)
        }
    }
}