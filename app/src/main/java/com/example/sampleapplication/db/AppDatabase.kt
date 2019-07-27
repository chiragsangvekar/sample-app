package com.example.sampleapplication.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [MyData::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun myDataDao(): MyDataDao

    companion object {

        @Volatile
        private var instance: AppDatabase? = null

        fun get(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildRoomDatabase(context).also { instance = it }
            }
        }

        private fun buildRoomDatabase(context: Context): AppDatabase {
            val buildRoom =
                Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "my-db")
                    .fallbackToDestructiveMigration().addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {

                        }
                    })

            return buildRoom.build()
        }
    }
}