package com.example.drinkwaterreminder.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User_notification_table")
data class Notification (
    @PrimaryKey(autoGenerate = true)
    var id : Int ,
    var notificationPreference :Int ,
    var startTime : Int ,
    val finishTime : Int ,
    @ColumnInfo(name = "User_Reminder_Time")
    val reminderTimeOut : Int
)