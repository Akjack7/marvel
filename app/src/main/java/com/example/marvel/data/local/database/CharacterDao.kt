package com.example.marvel.data.local.database

import androidx.room.*
import com.example.marvel.data.local.dto.CharacterDaoModel
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(character: CharacterDaoModel): Long

    @Delete
    fun delete(vararg character: CharacterDaoModel): Int

    @Query("SELECT * FROM " + CharacterDaoModel.TABLE_NAME + " WHERE character_id=:id ")
    fun loadCharacterById(id: Int): Flow<CharacterDaoModel>?
}