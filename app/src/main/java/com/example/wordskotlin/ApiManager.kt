package com.example.wordskotlin

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

object ApiManager {

    private val client = OkHttpClient()

    fun makeGetRequest(): Response {
        val request = Request.Builder()
            .url("https://raw.githubusercontent.com/kahramanumut/English-Word-Reminder/main/EnglishWordReminder/frequentlyWords.json")
            .build()
        return client.newCall(request).execute()
    }
}
