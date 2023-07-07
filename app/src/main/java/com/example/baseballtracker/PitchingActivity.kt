package com.example.baseballtracker

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import java.util.*

class PitchingActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pitching)

       var teamspian = findViewById<Spinner>(R.id.practiceteam)
        updatespinner(teamspian,"teamlist")
//        findViewById<Spinner>(R.id.practiceteam).onItemSelectedListener = selector();


        // TODO: Get results back
        var ball = findViewById<Button>(R.id.pichBall)
        ball.setOnClickListener{
            val intent = Intent(this,SelectPitch::class.java)
            startActivity(intent)
        }

        var strike = findViewById<Button>(R.id.pitchStrike)
        strike.setOnClickListener{
            val intent = Intent(this,SelectPitch::class.java)
            startActivity(intent)
        }


        var pitcher = findViewById<Spinner>(R.id.pitcher)
        ArrayAdapter.createFromResource(
            this,
            R.array.pitchers,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            pitcher.adapter = adapter
        }

//        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onNothingSelected(parent: AdapterView<>?) {
//            }
//
//            override fun onItemSelected(parent: AdapterView<>?, view: View?, position: Int, id: Long) {
//                val spinnerSelected = position
//            }


    }



    fun updatespinner(spin : Spinner,s:String){

        var temp = Vector<String>()
        var team = TableManager(this, s)
        team.table.forEach{
            temp.add(it[0])
        }
        if (team.table.size>-1){

            var name = temp.toList()

            var homePitcherSpinnerAdapter =
                ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, name)
            spin.adapter = homePitcherSpinnerAdapter
        }

    }
//    fun selector(): AdapterView.OnItemSelectedListener? {}
}