package com.example.baseballtracker

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class PracticeMainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practice_main)

//        val pitching = findViewById<Button>(R.id.Pitching)
//        pitching.setOnClickListener {
//            val intent = Intent(this, PitchingActivity::class.java)
//            startActivity(intent)
//        }
//
//        val bat = findViewById<Button>(R.id.Batting)
//        bat.setOnClickListener {
//            val intent = Intent(this, BattingActivity::class.java)
//            startActivity(intent)
//
//        }
//
//        val dist = findViewById<Button>(R.id.distancetracker)
//        dist.setOnClickListener { val intent = Intent(this, DistanceTrackerActivity::class.java)
//            startActivity(intent)}

    }
}
