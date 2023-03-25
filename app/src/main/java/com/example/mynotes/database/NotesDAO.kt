package com.example.mynotes.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NotesDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: Note)

    @Query("Select * from notes order by id ASC")
    fun getAllNotes() : LiveData<List<Note>>

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)
}