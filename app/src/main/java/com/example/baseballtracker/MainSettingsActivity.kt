package com.example.baseballtracker

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import java.util.*

class MainSettingsActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_settings)

        findViewById<Button>(R.id.CreateTeam).setOnClickListener{callCreateTeam()}
        findViewById<Button>(R.id.removeteam).setOnClickListener{deleteteam()}
        updatespinner(findViewById<Spinner>(R.id.spin))


    }

    private fun deleteteam() {
        var bye = Vector<Vector<String>>()
        var spin =findViewById<Spinner>(R.id.spin)
        var team = TableManager(this,"teamlist")

        team.table.forEach{
            if (it[0] ==spin.selectedItem.toString()){
                bye.add(it)

            }
        }
        bye.forEach{
            team.table.removeElement(it)
        }
        team.SaveFile()



        updatespinner(spin)

    }

    private fun callCreateTeam() {
        val intent = Intent(this,TeamActivity::class.java)
        startActivity(intent)
    }

    fun updatespinner(spin:Spinner){
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