package com.example.marvel.ui.detail

import Comics
import Events
import MarvelBase
import Results
import Series
import Stories
import Thumbnail
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.MainCoroutineRule
import com.example.TestAppDispatcherFactory
import com.example.marvel.data.local.LocalRepository
import com.example.marvel.data.remote.MarvelRepository
import com.example.marvel.data.remote.service.MarvelApi
import com.example.marvel.domain.models.toDomain
import com.example.marvel.domain.usecases.ChangeFavoriteCharacterUseCase
import com.example.marvel.domain.usecases.GetDetailCharacterUseCase
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.stopKoin


@ExperimentalCoroutinesApi
class CharacterDetailViewModelTest {

    @MockK
    lateinit var detailViewModel: CharacterDetailViewModel

    lateinit var item: Results
    lateinit var dispatcherFactory: TestAppDispatcherFactory

   /* private val testDispatcher = AppDispatcherFactory(
        IO = StandardTestDispatcher()
    )*/

    @MockK
    lateinit var observer: Observer<CharacterState>


    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

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
        dispatcherFactory = TestAppDispatcherFactory()
        val marvelRepository = mockk<MarvelRepository>(relaxed = true)
        val detailUseCase = mockk<GetDetailCharacterUseCase>(relaxed = true)

        coEvery { marvelBase.data.results } returns listOf(item)
        coEvery { marvelResponse.getCharacters() } returns marvelBase
        coEvery { marvelResponse.getCharacterById(1) } returns marvelBase

        coEvery { marvelRepository.getCharacterById(1) } returns flow {
            emit(item)
        }
        val localRepository: LocalRepository = mockk(relaxed = true)
       // coEvery { localRepository.getCharacterById(1) } returns null
        coEvery { detailUseCase.invoke(1) } returns flow {
            emit(item.toDomain())
        }


       /* val getDetailCharactersUseCase =
            GetDetailCharacterUseCase(marvelRepository, localRepository)*/
        val changeFavoriteCharacterUseCase: ChangeFavoriteCharacterUseCase = mockk(relaxed = true)
        coEvery { changeFavoriteCharacterUseCase.invoke(item.toDomain()) } returns flow {

        }

        detailViewModel = CharacterDetailViewModel(
            dispatcherFactory,
            detailUseCase,
            changeFavoriteCharacterUseCase
        )
    }

    @After
    fun tearDown() {
        stopKoin()
        Dispatchers.resetMain()
    }

    @Test
    fun `check if the item have data for detail`() {
        runBlocking {
           /* //create mockk object
            val observer = mockk<Observer<CharacterState>>()

            //create slot
            val slot = slot<CharacterState>()

            //create list to store values
            val list = arrayListOf<CharacterState>()

            //start observing
            detailViewModel.characterState.observeForever(observer)


            //capture value on every call
            every { observer.onChanged(capture(slot)) } answers {

                //store captured value
                list.add(slot.captured)
            }*/

            //val observer = Observer<CharacterState>{}
            detailViewModel.characterState.observeForever(observer)
            detailViewModel.getCharacter(1)
            /*detailViewModel.characterState.observe(TestLifecycleOwner()){
                if(it is CharacterState.Loaded){
                    assert(it.data.name.isNotEmpty())
                }
            }*/
            verifySequence {
                observer.onChanged(CharacterState.Loading)
                observer.onChanged(CharacterState.Loaded(item.toDomain()))
            }
          //  detailViewModel.characterState.observeForever(observer)
            /*when(result){
                CharacterState.Error -> {
                    println("Error")
                }
                is CharacterState.Loaded -> {
                    println("Loaded")
                }

                CharacterState.Loading -> {
                    println("Loading")
                }
                else -> {}
            }*/
          //  val observer = Observer<CharacterState>{}

           // detailViewModel.characterState.observeForever(observer)
            /*val result = detailViewModel.characterState.getOrAwaitValue(time = 10)
                when(result){
                    CharacterState.Error -> {
                        println("Error")
                    }
                    is CharacterState.Loaded -> {
                        println("Loaded")
                    }

                    CharacterState.Loading -> {
                        println("Loading")
                    }
                    else -> {}
                }
*/

           /* val observer = Observer<CharacterState>{}
            val result = detailViewModel.characterState.getOrAwaitValue()
            when(result){
                CharacterState.Error -> {
                    println("Error")
                }
                is CharacterState.Loaded -> {
                    println("Loaded")
                }

                CharacterState.Loading -> {
                    println("Loading")
                }
            }*/
            /*detailViewModel.characterState.observe(TestLifecycleOwner()){
                when(it){
                    CharacterState.Error -> Log.d("Error","Error")
                    is CharacterState.Loaded ->  Log.d("Loaded","Loaded")
                    CharacterState.Loading ->  Log.d("Loading","Loading")
                }*/
               /* if(it is CharacterState.Loaded){
                    assert(it.data.name.isNotEmpty())
                }*/
            }
           /* val expected = listOf(
                CharacterState.Loading,
                CharacterState.Loaded(item.toDomain())
            )
            assertThat(result, `is`(expected))*/

        }

}

inline fun <reified T > LiveData<T>.captureValues(): List<T?> {
    val mockObserver = mockk<Observer<T>>()
    val list = mutableListOf<T?>()
    every { mockObserver.onChanged(captureNullable(list))} just runs
    this.observeForever(mockObserver)
    return list
}