package com.example.guessnums

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GuessActivity : AppCompatActivity() {

    private var min: Int = 0
    private var max: Int = 0
    private var mid: Int = 0

    private lateinit var tvQuestion: TextView
    private lateinit var tvRange: TextView
    private lateinit var btnLess: Button
    private lateinit var btnEqual: Button
    private lateinit var btnGreater: Button
    private lateinit var btnRestart: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guess)

        // Получаем диапазон из Intent
        min = intent.getIntExtra("MIN", 1)
        max = intent.getIntExtra("MAX", 100)

        tvQuestion = findViewById(R.id.tvQuestion)
        tvRange = findViewById(R.id.tvRange)
        btnLess = findViewById(R.id.btnLess)
        btnEqual = findViewById(R.id.btnEqual)
        btnGreater = findViewById(R.id.btnGreater)
        btnRestart = findViewById(R.id.btnRestart)

        updateQuestion()

        btnLess.setOnClickListener {
            max = mid - 1
            checkRangeAndUpdate()
        }

        btnGreater.setOnClickListener {
            min = mid + 1
            checkRangeAndUpdate()
        }

        btnEqual.setOnClickListener {
            tvQuestion.text = "Я угадал! Ваше число: $mid"
            showRestartButton()
        }

        btnRestart.setOnClickListener {
            finish() // Возврат на первый экран
        }
    }

    private fun checkRangeAndUpdate() {
        when {
            min > max -> {
                tvQuestion.text = "Вы ошиблись в ответах, диапазон стал невалидным!"
                hideGuessButtons()
                showRestartButton()
            }
            min == max -> {
                tvQuestion.text = "Я угадал! Ваше число: $min"
                hideGuessButtons()
                showRestartButton()
            }
            else -> {
                updateQuestion()
            }
        }
    }

    private fun updateQuestion() {
        // Бинарный поиск: делим диапазон пополам
        mid = (min + max) / 2
        tvQuestion.text = "Ваше число больше, меньше или равно $mid?"
        tvRange.text = "Текущий диапазон: $min - $max"

        // Примечание: когда остаётся 2 числа (например, 5 и 6), mid будет равен 5.
        // Если пользователь нажмёт "Больше", диапазон станет 6-6, и на следующем шаге число будет угадано.
    }

    private fun hideGuessButtons() {
        btnLess.visibility = View.GONE
        btnEqual.visibility = View.GONE
        btnGreater.visibility = View.GONE
    }

    private fun showRestartButton() {
        btnRestart.visibility = View.VISIBLE
    }
}