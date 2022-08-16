package com.example.marvel.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.marvel.data.local.dto.CharacterDaoModel

@Database(
    entities = [CharacterDaoModel::class],
    version = 1
)

abstract class CharactersDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}