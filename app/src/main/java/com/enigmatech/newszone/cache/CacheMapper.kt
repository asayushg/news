package com.enigmatech.newszone.cache

import com.enigmatech.newszone.model.Article
import com.enigmatech.newszone.model.News
import com.enigmatech.newszone.model.Source
import com.enigmatech.newszone.utils.network.EntityMapper
import javax.inject.Inject

class CacheMapper
@Inject
constructor() :
    EntityMapper<NewsCacheEntity, News> {
    override fun mapFromEntity(entity: NewsCacheEntity): News {
        return News(
            sourceName = entity.sourceName,
            title = entity.title,
            image = entity.image,
            time = entity.time,
            url = entity.url
        )
    }

    override fun mapToEntity(domainModel: News): NewsCacheEntity {
        return NewsCacheEntity(
            sourceName = domainModel.sourceName,
            title = domainModel.title,
            image = domainModel.image!!,
            time = domainModel.time,
            url = domainModel.url
        )
    }

    fun mapFromEntityList(entities: List<NewsCacheEntity>): List<News> {
        return entities.map { mapFromEntity(it) }
    }

}