package com.enigmatech.newszone.network

import com.enigmatech.newszone.model.Article
import com.enigmatech.newszone.model.News
import com.enigmatech.newszone.model.Source
import com.enigmatech.newszone.utils.network.EntityMapper
import javax.inject.Inject

class NetworkMapper
@Inject
constructor() :
    EntityMapper<Article, News> {
    override fun mapFromEntity(entity: Article): News {
        return News(
            sourceName = entity.source.name,
            title = entity.title,
            image = entity.urlToImage,
            time = entity.publishedAt.substring(0, 10),
            url = entity.url
        )
    }

    override fun mapToEntity(domainModel: News): Article {
        val list = mutableListOf<Article>()
        return Article(
            source = Source("", domainModel.sourceName),
            title = domainModel.title,
            publishedAt = domainModel.time,
            url = domainModel.url,
            urlToImage = domainModel.image!!
        )
    }

    fun mapFromEntityList(entities: List<Article>): List<News> {
        return entities.map { mapFromEntity(it) }
    }

}