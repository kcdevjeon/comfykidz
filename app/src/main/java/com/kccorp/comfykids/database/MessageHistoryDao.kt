package com.kccorp.comfykids.database

import androidx.room.*

@Dao
interface MessageHistoryDao {
//    @Query("SELECT * FROM MessageHistory")
//    fun getAll(): List<MessageHistory>

//    @Query("SELECT * FROM MessageHistory WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<MessageHistory>

//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): MessageHistory
//
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    fun insert(history: MessageHistory)
//
//    @Delete
//    fun delete(id: Int)
}