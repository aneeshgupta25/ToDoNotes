package com.aneesh.todonotesfinal.data.local.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notesData")
data class NotesEntity (
    @PrimaryKey(autoGenerate = true) var id : Int? = null,
    @ColumnInfo(name = "title") var title : String = "",
    @ColumnInfo(name = "description") var description : String = "",
    @ColumnInfo(name = "imagePath") var imagePath : String = "",
    @ColumnInfo(name = "isTaskCompleted") var isTaskCompleted : Boolean = false
)