package com.enigmatech.newszone.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Article(

    @SerializedName("source")
    @Expose
    var source: Source,

    @SerializedName("title")
    @Expose
    var title: String,

    @SerializedName("publishedAt")
    @Expose
    var publishedAt: String,

    @SerializedName("url")
    @Expose
    var url: String,

    @SerializedName("urlToImage")
    @Expose
    var urlToImage: String = ""
)