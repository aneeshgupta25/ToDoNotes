package com.aneesh.todonotesfinal.data.local.db

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface NotesDao {

    @Query("SELECT * from notesData")
    fun getAll() : List<NotesEntity>

    @Insert(onConflict = REPLACE)
    fun insert(note : NotesEntity)

    @Update
    fun updateNotes(note: NotesEntity)

    @Delete
    fun delete(note: NotesEntity)

    @Query("DELETE FROM notesData WHERE isTaskCompleted = :status")
    fun deleteNotes(status : Boolean)
}