package com.uli.todo.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class NoteModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var title: String,
    var description: String
) : Serializable
