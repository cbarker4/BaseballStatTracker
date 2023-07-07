package com.example.baseballtracker

import android.annotation.SuppressLint
import android.os.Bundle

import android.widget.EditText
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView


class StatsActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)

        var table = findViewById<TableLayout>(R.id.tablelayout)
        for (i in 0..50) {
            val row = TableRow(this)
            //row.background(R.drawable.cell)
            val lp: TableRow.LayoutParams =
                TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT)

            row.setLayoutParams(lp)
            //row.background(R.drawable.cell)
            for (j in 0..10){
            val textView = TextView(this)

            textView.text = "Name $i"
            textView.setBackgroundResource(R.drawable.cell)


            row.addView(textView)}
            table.addView(row, i)
        }
    }



}


