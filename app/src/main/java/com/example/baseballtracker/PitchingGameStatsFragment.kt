package com.example.baseballtracker

import android.content.Context

import android.content.Intent

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import android.widget.Button


import java.util.*
import kotlin.collections.HashMap

private const val PITCHER_GAME_STATS = "pitcher_game_stats"

class PitchingGameStatsFragment : Fragment() {
    private var pitcherGameStats: HashMap<String, PitcherGameStats>? = null
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            pitcherGameStats = it.getSerializable(PITCHER_GAME_STATS) as HashMap<String, PitcherGameStats>
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_pitching_game_stats, container, false)

        recyclerView = view.findViewById(R.id.pitching_game_stats_recycler_view)
//        recyclerView.adapter = ItemAdapter(this, activeLineup)
//        Log.d("Test", playerGameStats.toString())
//        recyclerView.adapter = ItemAdapter(this.requireContext(), playerGameStats as HashMap<String, PlayerGameStats>)

        if (pitcherGameStats != null) {
            recyclerView.adapter = ItemAdapter(this.requireContext(), pitcherGameStats as HashMap<String, PitcherGameStats>)
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(pitcherGameStats: HashMap<String,PitcherGameStats>) =
            PitchingGameStatsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(PITCHER_GAME_STATS, pitcherGameStats)
                }
            }
    }

    private class ItemAdapter(
        private val context: Context,
        private val pitcherGameStats: HashMap<String, PitcherGameStats>
    ) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
        class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
            val playerName: TextView = view.findViewById(R.id.player_name)
            val era: TextView = view.findViewById(R.id.era)
            val ip: TextView = view.findViewById(R.id.ip)
            val h: TextView = view.findViewById(R.id.h)
            val er: TextView = view.findViewById(R.id.er)
            val hr: TextView = view.findViewById(R.id.hr)
            val bb: TextView = view.findViewById(R.id.bb)
            val so: TextView = view.findViewById(R.id.so)
            val moreButton: Button = view.findViewById(R.id.show_more_button)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
            val adapterLayout = LayoutInflater.from(parent.context)
                .inflate(R.layout.pitching_stats_list_item, parent, false)

            return ItemViewHolder(adapterLayout)
        }

        override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
            val playerNameString = pitcherGameStats.keys.toList()[position]
            holder.playerName.text = playerNameString
            holder.era.text = pitcherGameStats[playerNameString]!!.getEra()
            holder.ip.text = pitcherGameStats[playerNameString]!!.getIp()
            holder.h.text = pitcherGameStats[playerNameString]!!.getH()
            holder.er.text = pitcherGameStats[playerNameString]!!.getEr()
            holder.hr.text = pitcherGameStats[playerNameString]!!.getHr()
            holder.bb.text = pitcherGameStats[playerNameString]!!.getBb()
            holder.so.text = pitcherGameStats[playerNameString]!!.getSo()
            holder.moreButton.setOnClickListener { showMoreButtonClicked(playerNameString) }
        }

        override fun getItemCount(): Int {
            return pitcherGameStats.size
        }

        fun showMoreButtonClicked(playerNameString: String) {
            val intent = Intent(context, PitcherStatsActivity::class.java)
            intent.putExtra(PITCH_TYPE_BALLS, pitcherGameStats[playerNameString]!!.pitchTypeBalls as HashMap<String, Int>)
            intent.putExtra(PITCH_TYPE_STRIKES, pitcherGameStats[playerNameString]!!.pitchTypeStrikes as HashMap<String, Int>)
            intent.putExtra(PLAYER_NAME, playerNameString)
            context.startActivity(intent)
        }
    }
}