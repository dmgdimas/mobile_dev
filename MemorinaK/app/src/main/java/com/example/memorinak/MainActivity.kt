package com.example.memorinak

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
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
    private val totalPairs = 8

    private var firstCard: ImageView? = null
    private var isLocked = false
    private var matchedPairs = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(applicationContext).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(16, 16, 16, 16)
        }

        val density = resources.displayMetrics.density
        val marginDp = 8f
        val marginPx = (marginDp * density).toInt()
        val mainPaddingPx = (16f * density).toInt()
        val screenWidth = resources.displayMetrics.widthPixels
        val availableWidth = screenWidth - (2 * mainPaddingPx)
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

        val rows = Array(4) {
            LinearLayout(applicationContext).apply {
                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.CENTER
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
        if (isLocked) return@OnClickListener
        val imageView = view as ImageView
        if (!imageView.isClickable) return@OnClickListener

        imageView.setImageResource(imageView.tag as Int)
        imageView.isClickable = false

        if (firstCard == null) {
            firstCard = imageView
        } else {
            isLocked = true

            val firstResId = firstCard!!.tag as Int
            val secondResId = imageView.tag as Int

            GlobalScope.launch(Dispatchers.Main) {
                delay(1000)

                if (firstResId == secondResId) {
                    matchedPairs++
                    firstCard!!.alpha = 0.5f
                    imageView.alpha = 0.5f

                    if (matchedPairs == totalPairs) {
                        Toast.makeText(applicationContext, "Поздравляем! Вы нашли все пары!", Toast.LENGTH_LONG).show()
                    }
                } else {
                    firstCard!!.setImageResource(cardBack)
                    firstCard!!.isClickable = true
                    firstCard!!.alpha = 1.0f

                    imageView.setImageResource(cardBack)
                    imageView.isClickable = true
                    imageView.alpha = 1.0f
                }

                firstCard = null
                isLocked = false
            }
        }
    }
}