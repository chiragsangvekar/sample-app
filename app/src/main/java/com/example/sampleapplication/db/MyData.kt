package com.example.sampleapplication.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "my-db")
data class MyData (
    @PrimaryKey(autoGenerate = true) val uId: Int,
    @ColumnInfo(name = "text") val text: String?,
    @ColumnInfo(name = "data") val data: String?
) {
}