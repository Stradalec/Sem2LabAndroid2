package com.example.sem2labandroid2

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

object RetrofitClient {

    val RickAndMorty: RickAndMortyApi by lazy {

        Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RickAndMortyApi::class.java)
    }
}
interface RickAndMortyApi {
    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id: Int): Character
}