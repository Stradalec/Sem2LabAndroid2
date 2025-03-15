package com.example.sem2labandroid2

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import java.io.IOException

@ExperimentalCoroutinesApi
class RickAndMortyViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()
    private lateinit var api: RickAndMortyApi
    private lateinit var viewModel: RickAndMortyViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        api = mockk()
        viewModel = RickAndMortyViewModel(api)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun fetchCharactersNetworkError() = runTest {
        coEvery { api.getCharacter(1) } throws IOException("Network error")

        viewModel.fetchCharacters(1, 2)

        coVerify { api.getCharacter(1) }
        assert(viewModel.error.value == "Ошибка сети. Проверьте подключение к интернету.")
    }
}

