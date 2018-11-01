package com.xfinity.data

import com.xfinity.data.model.response.Character
import com.xfinity.data.remote.CharactersService

import javax.inject.Inject
import javax.inject.Singleton

import io.reactivex.Single

@Singleton
class DataManager @Inject
constructor(private val charactersService: CharactersService) {

    fun getCharacters(q: String): Single<Character> {
        return charactersService.getCharacters(q, FORMAT)
    }

    companion object {
        private val FORMAT = "json"
    }

}