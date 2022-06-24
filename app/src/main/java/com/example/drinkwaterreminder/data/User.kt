package com.example.drinkwaterreminder.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User_table")
data class User (
    @PrimaryKey (autoGenerate = true)

    @ColumnInfo(name = "user_id")
    var id :Int ,
    @ColumnInfo(name = "user_name")
    var userName : String ,
    @ColumnInfo(name = "user_age")
    var age : Int ,
    @ColumnInfo(name = "user_weight")
    var weight : Int ,
    @ColumnInfo(name = "user_gender")
    var gender : String,
    @ColumnInfo(name = "user_Water_amount")
    var waterAmount : Int
        )
