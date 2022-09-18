package com.uli.todo.data.local

import android.provider.ContactsContract
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.room.*
import com.uli.todo.data.model.NoteModel

@Dao
interface NoteDao {

    @Query("SELECT * FROM notemodel")
    fun getAllNote(): List<NoteModel>

    @Insert
    fun addNote(model: NoteModel)

    @Update
    fun updateNote(model: NoteModel)

    @Delete
    fun delete(model: NoteModel)

}