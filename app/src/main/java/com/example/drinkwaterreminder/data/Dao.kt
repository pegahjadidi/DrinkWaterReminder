package com.example.drinkwaterreminder.data

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao


private const val USER_TABLE_NAME = "User_table"
private const val COLUMN_ID = "id"
private const val COLUMN_AGE = "age"
private const val COLUMN_WEIGHT = "weight"
private const val COLUMN_GENDER = "gender"
private const val COLUMN_WATER = "water"

private const val TABLE_NAME_DRINK = "User_Drink_Info_Table"
private const val COLUMN_ID_DRINK = "id"
private const val COLUMN_DATE_DRINK = "date"
private const val COLUMN_TIME_DRINK = "time"
private const val COLUMN_DRINK_DRINK = "drink"
private const val COLUMN_AMOUNT_DRINK = "amount"


private const val TABLE_NAME_NOT = "User_notification_table"
private const val COLUMN_ID_NOT = "id"
private const val COLUMN_PREF_NOT = "notificationPreference"
private const val COLUMN_START_NOT = "startTime"
private const val COLUMN_FINISH_NOT = "finishTime"
private const val COLUMN_REMINDER_NOT = "reminderTimeOut"

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user :User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDrinkData(drink: Drink)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotificationInfo(notification : Notification)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUser(user: User)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateNotificationInfo(notification: Notification)

    @Delete
    suspend fun deleteSelectedDrinkData(drink : Drink)

    @Query("SELECT *FROM User_table")
    fun getAllUsers() : LiveData<List<User>>

    @Query("SELECT * FROM $USER_TABLE_NAME LIMIT 1")
    suspend fun readUserData() : User

    @Query("SELECT * FROM $USER_TABLE_NAME LIMIT 1")
    fun readData() : LiveData<User>

    @Query("SELECT * FROM $USER_TABLE_NAME LIMIT 1")
    suspend fun readNotificationData() : Notification

    @Query("SELECT $COLUMN_DATE_DRINK , SUM($COLUMN_AMOUNT_DRINK) as Total GROUP BY $COLUMN_DATE_DRINK")
    suspend fun readDrinkSumData() : MutableList<Sum>

    @Query("SELECT $COLUMN_DATE_DRINK , SUM($COLUMN_AMOUNT_DRINK) as Total GROUP BY $COLUMN_DATE_DRINK")
    fun readDrinkData() : LiveData<MutableList<Sum>>

    @Query("SELECT * FROM $USER_TABLE_NAME WHERE $COLUMN_DATE_DRINK = :date")
    suspend fun readDrinkDataSelectedDay(date:String): MutableList<Drink>
}