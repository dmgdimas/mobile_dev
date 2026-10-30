package com.example.films

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var tvMovie: TextView
    private lateinit var btnShow: Button
    private lateinit var btnReset: Button

    private lateinit var originalMovies: List<String>
    private var availableMovies: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvMovie = findViewById(R.id.tvMovie)
        btnShow = findViewById(R.id.btnShow)
        btnReset = findViewById(R.id.btnReset)

        originalMovies = resources.getStringArray(R.array.movies_array).toList()

        resetMovies()

        btnShow.setOnClickListener {
            if (availableMovies.isEmpty()) {
                tvMovie.text = getString(R.string.msg_empty)
            } else {
                val randomIndex = (0 until availableMovies.size).random()
                val randomMovie = availableMovies[randomIndex]
                availableMovies.removeAt(randomIndex)

                tvMovie.text = randomMovie
            }
        }

        btnReset.setOnClickListener {
            resetMovies()
            tvMovie.text = getString(R.string.msg_reset)
        }
    }

    private fun resetMovies() {
        availableMovies.clear()
        availableMovies.addAll(originalMovies)
    }
}