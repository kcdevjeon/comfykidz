package com.kccorp.comfykids.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MessageHistory::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun messageHistoryDao(): MessageHistoryDao
}