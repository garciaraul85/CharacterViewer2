package com.xfinity.data.remote

import com.xfinity.data.model.response.Character

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CharactersService {
    @GET("/")
    fun getCharacters(@Query("q") q: String, @Query("format") format: String): Single<Character>
}