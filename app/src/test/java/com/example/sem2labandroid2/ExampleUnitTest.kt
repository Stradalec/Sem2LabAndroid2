package com.example.sem2labandroid2

import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import retrofit2.HttpException

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}
class RickAndMortyApiTest {

    private lateinit var api: RickAndMortyApi

    @Before
    fun setup() {
        api = RetrofitClient.RickAndMorty
    }

    @Test
    fun `getCharacter returns valid data`() = runBlocking {
        val character = api.getCharacter(1)
        assertEquals("Rick Sanchez", character.name)
        assertEquals("Alive", character.status)
    }

    @Test(expected = HttpException::class)
    fun `getCharacter with invalid id throws exception`(): Unit = runBlocking {
        api.getCharacter(99999)
    }
}
