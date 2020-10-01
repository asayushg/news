package com.enigmatech.newszone.network

import com.enigmatech.newszone.model.Article
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class NewsNetworkEntity(

    @SerializedName("status")
    @Expose
    var status: String,

    @SerializedName("totalResults")
    @Expose
    var totalResults: String,

    @SerializedName("articles")
    @Expose
    var articles: List<Article>

)