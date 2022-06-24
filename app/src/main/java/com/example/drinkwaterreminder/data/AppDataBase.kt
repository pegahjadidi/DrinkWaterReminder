package com.example.drinkwaterreminder.data

import android.content.Context
import android.os.Build
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


private const val DATABASE_VERSION = 1
@Database(entities = [User::class,Drink::class,Notification::class],
    version = DATABASE_VERSION)
abstract class AppDataBase : RoomDatabase() {
    abstract val daoInstance : Dao
    companion object {
        private var INSTANCE : AppDataBase? = null
        fun getInstance(context : Context) : AppDataBase {
            synchronized(this) {
                var instance : AppDataBase? = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDataBase::class.java,
                        "AppDataBase"
                    ).build()
                }
                return instance
            }
        }
    }
}