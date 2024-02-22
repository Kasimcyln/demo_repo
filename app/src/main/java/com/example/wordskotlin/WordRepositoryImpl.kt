package com.example.wordskotlin

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WordRepositoryImpl : WordRepository {

    override suspend fun fetchWords(): List<Word>? = withContext(Dispatchers.IO) {
        val response = ApiManager.makeGetRequest()
        val body = response.body?.string()
        if (body != null) {
            val gson = Gson()
            val wordListType = object : TypeToken<List<Word>>() {}.type
            return@withContext gson.fromJson(body, wordListType)
        } else {
            return@withContext null
        }
    }
}
