package com.example.drinkwaterreminder.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User_Drink_Info_Table")
data class Drink (
    @PrimaryKey(autoGenerate = true)

    var id : Int ,
    @ColumnInfo(name = "date")
    var date : String,
    @ColumnInfo(name = "time")
    var time :String ,
    @ColumnInfo(name = "drink")
    var drink : String ,
    @ColumnInfo(name = "amount")
    var amount : String
)