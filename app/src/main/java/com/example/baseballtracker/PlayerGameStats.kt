package com.example.baseballtracker

import android.util.Log

class PlayerGameStats : java.io.Serializable {
    var atBats = 0
    var hits = 0
    var singles = 0
    var doubles = 0
    var triples = 0
    var homeRuns = 0
    var rbis = 0
    var walks = 0
    var strikeOuts = 0

    fun getAvg(): String {
        var avg: Double = 0.0
        if (atBats > 0) {
            avg = hits.toDouble() / (atBats - walks)
        }
        return String.format("%.3f", avg)
    }

    fun getObp(): String {
        var obp: Double = 0.0
        if (atBats + walks > 0) {
            obp = (hits + walks) / (atBats + walks).toDouble()
        }
        return String.format("%.3f", obp)
    }

    fun getOps(): String {
        var obp: Double = 0.0
        if (atBats > 0) {
            obp = (hits + walks) / atBats.toDouble()
        }
        var slg: Double = 0.0
        if (atBats > 0) {
            val totalBases = singles + 2 * doubles + 3 * triples + 4 * homeRuns
            slg = totalBases.toDouble() / atBats
        }
        val ops: Double = obp + slg
        Log.d("Ops", ops.toString())
        Log.d("abs", atBats.toString())
        return String.format("%.3f", ops)
    }

    fun getAb(): String {
        return atBats.toString()
    }

    fun getHr(): String {
        return homeRuns.toString()
    }

    fun getK(): String {
        return strikeOuts.toString()
    }
}