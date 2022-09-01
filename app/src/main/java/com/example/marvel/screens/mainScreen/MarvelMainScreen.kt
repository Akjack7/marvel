package com.example.marvel.screens

import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.marvel.component.CustomCircularProgressIndicator
import com.example.marvel.component.HeroList
import com.example.marvel.data.Resource
import com.example.marvel.screens.mainScreen.AllHeroesViewModel

@Composable
fun MarvelMainScreen(navController: NavController, viewModel: AllHeroesViewModel) {

    Surface {
        when (viewModel.data.value) {
            is Resource.DataError -> viewModel.showToastMessage(
                LocalContext.current
            )
            is Resource.Loading -> CustomCircularProgressIndicator()
            is Resource.Success -> HeroList(viewModel = viewModel, navController = navController)
        }
    }
}