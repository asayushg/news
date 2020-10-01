package com.enigmatech.newszone.cache

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "news")
class NewsCacheEntity(

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "source_name")
    var sourceName: String,

    @ColumnInfo(name = "image")
    var image: String,

    @ColumnInfo(name = "time")
    var time: String,

    @ColumnInfo(name = "url")
    var url: String,

    @ColumnInfo(name = "timestamp")
    var timeStamp: String = Date(System.currentTimeMillis()).toString()

) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0

}