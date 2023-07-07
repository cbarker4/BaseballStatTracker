package com.example.baseballtracker


import android.content.Context
import java.util.Vector

class Player() {

fun get_id(context: Context):Int{
    val temp = TableManager(context,"CounterForNewPlayerIds")
    try {


        val out = temp.table[0][0].toInt() + 1
        temp.table[0][0] = (temp.table[0][0].toInt()+1).toString()
        temp.SaveFile()
        return out
    }catch (e:java.lang.Exception){
        var count = Vector<String>()
        count.add("1")
        temp.table.add(count)
        temp.SaveFile()
        return 1
    }
}
}
