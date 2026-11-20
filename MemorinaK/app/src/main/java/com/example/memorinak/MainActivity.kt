package com.example.memorinak

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val cardFaces = listOf(
        R.drawable.card_1, R.drawable.card_1,
        R.drawable.card_2, R.drawable.card_2,
        R.drawable.card_3, R.drawable.card_3,
        R.drawable.card_4, R.drawable.card_4,
        R.drawable.card_5, R.drawable.card_5,
        R.drawable.card_6, R.drawable.card_6,
        R.drawable.card_7, R.drawable.card_7,
        R.drawable.card_8, R.drawable.card_8
    ).shuffled()

    private val cardBack = R.drawable.squarecat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Создаем главный контейнер и сразу центрируем его содержимое
        val layout = LinearLayout(applicationContext).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER // Центрируем всё по вертикали и горизонтали
            setPadding(16, 16, 16, 16) // Отступы от краев экрана
        }

        val density = resources.displayMetrics.density
        val marginDp = 8f
        val marginPx = (marginDp * density).toInt()

        val mainPaddingPx = (16f * density).toInt() // Те же 16dp, что и в setPadding
        val screenWidth = resources.displayMetrics.widthPixels

        // 2. Корректный расчет доступной ширины (экран минус левый и правый паддинги)
        val availableWidth = screenWidth - (2 * mainPaddingPx)

        // 4 карточки и 5 промежутков (слева, 3 между ними, справа)
        val cardSizePx = (availableWidth - (5 * marginPx)) / 4

        val params = LinearLayout.LayoutParams(cardSizePx, cardSizePx).apply {
            setMargins(marginPx, marginPx, marginPx, marginPx)
        }

        val cardViews = ArrayList<ImageView>()

        for (i in 0 until 16) {
            val faceResId = cardFaces[i]

            cardViews.add(ImageView(applicationContext).apply {
                setImageResource(cardBack)
                layoutParams = params
                scaleType = ImageView.ScaleType.CENTER_CROP
                tag = faceResId
                setOnClickListener(colorListener)
                isClickable = true
            })
        }

        // 3. Создаем ряды и тоже центрируем их содержимое
        val rows = Array(4) {
            LinearLayout(applicationContext).apply {
                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.CENTER // Центрируем карточки внутри ряда
            }
        }

        var count = 0
        for (view in cardViews) {
            val rowIndex = count / 4
            rows[rowIndex].addView(view)
            count++
        }

        for (row in rows) {
            layout.addView(row)
        }

        setContentView(layout)
    }

    val colorListener = View.OnClickListener { view ->
        val imageView = view as ImageView

        GlobalScope.launch(Dispatchers.Main) {
            val faceResId = imageView.tag as Int

            imageView.setImageResource(faceResId)
            imageView.isClickable = false

            delay(1000)

            imageView.setImageResource(cardBack)
            imageView.isClickable = true
        }
    }
}