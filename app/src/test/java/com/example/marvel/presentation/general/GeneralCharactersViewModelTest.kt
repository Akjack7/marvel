package com.example.marvel.presentation.general

import Comics
import Events
import Results
import Series
import Stories
import Thumbnail
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.MainCoroutineRule
import com.example.TestAppDispatcherFactory
import com.example.marvel.data.remote.MarvelRepository
import com.example.marvel.domain.models.toDomain
import com.example.marvel.domain.usecases.GetCharactersUseCase
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
        val marvelRepository = mockk<MarvelRepository>()
        coEvery { marvelRepository.getCharacters() } returns flow {
            emit(listOf(item))
        }
        dispatcherFactory = TestAppDispatcherFactory()
        getCharactersUseCase = GetCharactersUseCase(marvelRepository)
    }

    @Test
    fun `check if the list has data`() = runTest(StandardTestDispatcher()) {
        generalViewModel = GeneralCharactersViewModel(dispatcherFactory, getCharactersUseCase)
        generalViewModel.getCharacters()

        advanceUntilIdle()
        val result = generalViewModel.allCharactersState.getOrAwaitValue()
        assert(result is CharactersState.Loaded)
        Assert.assertEquals(listOf(item.toDomain()),(result as CharactersState.Loaded).data)
    }

}