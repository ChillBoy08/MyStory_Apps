package com.example.mystory.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mystory.data.ListItem

@Dao
interface StoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStory(story: List<ListItem>)

    @Query("SELECT * FROM story")
    fun getAllStory(): PagingSource<Int, ListItem>

    @Query("DELETE FROM story")
    suspend fun deleteAll()
}