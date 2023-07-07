package com.example.baseballtracker

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import java.util.*
import kotlin.collections.HashMap


class PlayGameActivity : AppCompatActivity() {

    private lateinit var game: Game
    private lateinit var awayScoreTextView: TextView
    private lateinit var homeScoreTextView: TextView
    private lateinit var inningTextView: TextView
    private lateinit var outsTextView: TextView
    private lateinit var countTextView: TextView
    private lateinit var pitcherTextView: TextView
    private lateinit var batterTextView: TextView
    private lateinit var strikeButton: Button
    private lateinit var ballButton: Button
    private lateinit var inPlayButton: Button
    private lateinit var runScoredButton: Button
    private lateinit var statsButton: Button
    private lateinit var lineupButton: Button
    lateinit var homePlayerNames: List<String>
    lateinit var awayPlayerNames: List<String>
    lateinit var pitchTypes: Array<String>

    private var strike = true
//    private lateinit var strikeArray: Array<Int>
//    private lateinit var ballArray: Array<Int>
//    private lateinit var totalArray: Array<Int>
//    private lateinit var pitchTypes: Array<String>
//    private lateinit var pitchTextViewArray: Array<TextView>
//    private lateinit var strikeTextViewArray: Array<TextView>
//    private lateinit var ballTextViewArray: Array<TextView>
//    private lateinit var totalTextViewArray: Array<TextView>
//    private lateinit var allStrikesTextView: TextView
//    private lateinit var allBallsTextView: TextView
//    private lateinit var allTotalTextView: TextView
//    private var allStrikes: Int = 0
//    private var allBalls: Int = 0
//    private var allPitches: Int = 0
//    private var runsAway: Int = 0
//    private var runsHome: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playgame)

        pitchTypes = resources.getStringArray(R.array.pitch_types)




        // find views
        awayScoreTextView = findViewById(R.id.away_score)
        homeScoreTextView = findViewById(R.id.home_score)
        inningTextView = findViewById(R.id.inning)
        outsTextView = findViewById(R.id.outs)
        countTextView = findViewById(R.id.count)
        pitcherTextView = findViewById(R.id.pitcher)
        batterTextView = findViewById(R.id.batter)

        strikeButton = findViewById(R.id.strike_button)
        ballButton = findViewById(R.id.ball_button)
        inPlayButton = findViewById(R.id.in_play_button)
        runScoredButton = findViewById(R.id.run_button)
        statsButton = findViewById(R.id.stats_button)
        lineupButton = findViewById(R.id.lineup_button)

        strikeButton.setOnClickListener { onStrikeButtonClicked() }
        ballButton.setOnClickListener { onBallButtonClicked() }
        inPlayButton.setOnClickListener { onInPlayButtonClicked() }
        runScoredButton.setOnClickListener { onRunScoredButtonClicked() }
        statsButton.setOnClickListener { onStatsButtonClicked() }
        lineupButton.setOnClickListener { onLineupButtonClicked() }

        var who = TableManager(this, "teamsplaying")
        var home = who.table[0][0]
        var away = who.table[0][1]
        var home_vec = TableManager(this, home).table

        var temp = Vector<String>()
        home_vec.forEach{
            temp.add(it[0])
        }

        var away_vec = TableManager(this, away).table
        var temp2 = Vector<String>()
        away_vec.forEach{
            temp2.add(it[0])
        }
        game = Game(who.table[0][2].toInt(), pitchTypes)

        homePlayerNames = temp.toList()
        awayPlayerNames = temp2.toList()

        for (playerName in homePlayerNames) {
            game.homeLineup.addPlayer(playerName)
            game.homePlayerStats[playerName] = PlayerGameStats()
        }
        for (playerName in awayPlayerNames) {
            game.awayLineup.addPlayer(playerName)
            game.awayPlayerStats[playerName] = PlayerGameStats()
        }
        

//        // initialize arrays
//        pitchTypes = resources.getStringArray(R.array.pitch_types)
//        strikeArray = Array<Int>(pitchTypes.size) {0}
//        ballArray = Array<Int>(pitchTypes.size) {0}
//        totalArray = Array<Int>(pitchTypes.size) {0}

//        // add stat textViews
//        val layout = findViewById<GridLayout>(R.id.stats_layout)
//        allStrikesTextView = findViewById(R.id.all_strikes)
//        allBallsTextView = findViewById(R.id.all_balls)
//        allTotalTextView = findViewById(R.id.all_total)
//        pitchTextViewArray = Array<TextView>(pitchTypes.size) { TextView(this) }
//        strikeTextViewArray = Array<TextView>(pitchTypes.size) { TextView(this) }
//        ballTextViewArray = Array<TextView>(pitchTypes.size) { TextView(this) }
//        totalTextViewArray = Array<TextView>(pitchTypes.size) { TextView(this) }
//        for (i in pitchTypes.indices) {
//            createTextView(pitchTextViewArray[i])
//            createTextView(strikeTextViewArray[i])
//            createTextView(ballTextViewArray[i])
//            createTextView(totalTextViewArray[i])
//            layout.addView(pitchTextViewArray[i])
//            layout.addView(strikeTextViewArray[i])
//            layout.addView(ballTextViewArray[i])
//            layout.addView(totalTextViewArray[i])
//        }

//        val runForAwayButton = findViewById<Button>(R.id.run_for_away_button)
//        val runForHomeButton = findViewById<Button>(R.id.run_for_home_button)
//        runForAwayButton.setOnClickListener { runForAwayClicked() }
//        runForHomeButton.setOnClickListener { runForHomeClicked() }



//        displayStats()
        updateScoreboard()
    }

