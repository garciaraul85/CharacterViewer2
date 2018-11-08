package com.xfinity.data

import com.xfinity.data.model.response.Character
import com.xfinity.data.model.response.RelatedTopic
import com.xfinity.data.remote.CharactersService

import javax.inject.Inject
import javax.inject.Singleton

import io.reactivex.Single

@Singleton
class DataManager @Inject
constructor(private val charactersService: CharactersService) {

    fun getCharacters(q: String): Single<List<RelatedTopic>?> {
        return charactersService.getCharacters(q, FORMAT).map(Character::relatedTopics)
    }

    companion object {
        private val FORMAT = "json"
    }

}