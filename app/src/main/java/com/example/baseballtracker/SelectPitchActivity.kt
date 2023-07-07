package com.example.baseballtracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.LinearLayout.LayoutParams


const val PITCH_TYPE = "com.example.baseballtracker.pitch_type"

class SelectPitch : AppCompatActivity() {
    private lateinit var buttonArray: Array<Button>
    private lateinit var pitchTypes: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_pitch)

        // create buttons
        val layout = findViewById<LinearLayout>(R.id.select_pitch_layout)
        pitchTypes = resources.getStringArray(R.array.pitch_types)

        buttonArray = Array<Button>(pitchTypes.size) { Button(this) }
        for (i in buttonArray.indices) {
            Log.d("test", i.toString())
            val params = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
            params.setMargins(10,10,10,10)
            params.weight = 1f
            buttonArray[i].layoutParams = params
            buttonArray[i].text = pitchTypes[i]
            buttonArray[i].textSize = 30f

            buttonArray[i].setOnClickListener { onButtonClicked(i) }

            layout.addView(buttonArray[i])
        }
    }

    private fun onButtonClicked(i: Int) {
        val intent = Intent()
        intent.putExtra(PITCH_TYPE, i)
        setResult(RESULT_OK, intent)
        finish()
    }
}