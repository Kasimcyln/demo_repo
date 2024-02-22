package com.example.wordskotlin

interface WordRepository {
    suspend fun fetchWords(): List<Word>?
}
