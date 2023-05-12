package com.example.marvel.screens.detailSreen

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.marvel.R
import com.example.marvel.component.CustomCircularProgressIndicator
import com.example.marvel.data.Resource
import com.example.marvel.domain.models.Character

@Composable
fun MarvelDetailScreen(detailViewModel: DetailViewModel, id: Int) {
    Log.d("DETAIL", id.toString())




     LaunchedEffect(key1 = id) {
         Log.i("LaunchEffect", id.toString())
         detailViewModel.getCharacter(id)
     }



    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        when (detailViewModel.data.value) {
            is Resource.DataError -> detailViewModel.showToastMessage(
                LocalContext.current
            )
            is Resource.Loading -> CustomCircularProgressIndicator()
            is Resource.Success -> detailViewModel.data.value.data?.let {
                Log.i("SUCCESS DEtail", it.id.toString())
                DetailInfo(
                    character = it,
                    detailViewModel
                )
            }
        }
    }
}

class SampleCharacterProvider : PreviewParameterProvider<Character> {
    override val values = sequenceOf(
        Character(
            1,
            "nombre",
            "Descripcion",
            "https://www.altonivel.com.mx/wp-content/uploads/2018/05/avengers.jpg",
            false
        )
    )
}

@Composable
fun DetailInfo(
    @PreviewParameter(SampleCharacterProvider::class) character: Character,
    viewModel: DetailViewModel
) {
    Surface() {
        Column(verticalArrangement = Arrangement.Top) {
            Box(contentAlignment = Alignment.BottomEnd) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(character.imageUrl)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.ic_captain_america),
                    contentDescription = stringResource(R.string.description),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                )
                Button(
                    onClick = {
                        viewModel.changeFavoriteCharacter(character)
                    },
                    modifier = Modifier.padding(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Red,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(30.dp)
                ) {
                    Icon(
                        imageVector = if (character.isFavorite) Icons.Outlined.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "favorite button"
                    )
                }
            }

            Card(
                elevation = 6.dp,
                backgroundColor = Color.LightGray,
                modifier = Modifier.padding(10.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = character.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h5
                )
            }

            if (character.description.isNotBlank())
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp), shape = RoundedCornerShape(4.dp), border = BorderStroke(
                        1.dp,
                        Color.LightGray
                    )
                ) {
                    Text(text = character.description, modifier = Modifier.padding(12.dp))
                }
        }
    }
}