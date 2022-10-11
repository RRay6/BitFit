package com.example.bitfit

import com.tinyappco.databasedemo.shortDate
import java.io.Serializable
import java.util.*

class Cloud (var id: Int?, var type: String, var title:String, var description: String, var date: Date) : Comparable<Cloud>,
    Serializable {

    override fun compareTo(other: Cloud): Int {
        return date.compareTo(other.date)
    }

//    fun daysLeft() : Int{
//        val difference = (deadline.time - Date().time) / (1000 * 60 * 60 * 24)
//        return difference.toInt()
//    }

    override fun toString(): String {
        return "${date.shortDate()} : $title \n$type\n$description"
    }
}