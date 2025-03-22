package com.example.sem2labandroid2

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.viewModelScope
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

@ExperimentalCoroutinesApi
class RickAndMortyViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()
    private lateinit var api: RickAndMortyApi
    private lateinit var viewModel: MainActivityViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        api = mockk()
        viewModel = MainActivityViewModel(api)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun mainActivityViewModel_fetchCharactersNetwork_Error() = runTest {
        coEvery { api.getCharacter(1) } throws IOException("Network error")

        viewModel.fetchCharacters(1, 2)

        coVerify { api.getCharacter(1) }
        assert(viewModel.error.value == "Ошибка сети. Проверьте подключение к интернету.")
    }
    @Test
    fun mainActivityViewModel_fetchCharactersNetwork_Success() = runTest {
        val mockCharacter1 = mockk<Character>()
        val mockCharacter2 = mockk<Character>()
        coEvery { api.getCharacter(1) } returns mockCharacter1
        coEvery { api.getCharacter(2) } returns mockCharacter2

        viewModel.fetchCharacters(1, 2)

        coVerify { api.getCharacter(1) }
        coVerify { api.getCharacter(2) }
        assert(viewModel.characterData.value == listOf(mockCharacter1, mockCharacter2))
    }
    @Test
    fun mainActivityViewModel_fetchCharactersUI_Success() = runTest {
        val mockCharacter = mockk<Character>()
        coEvery { api.getCharacter(any()) } returns mockCharacter

        viewModel.fetchCharacters(1, 1)

        assert(viewModel.characterData.value?.isNotEmpty() == true)
    }
    @Test
    fun mainActivityViewModel_fetchCharactersHTTP_Error() = runTest {
        coEvery { api.getCharacter(1) } throws HttpException(Response.error<Any>(404, ResponseBody.create(null, "")))

        viewModel.fetchCharacters(1, 1)

        assert(viewModel.error.value?.startsWith("Ошибка сервера: 404") == true)
    }
    @Test
    fun mainActivityViewModel_cancelJob_Success() = runTest {
        val job = viewModel.viewModelScope.launch {
            viewModel.fetchCharacters(1, 100)
        }

        viewModel.viewModelScope.cancel()
        advanceUntilIdle()

        assert(viewModel.viewModelScope.coroutineContext[Job]?.isCancelled == true) {
            "ViewModelScope's Job should be cancelled"
        }
    }
}

