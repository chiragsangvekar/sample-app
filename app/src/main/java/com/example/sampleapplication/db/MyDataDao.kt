package com.example.sampleapplication.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.sampleapplication.ListDataUIModel

@Dao
interface MyDataDao {
    @Query("SELECT * FROM `my-db` where uId =:uniqueId")
    fun findData(uniqueId: Long): MyData

    @Insert
    fun insertAll(data: List<MyData>)

    @Delete
    fun delete(data: MyData)
}