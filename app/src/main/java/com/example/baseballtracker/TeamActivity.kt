package com.example.baseballtracker

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.view.isVisible
import java.util.Vector

class TeamActivity : AppCompatActivity() {
    private lateinit var team: TableManager
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team)
        findViewById<Button>(R.id.make_Team).setOnClickListener{makeTeam()}
        findViewById<Button>(R.id.add_player).setOnClickListener{addplayer()}
        findViewById<Button>(R.id.remove_player).setOnClickListener{removeplayer()}
        findViewById<Button>(R.id.add_player).isVisible = false
        findViewById<Button>(R.id.remove_player).isVisible = false
        findViewById<Button>(R.id.player_to_add).isVisible = false



    }

    private fun addplayer() {
        findViewById<EditText>(R.id.player_to_add)
        val id = Player().get_id(this)
        var line = Vector<String>()
        val temp = findViewById<EditText>(R.id.player_to_add)
        line.add(temp.text.toString())
        line.add(id.toString())
        team.table.add(line)
        team.SaveFile()
        temp.setText("")
        updatespinner()
    }



    @SuppressLint("WrongViewCast")
    private fun makeTeam() {

        var teamname = findViewById<EditText>(R.id.team_name).text.toString()
        teamname =  teamname
        team = TableManager(this, teamname)
        findViewById<Button>(R.id.add_player).isVisible = true
        findViewById<Button>(R.id.remove_player).isVisible = true
        findViewById<Button>(R.id.player_to_add).isVisible = true

        var listOfTeams = TableManager(this, "teamlist")
        var temp = Vector<String>()
        temp.add(teamname)
        listOfTeams.table.add(temp)
        listOfTeams.SaveFile()





        updatespinner()

        //pitcherSpinner  = homePitcherSpinnerAdapter

    }


    private fun removeplayer(){
        val spin = findViewById<Spinner>(R.id.player_name)
        var bye = Vector<Vector<String>>()
        var i =0
        team.table.forEach{
            if (it[0] ==spin.selectedItem.toString()){
                bye.add(it)

            }
        }
        bye.forEach{
            team.table.removeElement(it)
        }
        updatespinner()
        team.SaveFile()

    }
//

//
//        val pitcherSpinnerAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
//            this,
//            android.R.layout.simple_spinner_dropdown_item,
//            homePlayerNames
//        ).also { adapter ->
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//            pitcherSpinner.adapter = adapter
//        }


    fun updatespinner(){
        var temp = Vector<String>()
        team.table.forEach{
            temp.add(it[0])
        }
        if (team.table.size>-1){

            var name = temp.toList()
            val spin = findViewById<Spinner>(R.id.player_name)
            var homePitcherSpinnerAdapter =
                ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, name)
            spin.adapter = homePitcherSpinnerAdapter
        }

    }



    }