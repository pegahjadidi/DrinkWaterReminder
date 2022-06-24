package com.example.drinkwaterreminder.data

import androidx.lifecycle.LiveData

class AppRepository(private val dao: Dao) {
    val allUsers = dao.getAllUsers()

    fun readData() : LiveData<User> = dao.readData()
    fun readDrinkData(): LiveData<MutableList<Sum>> = dao.readDrinkData()
    suspend fun insertUser(user :User): Unit = dao.insertUser(user)
    suspend fun insertDrinkData(drink: Drink): Unit = dao.insertDrinkData(drink)
    suspend fun insertNotificationInfo(notification : Notification): Unit = dao.insertNotificationInfo(notification)
    suspend fun updateUser(user: User) : Unit = dao.updateUser(user)
    suspend fun updateNotificationInfo(notification: Notification): Unit = dao.updateNotificationInfo(notification)
    suspend fun readUserData() : User = dao.readUserData()
    suspend fun readNotificationData() : Notification = dao.readNotificationData()
    suspend fun deleteSelectedDrinkData(drink : Drink): Unit = dao.deleteSelectedDrinkData(drink)
    suspend fun readDrinkSumData(): MutableList<Sum>? = dao.readDrinkSumData()
    suspend fun readDrinkDataSelectedDay(date: String): MutableList<Drink>? {
        return dao.readDrinkDataSelectedDay(date)
    }



}