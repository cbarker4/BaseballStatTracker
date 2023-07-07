package com.example.baseballtracker

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

const val HOME_LINEUP = "com.example.baseballtracker.home_lineup"
const val AWAY_LINEUP = "com.example.baseballtracker.away_lineup"
const val HOME_PLAYER_NAMES = "com.example.baseballtracker.home_player_names"
const val AWAY_PLAYER_NAMES = "com.example.baseballtracker.away_player_names"

class LineupActivity : AppCompatActivity() {
//    lateinit var homeLineup: Lineup
//    lateinit var awayLineup: Lineup
    var homeActive = true
    var homePitcherSpinnerSelected: Int = 0
    var awayPitcherSpinnerSelected: Int = 0
    var homeEnterPlayerSpinnerSelected: Int = 0
    var awayEnterPlayerSpinnerSelected: Int = 0
    lateinit var homePlayerNames: Array<String>
    lateinit var awayPlayerNames: Array<String>
    private lateinit var homeAdapter: ItemAdapter
    private lateinit var awayAdapter: ItemAdapter
    lateinit var saveButton: Button
    lateinit var homeAwayRadioGroup: RadioGroup
//    lateinit var enterPlayerEditText: EditText
    lateinit var enterPlayerSpinner: Spinner
    lateinit var addButton: Button
    lateinit var pitcherSpinner: Spinner
    lateinit var homePitcherSpinnerAdapter: ArrayAdapter<String>
    lateinit var awayPitcherSpinnerAdapter: ArrayAdapter<String>
    lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lineup)

        val homeLineup = intent.getSerializableExtra(HOME_LINEUP) as Lineup
        val awayLineup = intent.getSerializableExtra(AWAY_LINEUP) as Lineup
        homePlayerNames = intent.getStringArrayExtra(HOME_PLAYER_NAMES)!!
        awayPlayerNames = intent.getStringArrayExtra(AWAY_PLAYER_NAMES)!!

        homeAdapter = ItemAdapter(this, homeLineup)
        awayAdapter = ItemAdapter(this, awayLineup)

        saveButton = findViewById(R.id.save_button)
        homeAwayRadioGroup = findViewById(R.id.home_away_radio_group)
//        enterPlayerEditText = findViewById(R.id.enter_player)
        enterPlayerSpinner = findViewById(R.id.enter_player_spinner)
        addButton = findViewById(R.id.add_button)
        pitcherSpinner = findViewById(R.id.pitcher_spinner)

        saveButton.setOnClickListener { onSaveButtonClicked() }
        addButton.setOnClickListener { onAddButtonClicked() }

        recyclerView = findViewById(R.id.lineup_recyler_view)
//        recyclerView.adapter = ItemAdapter(this, activeLineup)
        recyclerView.adapter = homeAdapter


        if (homeLineup.pitcher != "No Pitcher") {
            homePitcherSpinnerSelected = homePlayerNames.indexOf(homeLineup.pitcher)
        }
        if (awayLineup.pitcher != "No Pitcher") {
            awayPitcherSpinnerSelected = awayPlayerNames.indexOf(awayLineup.pitcher)
        }

        homePitcherSpinnerAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, homePlayerNames)
        homePitcherSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        awayPitcherSpinnerAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, awayPlayerNames)
        awayPitcherSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        pitcherSpinner.adapter = homePitcherSpinnerAdapter
        enterPlayerSpinner.adapter = homePitcherSpinnerAdapter

        pitcherSpinner.setSelection(homePitcherSpinnerSelected)


//
//        val pitcherSpinnerAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
//            this,
//            android.R.layout.simple_spinner_dropdown_item,
//            homePlayerNames
//        ).also { adapter ->
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//            pitcherSpinner.adapter = adapter
//        }

        pitcherSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                if (homeActive) {
                    homePitcherSpinnerSelected = position
                }
                else {
                    awayPitcherSpinnerSelected = position
                }

                getActiveAdapter().lineup.pitcher = getActivePlayerNames()[position]
            }
        }

        enterPlayerSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                if (homeActive) {
                    homeEnterPlayerSpinnerSelected = position
                }
                else {
                    awayEnterPlayerSpinnerSelected = position
                }

