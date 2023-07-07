package com.example.baseballtracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView

const val HOME_PLAYER_STATS = "com.example.baseballtracker.home_player_stats"
const val AWAY_PLAYER_STATS = "com.example.baseballtracker.away_player_stats"
const val HOME_PITCHER_STATS = "com.example.baseballtracker.home_pitcher_stats"
const val AWAY_PITCHER_STATS = "com.example.baseballtracker.away_pitcher_stats"

class GameStatsActivity : AppCompatActivity() {
    lateinit var homeBattingGameStats: HashMap<String, PlayerGameStats>
    lateinit var awayBattingGameStats: HashMap<String, PlayerGameStats>
    lateinit var homePitchingGameStats: HashMap<String, PitcherGameStats>
    lateinit var awayPitchingGameStats: HashMap<String, PitcherGameStats>
    lateinit var homeAwayRadioGroup: RadioGroup
    lateinit var pitchingBattingRadioGroup: RadioGroup
    lateinit var backButton: Button
    var homeActive = true
    var pitchingActive = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_stats)

        homeBattingGameStats = intent.getSerializableExtra(HOME_PLAYER_STATS) as HashMap<String, PlayerGameStats>
        awayBattingGameStats = intent.getSerializableExtra(AWAY_PLAYER_STATS) as HashMap<String, PlayerGameStats>
        homePitchingGameStats = intent.getSerializableExtra(HOME_PITCHER_STATS) as HashMap<String, PitcherGameStats>
        awayPitchingGameStats = intent.getSerializableExtra(AWAY_PITCHER_STATS) as HashMap<String, PitcherGameStats>


        homeAwayRadioGroup = findViewById(R.id.game_stats_home_away_radio_group)
        pitchingBattingRadioGroup = findViewById(R.id.game_stats_pitching_batting_radio_group)
        backButton = findViewById(R.id.back_button)

        backButton.setOnClickListener { onBackButtonClicked() }

        val homeBattingFragment: Fragment = BattingGameStatsFragment.Companion.newInstance(homeBattingGameStats)

        supportFragmentManager.beginTransaction().add(R.id.game_stats_fragment_container_view, homeBattingFragment).commit()

        // home away radio group
        homeAwayRadioGroup.setOnCheckedChangeListener { radioGroup, i ->
            homeActive = i == R.id.home_radio_button
            replaceFragment()

        }

        // pitching hitting radio group
        pitchingBattingRadioGroup.setOnCheckedChangeListener { radioGroup, i ->

            pitchingActive = i == R.id.pitching_radio_button
            replaceFragment()

        }
    }

    private fun onBackButtonClicked() {
        finish()
    }

    private fun replaceFragment() {
        supportFragmentManager.beginTransaction().replace(R.id.game_stats_fragment_container_view, getSelectedFragment()).commit()
    }

    private fun getSelectedFragment() : Fragment {
        if (pitchingActive) {
            if (homeActive) {
                return PitchingGameStatsFragment.newInstance(homePitchingGameStats)
            }
            return PitchingGameStatsFragment.newInstance(awayPitchingGameStats)
        }
        if (homeActive) {
            return BattingGameStatsFragment.newInstance(homeBattingGameStats)
        }
        return BattingGameStatsFragment.newInstance(awayBattingGameStats)
    }
}