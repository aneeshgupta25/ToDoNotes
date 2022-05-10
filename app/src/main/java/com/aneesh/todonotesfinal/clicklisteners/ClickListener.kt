package com.aneesh.todonotesfinal.clicklisteners

import com.aneesh.todonotesfinal.db.NotesEntity

interface ClickListener {
    fun onClick(note : NotesEntity)
    fun onUpdate(note : NotesEntity)
}