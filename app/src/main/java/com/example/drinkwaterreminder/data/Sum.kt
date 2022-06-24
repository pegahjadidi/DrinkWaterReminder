package com.example.drinkwaterreminder.data

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "Sum")
data class Sum (
    var date : String ,
    @ColumnInfo(name = "Total")
    var total : Int = 0
)
