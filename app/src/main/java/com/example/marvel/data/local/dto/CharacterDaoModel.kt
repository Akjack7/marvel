package com.example.marvel.data.local.dto

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = CharacterDaoModel.TABLE_NAME)
@Keep
data class CharacterDaoModel(
    @ColumnInfo(name = "character_id") @PrimaryKey val id: Int,
    @ColumnInfo(name = "character_name") val name: String,
    @ColumnInfo(name = "character_description") val description: String,
    @ColumnInfo(name = "character_image_url") val imageUrl: String,
) {
    companion object {
        const val TABLE_NAME = "characters"
    }
}