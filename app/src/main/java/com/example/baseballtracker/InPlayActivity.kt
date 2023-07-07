package com.example.baseballtracker

import android.content.Intent
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import android.widget.LinearLayout.LayoutParams
import androidx.core.graphics.red


const val IN_PLAY_OUTCOME = "com.example.baseballtracker.in_play_outcome"

class InPlayActivity : AppCompatActivity() {
    private lateinit var buttonArray: Array<Button>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_in_play)

        // create buttons
        val layout = findViewById<LinearLayout>(R.id.in_play_layout)
        val outcomes = InPlayOutcome.values()

        buttonArray = Array<Button>(outcomes.size) { Button(this) }
        for (i in buttonArray.indices) {
            val params = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
            params.setMargins(10,10,10,10)
            params.weight = 1f
            buttonArray[i].layoutParams = params
            buttonArray[i].text = outcomes[i].text
            buttonArray[i].textSize = 30f

            buttonArray[i].setOnClickListener { onButtonClicked(i) }

            layout.addView(buttonArray[i])
        }
    }

    private fun onButtonClicked(i: Int) {
        val intent = Intent()
        intent.putExtra(IN_PLAY_OUTCOME, i)
        setResult(RESULT_OK, intent)
        finish()
    }
}