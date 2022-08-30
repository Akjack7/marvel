package com.example.marvel.domain.models

import Results
import com.example.marvel.data.local.dto.CharacterDaoModel


const val XLARGE_IMAGE = "/landscape_xlarge."
const val HTTP = "http"
const val HTTPS = "https"


fun Results.toDomain(): Character {
    return Character(
        id, name, description, (thumbnail.path + XLARGE_IMAGE + thumbnail.extension).replace(
            HTTP, HTTPS
        )
    )
}

fun Character.toDaoModel(): CharacterDaoModel {
    return CharacterDaoModel(id, name, description, imageUrl)
}

fun CharacterDaoModel.toDomain(): Character {
    return Character(id, name, description, imageUrl)
}
