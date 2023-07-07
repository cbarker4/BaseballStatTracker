package com.example.baseballtracker


import android.annotation.SuppressLint
import android.content.Context
import java.io.File
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.Vector



class TableManager (){
    lateinit var context : Context
    var table = Vector<Vector<String>>()
    lateinit var Filename: String
    constructor(context:Context,Filename:String) : this() {
        this.context = context
        this.Filename = Filename + ".csv"
        try {


            val path = "context!!.team"
            val inputStream: InputStream = File("${context.filesDir}",this.Filename).inputStream()
            inputStream.bufferedReader().useLines {
                it.forEach {
                    var word = ""
                    var line = Vector<String>()
                    it.forEach {
                        if (it == ',') {
                            line.add(word)
                            word = ""
                        } else {
                            word += it.toString()
                        }
                    }
                    this.table.add(line)
                }
            }

//            var file = File("$path/$Filename")
//            file.readLines().forEach {
//                var word = ""
//                var line = Vector<String>()
//                it.forEach {
//                    if (it == ',') {
//                        line.add(word)
//                        word = ""
//                    } else {
//                        word += it.toString()
//                    }
//                }
//                this.table.add(line)
//            }
        }catch (e:Exception){

        }
    }

    fun add(row:Vector<String>){
        table.add(row)
    }


    @SuppressLint("SuspiciousIndentation")
    fun SaveFile(){


      val file = File("${context.filesDir}","${this.Filename}")
      file.createNewFile()


        file.writeText("")


            file.bufferedWriter().use { out->

            table.forEach {
                it.forEach {
                    out.write(it + ",")
                }
                out.write("\n")
            }
        }
    }




}