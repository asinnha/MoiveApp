package com.example.myapplication.roomdatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.dataclasses.Results

@Database(entities = [Results::class], version = 1)
abstract class NowShowingDatabase: RoomDatabase()
{

    abstract fun movieResultDao():MovieResultsDao

}