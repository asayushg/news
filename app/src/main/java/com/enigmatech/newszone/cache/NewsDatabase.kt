package com.enigmatech.newszone.cache

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [NewsCacheEntity::class], version = 1)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsDao

    companion object {
        val DATABASE_NAME: String = "news_cache"
    }

}