package com.kccorp.comfykids.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class MessageHistory(
    @ColumnInfo(name = "when_msg")
    val whenMsg: String?,
    @ColumnInfo(name = "where_msg")
    val whereMsg: String?,
    @ColumnInfo(name = "what_msg")
    val whatMsg: String?
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}
