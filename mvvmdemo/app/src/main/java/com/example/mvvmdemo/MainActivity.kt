package com.example.mvvmdemo

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    private lateinit var tvCounter: TextView
    private lateinit var btnIncrement: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        initView()
        observeViewModel()
    }

    fun observeViewModel() {
        viewModel.counter.observe(this) { count ->
            tvCounter.text = count.toString()
        }
    }

    fun initView() {
        tvCounter = findViewById(R.id.tv_counter)
        btnIncrement = findViewById(R.id.btn_increment)

        btnIncrement.setOnClickListener {
            viewModel.onIncrementClicked()
        }
    }
}