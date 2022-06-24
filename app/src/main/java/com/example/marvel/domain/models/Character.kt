package com.example.marvel.domain.models

data class Character(
    val id: Int,
    val name: String,
    val description: String,
    val imageUrl: String,
    var isFavorite: Boolean = false
)