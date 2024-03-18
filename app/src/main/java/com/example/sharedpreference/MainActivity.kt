package com.example.sharedpreference

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import kotlin.random.Random
import android.content.Context

private lateinit var text : EditText
private lateinit var img : ImageView
private var currentImageIdx: Int = -1

var randomIdx : Int = 0
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        text = findViewById(R.id.editText)
        img = findViewById(R.id.img)
        val btn : Button = findViewById(R.id.btn)

        //Ref: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.random/-random/
        btn.setOnClickListener {
            randomIdx = Random.nextInt(1, 100)
            when (randomIdx % 3) {
                0 -> {
                    img.setImageResource(R.drawable.paper)
                    currentImageIdx = 0
                }
                1 -> {
                    img.setImageResource(R.drawable.scissor)
                    currentImageIdx = 1
                }
                2 -> {
                    img.setImageResource(R.drawable.stone)
                    currentImageIdx = 2
                }
                else -> {
                    print("something goes wrong")
                }
            }
        }

        val sharedPreferences = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        currentImageIdx = sharedPreferences.getInt("imageIdx", -1)
        val savedText = sharedPreferences.getString("text", "")
        text.setText(savedText)

        when (currentImageIdx) {
            0 -> img.setImageResource(R.drawable.paper)
            1 -> img.setImageResource(R.drawable.scissor)
            2 -> img.setImageResource(R.drawable.stone)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val sharedPreferences = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putInt("imageIdx", currentImageIdx)
            putString("text", text.text.toString())
            apply()
        }
    }
}