package com.uli.todo.ui

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.uli.todo.data.local.NoteDatabase
import com.uli.todo.ui.utils.Prefs

class App : Application() {
    private lateinit var preferences: SharedPreferences

    companion object {
        lateinit var db: NoteDatabase

        @SuppressLint("StaticFieldLeak")
        lateinit var prefs: Prefs
    }

    override fun onCreate() {
        super.onCreate()
        db = NoteDatabase.invoke(this)

        preferences = this.applicationContext
            .getSharedPreferences("settings", Context.MODE_PRIVATE)
        prefs = Prefs(preferences)
    }
}