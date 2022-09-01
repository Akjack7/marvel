package com.example.marvel.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.marvel.R
import com.example.marvel.domain.models.Character
import com.example.marvel.navigation.MarvelScreens
import com.example.marvel.screens.mainScreen.AllHeroesViewModel

@Composable
fun HeroList(viewModel: AllHeroesViewModel,navController: NavController) {
    val heroList = viewModel.data.value.data
    LazyColumn() {
        items(items = heroList ?: emptyList()) { item ->
            HeroItem(character = item,navController)
        }
    }
}

@Composable
fun HeroItem(character: Character,navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate("${MarvelScreens.DetailScreen.name}/${character.id}")
            }
            .padding(10.dp)
            .height(200.dp), elevation = 6.dp, shape = RoundedCornerShape(10.dp)
    ) {
        Box() {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(character.imageUrl)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.ic_captain_america),
                contentDescription = stringResource(R.string.description),
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth(),
                alignment = Alignment.Center
            )
            Box(
                modifier = Modifier
                    .background(Color.Black.copy(alpha = 0.5f))
                    .fillMaxWidth()
                    .padding(vertical = 20.dp)
                    .align(alignment = Alignment.BottomCenter)
            ) {
                Text(
                    text = character.name,
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = Color.White, textAlign = TextAlign.Center,
                )
            }
        }
    }
}