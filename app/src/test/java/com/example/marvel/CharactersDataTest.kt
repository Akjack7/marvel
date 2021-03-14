package com.example.marvel

import Comics
import Events
import Results
import Series
import Stories
import Thumbnail
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.marvel.presentation.general.GeneralCharactersViewModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest


class CharactersDataTest : KoinTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @MockK
    lateinit var viewModel: GeneralCharactersViewModel


    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        viewModel = mockk()
        val item = Results(
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
        every { viewModel.data.value } returns listOf(
            item
        )
        every { viewModel.currentCharacter.value } returns item
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun `check if the list have items`() {
        runBlocking {
            viewModel.data.value?.let { assert(it.isNotEmpty()) }
        }
    }

    @Test
    fun `check if the list have one item`() {
        runBlocking {
            viewModel.data.value?.let { assert(it.size == 1) }
        }
    }

    @Test
    fun `check if the item have data for detail`() {
        runBlocking {
            val item = viewModel.currentCharacter.value
            assert(!item?.name.isNullOrEmpty())
            assert(!item?.thumbnail?.path.isNullOrEmpty())
            assert(!item?.description.isNullOrEmpty())
        }
    }
}