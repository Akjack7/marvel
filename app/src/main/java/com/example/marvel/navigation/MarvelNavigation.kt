package com.example.marvel.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.marvel.screens.MarvelMainScreen
import com.example.marvel.screens.MarvelSplashScreen
import com.example.marvel.screens.detailSreen.MarvelDetailScreen
import com.example.marvel.screens.mainScreen.AllHeroesViewModel
import com.example.marvel.screens.detailSreen.DetailViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun MarvelNavigation() {
    val navController = rememberNavController()
    val allHerosViewModel = getViewModel<AllHeroesViewModel>()
    val detailViewModel = getViewModel<DetailViewModel>()
    NavHost(navController = navController, startDestination = MarvelScreens.MainScreen.name) {
        composable(MarvelScreens.SplashScreen.name) {
            MarvelSplashScreen(navController = navController)
        }
        composable(MarvelScreens.MainScreen.name) {
            MarvelMainScreen(navController = navController, allHerosViewModel)
        }
        val route = MarvelScreens.DetailScreen.name
        composable("$route/{id}", arguments = listOf(navArgument(name = "id") {
            type = NavType.IntType
        })) {
            it.arguments?.getInt("id")?.let { id ->
                MarvelDetailScreen(
                    detailViewModel = detailViewModel,
                    id = id
                )
            }
        }
    }
}