//                getActiveAdapter().lineup.pitcher = getActivePlayerNames()[position]
            }
        }

        // home away radio group
        homeAwayRadioGroup.setOnCheckedChangeListener { radioGroup, i ->
            if (i == R.id.home_radio_button) {
                homeActive = true
                recyclerView.adapter = homeAdapter
                pitcherSpinner.adapter = homePitcherSpinnerAdapter
                pitcherSpinner.setSelection(homePitcherSpinnerSelected)
                enterPlayerSpinner.adapter = homePitcherSpinnerAdapter
                enterPlayerSpinner.setSelection(homeEnterPlayerSpinnerSelected)
            } else {
                homeActive = false
                recyclerView.adapter = awayAdapter
                pitcherSpinner.adapter = awayPitcherSpinnerAdapter
                pitcherSpinner.setSelection(awayPitcherSpinnerSelected)
                enterPlayerSpinner.adapter = awayPitcherSpinnerAdapter
                enterPlayerSpinner.setSelection(awayEnterPlayerSpinnerSelected)
            }
        }
    }

    private fun getActiveAdapter() : ItemAdapter {
        if (homeActive) {
            return homeAdapter
        }
        return awayAdapter
    }

    private fun getActivePlayerNames() : Array<String> {
        if (homeActive) {
            return homePlayerNames
        }
        return awayPlayerNames
    }

    private fun onAddButtonClicked() {
        // Ignore any leading or trailing spaces
//        val playerName = enterPlayerEditText.text.toString().trim()
        val playerName = enterPlayerSpinner.selectedItem.toString()

        // Clear the EditText so it's ready for another item
//        enterPlayerEditText.setText("")

        // Add the item to the list
        if (playerName.isNotEmpty()) {
//            val lineup = getActiveLineup()
//            lineup.addPlayer(playerName)
//            activeLineup.addPlayer(playerName)
            getActiveAdapter().lineup.addPlayer(playerName)
            getActiveAdapter().notifyDataSetChanged()
        }

//        recyclerView.adapter!!.notifyDataSetChanged()
    }

    private fun onSaveButtonClicked() {
        intent.putExtra(HOME_LINEUP, homeAdapter.lineup)
        intent.putExtra(AWAY_LINEUP, awayAdapter.lineup)
        setResult(RESULT_OK, intent)
        finish()
    }

    private class ItemAdapter(
        private val context: Context,
        val lineup: Lineup
    ) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
        class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
            val textView: TextView = view.findViewById(R.id.player_name)
            val upButton: Button = view.findViewById(R.id.move_up_button)
            val downButton: Button = view.findViewById(R.id.move_down_button)
            val removeButton: Button = view.findViewById(R.id.remove_button)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
            val adapterLayout = LayoutInflater.from(parent.context)
                .inflate(R.layout.lineup_list_item, parent, false)

            return ItemViewHolder(adapterLayout)
        }

        override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
            Log.d("Test", position.toString())
            val playerName = lineup.getPlayerNameByIndex(position)
            holder.textView.text = playerName

            holder.upButton.setOnClickListener { onMoveUpClicked(position) }
            holder.downButton.setOnClickListener { onMoveDownClicked(position) }
            holder.removeButton.setOnClickListener { onRemoveClicked(position) }
        }

        override fun getItemCount(): Int {
            return lineup.battingOrder.size
        }

        fun onMoveUpClicked(position: Int) {
            lineup.movePlayerUp(position)
            this.notifyDataSetChanged()
        }

        fun onMoveDownClicked(position: Int) {
            lineup.movePlayerDown(position)
            this.notifyDataSetChanged()
        }

        fun onRemoveClicked(position: Int) {
            lineup.removePlayerByIndex(position)
            this.notifyItemRemoved(position)
            this.notifyItemRangeChanged(position, lineup.battingOrder.size)
            this.notifyDataSetChanged()
        }
    }
}