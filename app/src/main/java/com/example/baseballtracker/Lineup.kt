package com.example.baseballtracker

import java.util.*

class Lineup : java.io.Serializable {
    var nextId = 0
    var battingOrder: Vector<Int> = Vector()
    var playerNames: MutableMap<Int, String> = mutableMapOf()
    var pitcher: String = "No Pitcher"

    fun addPlayer(playerName: String) {
        if (pitcher == "No Pitcher") {
            pitcher = playerName
        }

        val playerId = nextId
        nextId++

        battingOrder.add(playerId)
        playerNames[playerId] = playerName
    }

    fun removePlayerByIndex(battingOrderIndex: Int) {
        val playerId = battingOrder[battingOrderIndex]
        battingOrder.removeElementAt(battingOrderIndex)
        playerNames.remove(playerId)
    }

    fun removePlayerById(playerId: Int) {
        val battingOrderIndex = battingOrder.indexOf(playerId)
//        battingOrder.remove(battingOrderIndex)
        battingOrder.removeElementAt(battingOrderIndex)
        playerNames.remove(playerId)
    }

    fun movePlayerUp(playerIndex: Int) {
        if (playerIndex != 0) {
            val playerId = battingOrder[playerIndex]
            val playerBeforeId = battingOrder[playerIndex - 1]
            battingOrder[playerIndex] = playerBeforeId
            battingOrder[playerIndex - 1] = playerId
        }
    }

    fun movePlayerDown(playerIndex: Int) {
        if (playerIndex != battingOrder.size - 1) {
            val playerId = battingOrder[playerIndex]
            val playerAfterId = battingOrder[playerIndex + 1]
            battingOrder[playerIndex] = playerAfterId
            battingOrder[playerIndex + 1] = playerId
        }
    }

    fun getPlayerNameByIndex(playerIndex: Int): String {
        if (playerIndex in 0 until battingOrder.size) {
            val playerId = battingOrder[playerIndex]
            return playerNames[playerId]!!
        } else {
            return ""
        }

    }
}
