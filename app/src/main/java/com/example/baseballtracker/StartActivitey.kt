package com.example.baseballtracker

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import java.io.File
import java.util.*

class StartActivitey : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_activitey)
        findViewById<Button>(R.id.startbutton).setOnClickListener{start()}

    updatespinner(findViewById<Spinner>(R.id.home_team))
    updatespinner(findViewById<Spinner>(R.id.away_team))
    }
    private fun start() {
        var table = TableManager(this, "teamsplaying")
        var names = Vector<String>()
        table.table.removeAllElements()
        names.add(findViewById<Spinner>(R.id.home_team).selectedItem.toString())
        names.add(findViewById<Spinner>(R.id.away_team).selectedItem.toString())
        names.add(findViewById<EditText>(R.id.innings).text.toString() )
        table.table.add(names)

        table.SaveFile()


        val intent = Intent(this,PlayGameActivity::class.java)
        startActivity(intent)
    }


    fun updatespinner(spin : Spinner){

            var temp = Vector<String>()
            var team = TableManager(this, "teamlist")
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


    }
