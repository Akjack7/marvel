package com.example.marvel.ui.detail

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
import com.example.marvel.data.local.LocalRepository
import com.example.marvel.data.local.dto.CharacterDaoModel
import com.example.marvel.data.remote.MarvelRepository
import com.example.marvel.domain.models.toDomain
import com.example.marvel.domain.usecases.ChangeFavoriteCharacterUseCase
import com.example.marvel.domain.usecases.GetDetailCharacterUseCase
import com.task.data.error.NETWORK_ERROR
import com.task.data.error.NO_INTERNET_CONNECTION
import com.task.data.error.NO_RESULTS
import com.task.data.error.mapper.ErrorMapper
import com.task.usecase.errors.ErrorManager
import getOrAwaitValue
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.*
import org.junit.*


@ExperimentalCoroutinesApi
class CharacterDetailViewModelTest {

    @MockK
    lateinit var detailViewModel: CharacterDetailViewModel

    lateinit var item: Results
    lateinit var localItem: CharacterDaoModel
    lateinit var dispatcherFactory: TestAppDispatcherFactory

    lateinit var getDetailCharacterUseCase: GetDetailCharacterUseCase
    lateinit var changeFavoriteCharacterUseCase: ChangeFavoriteCharacterUseCase
    lateinit var marvelRepository: MarvelRepository
    lateinit var localRepository: LocalRepository

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
            "hero",
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
        localItem = CharacterDaoModel(1, "Hero", "description", "url")
        marvelRepository = mockk()
        localRepository = mockk()
        dispatcherFactory = TestAppDispatcherFactory()
        getDetailCharacterUseCase = GetDetailCharacterUseCase(marvelRepository, localRepository)
        changeFavoriteCharacterUseCase = ChangeFavoriteCharacterUseCase(localRepository)

        detailViewModel =
            CharacterDetailViewModel(
                dispatcherFactory,
                getDetailCharacterUseCase,
                changeFavoriteCharacterUseCase,
                errorManager
            )
    }

    @Test
    fun `check if there is a character in remote result `() = runTest(StandardTestDispatcher()) {
        coEvery { marvelRepository.getCharacterById(1) } returns flow {
            emit(Resource.Loading(data = item))
        }
        coEvery { localRepository.getCharacterById(1) } returns flow {
            emit(Resource.Loading(data = null))
        }
        detailViewModel.getCharacter(1)

        advanceUntilIdle()
        val result = detailViewModel.characterState.getOrAwaitValue()
        assert(result is Resource.Success)
        Assert.assertEquals(item.toDomain(), (result as Resource.Success).data)
        Assert.assertEquals(item.toDomain(), detailViewModel.characterState.value?.data)
    }

    @Test
    fun `check if there is a character in local result and it is favorite`() =
        runTest(StandardTestDispatcher()) {
            coEvery { marvelRepository.getCharacterById(1) } returns flow {
                emit(Resource.Loading(data = item))
            }
            coEvery { localRepository.getCharacterById(1) } returns flow {
                emit(Resource.Loading(data = localItem))
            }
            val domainCharacter = localItem.toDomain().copy(isFavorite = true)
            detailViewModel.getCharacter(1)

            advanceUntilIdle()
            val result = detailViewModel.characterState.getOrAwaitValue()
            assert(result is Resource.Success)
            Assert.assertEquals(domainCharacter, (result as Resource.Success).data)
            Assert.assertEquals(domainCharacter, detailViewModel.characterState.value?.data)
        }

    @Test
    fun `check if emit network error `() = runTest(StandardTestDispatcher()) {
        coEvery { marvelRepository.getCharacterById(1) } returns flow {
            emit(Resource.DataError(NETWORK_ERROR))
        }
        coEvery { localRepository.getCharacterById(1) } returns flow {
            emit(Resource.Loading(data = null))
        }
        detailViewModel.getCharacter(1)

        advanceUntilIdle()
        val result = detailViewModel.characterState.getOrAwaitValue()
        assert(result is Resource.DataError)
        Assert.assertEquals(NETWORK_ERROR, detailViewModel.characterState.value?.errorCode)
        Assert.assertEquals(NETWORK_ERROR, result.errorCode)
    }

    @Test
    fun `check if emit internet error `() = runTest(StandardTestDispatcher()) {
        coEvery { marvelRepository.getCharacterById(1) } returns flow {
            emit(Resource.DataError(NO_INTERNET_CONNECTION))
        }
        coEvery { localRepository.getCharacterById(1) } returns flow {
            emit(Resource.Loading(data = null))
        }
        detailViewModel.getCharacter(1)

        advanceUntilIdle()
        val result = detailViewModel.characterState.getOrAwaitValue()
        assert(result is Resource.DataError)
        Assert.assertEquals(NO_INTERNET_CONNECTION, detailViewModel.characterState.value?.errorCode)
        Assert.assertEquals(NO_INTERNET_CONNECTION, result.errorCode)
    }

    @Test
    fun `check if emit local character without connexion `() = runTest(StandardTestDispatcher()) {
        coEvery { marvelRepository.getCharacterById(1) } returns flow {
            emit(Resource.DataError(NETWORK_ERROR))
        }
        coEvery { localRepository.getCharacterById(1) } returns flow {
            emit(Resource.Loading(data = localItem))
        }
        detailViewModel.getCharacter(1)
        val domainCharacter = localItem.toDomain().copy(isFavorite = true)


        advanceUntilIdle()
        val result = detailViewModel.characterState.getOrAwaitValue()
        assert(result is Resource.Success)
        Assert.assertEquals(domainCharacter, detailViewModel.characterState.value?.data)
        Assert.assertEquals(domainCharacter, result.data)
    }

    @Test
    fun `check if emit no results error `() = runTest(StandardTestDispatcher()) {
        coEvery { marvelRepository.getCharacterById(1) } returns flow {
            emit(Resource.Loading(data = null))
        }
        coEvery { localRepository.getCharacterById(1) } returns flow {
            emit(Resource.Loading(data = null))
        }
        detailViewModel.getCharacter(1)

        advanceUntilIdle()
        val result = detailViewModel.characterState.getOrAwaitValue()
        assert(result is Resource.DataError)
        Assert.assertEquals(NO_RESULTS, detailViewModel.characterState.value?.errorCode)
        Assert.assertEquals(NO_RESULTS, result.errorCode)
    }


}