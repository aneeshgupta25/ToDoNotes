package com.aneesh.todonotesfinal.myNotes.clicklisteners

import com.aneesh.todonotesfinal.data.local.db.NotesEntity

interface ClickListener {
    fun onClick(note : NotesEntity)
    fun onUpdate(note : NotesEntity)
}