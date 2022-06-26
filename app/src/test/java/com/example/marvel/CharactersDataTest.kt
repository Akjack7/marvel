package com.example.marvel

import Comics
import Events
import MarvelBase
import Results
import Series
import Stories
import Thumbnail
import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.testing.TestLifecycleOwner
import com.example.marvel.data.local.LocalRepository
import com.example.marvel.data.local.database.CharacterDao
import com.example.marvel.data.remote.MarvelRepository
import com.example.marvel.data.remote.apis.MarvelApi
import com.example.marvel.domain.usecases.ChangeFavoriteCharacterUseCase
import com.example.marvel.domain.usecases.GetCharactersUseCase
import com.example.marvel.domain.usecases.GetDetailCharacterUseCase
import com.example.marvel.presentation.detail.CharacterDetailViewModel
import com.example.marvel.presentation.detail.CharacterState
import com.example.marvel.presentation.general.CharactersState
import com.example.marvel.presentation.general.GeneralCharactersViewModel
import com.example.marvel.utils.AppDispatcherFactory
import com.example.marvel.utils.DispatcherFactory
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest

@ExperimentalCoroutinesApi
class CharactersDataTest : KoinTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()


    @MockK
    lateinit var detailViewModel: CharacterDetailViewModel

    lateinit var item: Results


    val dispatcher: DispatcherFactory = AppDispatcherFactory()


    lateinit var viewModel: GeneralCharactersViewModel


    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        Dispatchers.setMain(UnconfinedTestDispatcher())
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
        val marvelResponse: MarvelApi = mockk(relaxed = true)
        val marvelBase: MarvelBase = mockk(relaxed = true)
        val service = mockk<MarvelApi>(relaxed = true)

        coEvery { marvelBase.data.results } returns listOf(item)
        coEvery { marvelResponse.getCharacters() } returns marvelBase
        val marvelRepository = MarvelRepository(service)
        val localRepository : LocalRepository = mockk(relaxed = true)

        val getCharactersUseCase = GetCharactersUseCase(marvelRepository)
        val getDetailCharactersUseCase = GetDetailCharacterUseCase(marvelRepository,localRepository)
        val changeFavoriteCharacterUseCase: ChangeFavoriteCharacterUseCase = mockk(relaxed = true)

        viewModel = GeneralCharactersViewModel(dispatcher, getCharactersUseCase)
        detailViewModel = CharacterDetailViewModel(dispatcher,getDetailCharactersUseCase,changeFavoriteCharacterUseCase)
    }

    @After
    fun after() {
        stopKoin()
        Dispatchers.resetMain()

    }

    @Test
    fun `check if the list have one item`() {
        runBlocking {
            viewModel.getCharacters()
            viewModel.allCharactersState.observe(TestLifecycleOwner()) {
               if(it is CharactersState.Loaded){
                   assert(it.data.isNotEmpty())
               }
            }
        }
    }


    @Test
    fun `check if the item have data for detail`() {
        runBlocking {
            detailViewModel.getCharacter(1)
            detailViewModel.characterState.observe(TestLifecycleOwner()){
                if(it is CharacterState.Loaded){
                    assert(it.data.name.isNotEmpty())
                }
            }
        }
    }
}