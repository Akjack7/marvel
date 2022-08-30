package com.example.marvel.ui.general

import Comics
import Events
import Results
import Series
import Stories
import Thumbnail
import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.MainCoroutineRule
import com.example.TestAppDispatcherFactory
import com.example.marvel.data.Resource
import com.example.marvel.data.remote.MarvelRepository
import com.example.marvel.domain.models.toDomain
import com.example.marvel.domain.usecases.GetCharactersUseCase
import com.task.data.error.NETWORK_ERROR
import com.task.data.error.NO_RESULTS
import com.task.data.error.mapper.ErrorMapper
import com.task.usecase.errors.ErrorManager
import getOrAwaitValue
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.*
import org.junit.*


@ExperimentalCoroutinesApi
class GeneralCharactersViewModelTest {
    @MockK
    lateinit var generalViewModel: GeneralCharactersViewModel

    lateinit var item: Results
    lateinit var dispatcherFactory: TestAppDispatcherFactory

    lateinit var getCharactersUseCase: GetCharactersUseCase
    lateinit var marvelRepository: MarvelRepository

    private val mContextMock = mockk<Context>(relaxed = true)
    private var errorMapper = ErrorMapper(mContextMock)

    private val errorManager = ErrorManager(errorMapper)


    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        item = Results(
            1,
            "heroe",
            "descripcion",
            "",
            Thumbnail("url", ""),
            "",
            Comics(1, "", emptyList(), 1),
            Series(1, "", emptyList(), 1),
            Stories(1, "", emptyList(), 1),
            Events(1, "", emptyList(), 1),
            emptyList()
        )
        marvelRepository = mockk()
        dispatcherFactory = TestAppDispatcherFactory()
        getCharactersUseCase = GetCharactersUseCase(marvelRepository)

        generalViewModel =
            GeneralCharactersViewModel(dispatcherFactory, getCharactersUseCase, errorManager)
    }

    @Test
    fun `check if the list has data`() = runTest(StandardTestDispatcher()) {
        coEvery { marvelRepository.getCharacters() } returns flow {
            emit(Resource.Loading(data = listOf(item)))
        }
        generalViewModel.getCharacters()

        advanceUntilIdle()
        val result = generalViewModel.allCharactersState.getOrAwaitValue()
        assert(result is Resource.Success)
        Assert.assertEquals(listOf(item.toDomain()), (result as Resource.Success).data)
    }

    @Test
    fun `check if the list has not data`() = runTest(StandardTestDispatcher()) {
        coEvery { marvelRepository.getCharacters() } returns flow {
            emit(Resource.Loading(data = emptyList()))
        }
        generalViewModel.getCharacters()

        advanceUntilIdle()
        val result = generalViewModel.allCharactersState.getOrAwaitValue()
        assert(result is Resource.DataError)
        Assert.assertEquals(NO_RESULTS, generalViewModel.allCharactersState.value?.errorCode)
    }

    @Test
    fun `check if emit network error`() = runTest(StandardTestDispatcher()) {
        coEvery { marvelRepository.getCharacters() } returns flow {
            emit(Resource.DataError(NETWORK_ERROR))
        }
        generalViewModel.getCharacters()

        advanceUntilIdle()
        val result = generalViewModel.allCharactersState.getOrAwaitValue()
        assert(result is Resource.DataError)
        Assert.assertEquals(NETWORK_ERROR, generalViewModel.allCharactersState.value?.errorCode)
    }

}