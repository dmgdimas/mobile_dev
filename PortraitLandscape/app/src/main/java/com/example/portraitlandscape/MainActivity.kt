package com.example.portraitlandscape

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var adapter: ArrayAdapter<CharSequence>
    private lateinit var sharedPreferences: SharedPreferences

    // Массив с идентификаторами ресурсов картинок
    private val imageResources = arrayOf(
        R.drawable.car1,
        R.drawable.car2,
        R.drawable.car3
    )

    // Текущий индекс отображаемой картинки
    private var currentImageIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Инициализация SharedPreferences для сохранения состояния
        sharedPreferences = getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)

        // Загружаем сохраненный индекс картинки (по умолчанию 0)
        currentImageIndex = sharedPreferences.getInt("current_image_index", 0)

        // Устанавливаем картинку при запуске
        updateImage(currentImageIndex)

        // Настройка Spinner (выпадающего списка)
        adapter = ArrayAdapter.createFromResource(this, R.array.pictures, R.layout.item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val spinner = findViewById<Spinner>(R.id.pictures_list)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = this

        // Синхронизируем выбор в Spinner с загруженным индексом
        // (false предотвращает вызов onItemSelected при программной установке)
        spinner.setSelection(currentImageIndex, false)
    }

    /**
     * Обработчик нажатия на кнопку смены картинки
     */
    fun onChangePictureClick(v: View) {
        // Переключаем индекс по кругу: 0 -> 1 -> 2 -> 0
        currentImageIndex = (currentImageIndex + 1) % imageResources.size

        updateImage(currentImageIndex)
        saveImageIndex(currentImageIndex)

        // Обновляем выбор в Spinner
        val spinner = findViewById<Spinner>(R.id.pictures_list)
        spinner.setSelection(currentImageIndex, false)
    }

    /**
     * Метод для обновления ImageView в соответствии с индексом
     */
    private fun updateImage(index: Int) {
        val imageView = findViewById<ImageView>(R.id.picture)
        imageView.setImageResource(imageResources[index])
    }

    /**
     * Метод для сохранения текущего индекса в SharedPreferences
     */
    private fun saveImageIndex(index: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt("current_image_index", index)
        editor.apply() // Асинхронное сохранение
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Toast.makeText(this, "Выбран элемент: $position", Toast.LENGTH_SHORT).show()

        currentImageIndex = position
        updateImage(currentImageIndex)
        saveImageIndex(currentImageIndex)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        Toast.makeText(this, "Элемент не выбран", Toast.LENGTH_SHORT).show()
    }
}