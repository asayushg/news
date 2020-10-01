package com.enigmatech.newszone.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(newsCacheEntity: NewsCacheEntity): Long

    @Query("SELECT * FROM news ORDER BY timestamp DESC LIMIT 20")
    suspend fun get(): List<NewsCacheEntity>

}