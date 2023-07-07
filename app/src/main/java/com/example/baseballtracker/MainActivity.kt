package com.example.baseballtracker

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//       val  practice = findViewById<Button>(R.id.practice)
//        practice.setOnClickListener{
//            val intent = Intent(this,PracticeMainActivity::class.java)
//            startActivity(intent)
//        }
        val  play = findViewById<Button>(R.id.playGame)
        play.setOnClickListener{
            val intent = Intent(this,StartActivitey::class.java)
            startActivity(intent)
        }
//        val  stats = findViewById<Button>(R.id.stats)
//        stats.setOnClickListener{
//            val intent = Intent(this,StatsActivity::class.java)
//            startActivity(intent)
//        }

        findViewById<Button>(R.id.distancetracker).setOnClickListener{
            val intent = Intent(this,DistanceTrackerActivity::class.java)
            startActivity(intent)
        }
        val  settings = findViewById<Button>(R.id.settings)
        settings.setOnClickListener{
            val intent = Intent(this,MainSettingsActivity::class.java)
            startActivity(intent)
        }

    }
}