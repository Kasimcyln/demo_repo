package com.example.wordskotlin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class WordViewModelFactory(
    private val repository: WordRepository,
    private val preferencesHelper: PreferencesHelper
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WordViewModel(repository, preferencesHelper) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
