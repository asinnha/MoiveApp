package com.example.myapplication.roomdatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.dataclasses.Results

@Dao
interface MovieResultsDao
{
    @Query("SELECT * FROM movie_results")
    fun getAll(): List<Results>

    @Insert
    fun insertAll(results: List<Results>)

    @Query("DELETE FROM movie_results")
    fun deleteAll()

    @Query("SELECT * FROM movie_results ORDER BY releaseDate ASC")
    fun orderOldest() :List<Results>

    @Query("SELECT * FROM movie_results ORDER BY releaseDate DESC")
    fun orderLatest() :List<Results>

}