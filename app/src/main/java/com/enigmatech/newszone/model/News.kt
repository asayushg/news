package com.enigmatech.newszone.model

data class News (
    val sourceName : String,
    val title : String,
    val image : String?,
    val time : String,
    val url : String
)