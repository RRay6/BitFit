package com.example.bitfit

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import java.util.*

class DBHandler(context: Context) {

    private val db: SQLiteDatabase = context.openOrCreateDatabase("Cloud", Context.MODE_PRIVATE, null)

    init {
        val cloudsCreateQuery =
            "CREATE TABLE IF NOT EXISTS `Clouds` ( `Id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, `Type` TEXT NOT NULL, `Title` TEXT NOT NULL, `Description` TEXT NOT NULL, `Date` INTEGER )"

        db.execSQL(cloudsCreateQuery)
    }


    fun add(cloud: Cloud) {

        val query = "INSERT INTO Clouds (Type, Title, Description, Date) " +
                "VALUES ('${cloud.type}', '${cloud.title}', '${cloud.description}', '${cloud.date.time}')"
        db.execSQL(query)
    }
    //endregion


    private fun clouds(query: String, args: Array<String>?): List<Cloud> {
        val clouds = mutableListOf<Cloud>()

        val cursor = db.rawQuery(query, args)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("Id"))
                val type = cursor.getString(cursor.getColumnIndex("Type"))
                val title = cursor.getString(cursor.getColumnIndex("Title"))
                val description = cursor.getString(cursor.getColumnIndex("Description"))
                val dateLong = cursor.getLong(cursor.getColumnIndex("Date"))
                val date = Date(dateLong)
                val cloud = Cloud(id, type, title, description, date)
                clouds.add(cloud)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return clouds.sorted()
    }


    fun clouds(): List<Cloud> {
        val query = "SELECT * FROM Clouds"
        return clouds(query, null)
    }

    fun update(cloud: Cloud) {
        val contentValues = ContentValues()
        contentValues.put("Type", cloud.type)
        contentValues.put("Title", cloud.title)
        contentValues.put("Description", cloud.description)
        contentValues.put("Deadline", cloud.date.time)
        val args = arrayOf(cloud.id.toString())
        db.update("Clouds",contentValues,"Id = ?",args)
    }


    //region delete
    fun delete(cloud: Cloud) {

        if (cloud.id != null) {
            val whereClause = "Id = ?"
            val whereArgs = arrayOf(cloud.id.toString())
            db.delete("Clouds", whereClause, whereArgs)
        }
    }

}