//    private fun createTextView(textView: TextView) {
//        val params = LinearLayout.LayoutParams(
//            LinearLayout.LayoutParams.MATCH_PARENT,
//            LinearLayout.LayoutParams.WRAP_CONTENT
//        )
//        params.gravity = Gravity.CLIP_HORIZONTAL
//        textView.gravity = Gravity.RIGHT
//        textView.layoutParams = params
//    }

    private fun onStrikeButtonClicked() {
        strike = true;
        val intent = Intent(this, SelectPitch::class.java)
        selectPitchResultLauncher.launch(intent)
    }

    private fun onBallButtonClicked() {
        strike = false
        val intent = Intent(this, SelectPitch::class.java)
        selectPitchResultLauncher.launch(intent)
    }

    val selectPitchResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val pitchTypeIndex = result.data!!.getIntExtra(PITCH_TYPE, -1)

            if (pitchTypeIndex != -1) {
                val pitchType = pitchTypes[pitchTypeIndex]
                if (strike) {
                    game.strike(pitchType)
//                    strikeArray[pitchTypeIndex]++
//                    allStrikes++
                } else {
                    game.ball(pitchType)
//                    ballArray[pitchTypeIndex]++
//                    allBalls++
                }
//                totalArray[pitchTypeIndex]++
//                allPitches++
            }
        } else {
//            pitchTypeIndex = -1
        }
        updateScoreboard()
    }

    private fun onInPlayButtonClicked() {
        val intent = Intent(this, InPlayActivity::class.java)
        inPlayResultLauncher.launch(intent)
    }

    val inPlayResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val outcomeIndex = result.data!!.getIntExtra(IN_PLAY_OUTCOME, -1)
            val outcomes = InPlayOutcome.values()

            when (outcomes[outcomeIndex]) {
                InPlayOutcome.FOUL -> {
                    game.foul()
                }
                InPlayOutcome.OUT -> {
                    game.out()
                }
                InPlayOutcome.SINGLE -> {
                    game.baseHit(1)
                }
                InPlayOutcome.DOUBLE -> {
                    game.baseHit(2)
                }
                InPlayOutcome.TRIPLE -> {
                    game.baseHit(3)
                }
                InPlayOutcome.HOME_RUN -> {
                    game.baseHit(4)
                }
            }
        }
        updateScoreboard()
    }

    private fun onRunScoredButtonClicked() {
        game.runScored()

        updateScoreboard()
    }

    private fun onStatsButtonClicked() {
        val intent = Intent(this, GameStatsActivity::class.java)
        intent.putExtra(HOME_PLAYER_STATS, game.homePlayerStats as HashMap<String, PlayerGameStats>)
        intent.putExtra(AWAY_PLAYER_STATS, game.awayPlayerStats as HashMap<String, PlayerGameStats>)
        intent.putExtra(HOME_PITCHER_STATS, game.homePitcherStats as HashMap<String, PitcherGameStats>)
        intent.putExtra(AWAY_PITCHER_STATS, game.awayPitcherStats as HashMap<String, PitcherGameStats>)
        Log.d("Test", game.homePitcherStats.toString())
        Log.d("Test", game.awayPitcherStats.toString())
        startActivity(intent)
    }

    private fun onLineupButtonClicked() {
        val intent = Intent(this,LineupActivity::class.java)
        intent.putExtra(HOME_LINEUP, game.homeLineup)
        intent.putExtra(AWAY_LINEUP, game.awayLineup)
        intent.putExtra(HOME_PLAYER_NAMES, homePlayerNames.toTypedArray())
        intent.putExtra(AWAY_PLAYER_NAMES, awayPlayerNames.toTypedArray())

        lineupResultLauncher.launch(intent)
    }

    val lineupResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            game.homeLineup = result.data!!.getSerializableExtra(HOME_LINEUP) as Lineup
            game.awayLineup = result.data!!.getSerializableExtra(AWAY_LINEUP) as Lineup
        }
        updateScoreboard()
    }


//    private fun displayStats() {
//
//        allStrikesTextView.text = allStrikes.toString()
//        allBallsTextView.text = allBalls.toString()
//        allTotalTextView.text = allPitches.toString()
//
//
//        for (i in pitchTypes.indices) {
//            pitchTextViewArray[i].text = pitchTypes[i]
//            strikeTextViewArray[i].text = strikeArray[i].toString()
//            ballTextViewArray[i].text = ballArray[i].toString()
//            totalTextViewArray[i].text = totalArray[i].toString()
//
////            strikeTextViewArray
//        }
//    }

    private fun updateScoreboard() {
        awayScoreTextView.text = getString(R.string.away_score, game.runsAway)
        homeScoreTextView.text = getString(R.string.home_score, game.runsHome)
        if (game.homeHitting) {
            inningTextView.text = getString(R.string.inning_bottom, game.inning)
        } else {
            inningTextView.text = getString(R.string.inning_top, game.inning)
        }
        outsTextView.text = getString(R.string.outs, game.outs)
        countTextView.text = getString(R.string.count, game.balls, game.strikes)
        batterTextView.text = getString(R.string.batter, game.getBatterName())
        pitcherTextView.text = getString(R.string.pitcher, game.getPitcherName())
    }

    fun updatespinner(spin : Spinner,file:String){

        var temp = Vector<String>()
        var team = TableManager(this, file)
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