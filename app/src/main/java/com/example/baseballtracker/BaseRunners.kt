package com.example.baseballtracker

import java.util.*

class BaseRunners {
    private var runners: Array<Int?> = Array<Int?>(4) { null }

    fun out(base: Int) : Int? {
        if (runners[base] == null) {
            return null
        }
        val runner = runners[base]
        runners[base] = null

        return runner
    }

    fun move(base: Int) : Set<Int> {
        val runnersScored = mutableSetOf<Int>()

        if (base < 1 || base > 3 || runners[base] == null) {
            return runnersScored
        }
        if (base == 3) {
            runnersScored.add(runners[base]!!)
            runners[base] = null
            return runnersScored
        }
        runnersScored.addAll(move(base + 1))
        runners[base + 1] = runners[base]
        runners[base] = null

        return runnersScored
    }

    fun getRunners() : Array<Int?> {
        return runners
    }

    fun getRunner(base : Int) : Int? {
        if (base in 1..3) {
            return runners[base]
        }
        return null
    }

    fun hit(base: Int): Set<Int> {
        val runnersScored = mutableSetOf<Int>()

        if (base < 1 || base > 4) {
            return runnersScored
        }

        for (i in 0 until base) {
            runnersScored.addAll(move(i))
        }

        return runnersScored
    }

    fun clear() {
        for (i in 0 until 4) {
            runners[i] = null
        }
    }

    fun setBatter(playerId: Int) {
        runners[0] = playerId
    }

}