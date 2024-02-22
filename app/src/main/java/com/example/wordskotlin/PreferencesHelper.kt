package com.example.wordskotlin

import android.content.Context

class PreferencesHelper(context: Context) {
    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFS_NAME = "word_prefs"
        private const val KEY_LAST_INDEX = "lastIndex"
        private const val KEY_CURRENT_PAGE = "currentPage"
    }

    var currentPage: Int
        get() = preferences.getInt(KEY_CURRENT_PAGE, 0)
        set(value) = preferences.edit().putInt(KEY_CURRENT_PAGE, value).apply()

    var lastIndex: Int
        get() = preferences.getInt(KEY_LAST_INDEX, 0)
        set(value) = preferences.edit().putInt(KEY_LAST_INDEX, value).apply()


    fun saveWordCheckState(word: String, isChecked: Boolean) {
        preferences.edit().putBoolean(word, isChecked).apply()
    }

    fun getWordCheckState(word: String): Boolean {
        return preferences.getBoolean(word, false)
    }
}