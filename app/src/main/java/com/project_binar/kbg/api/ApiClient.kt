package com.project_binar.kbg.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private val BASE_URL= "https://binar-gdd-cc8.herokuapp.com/api/v1/"
    fun service() : ApiService {
        val logging = HttpLoggingInterceptor()
        logging.level= HttpLoggingInterceptor.Level.BODY
        val clientBuilder= OkHttpClient.Builder()
        clientBuilder.addInterceptor(logging)
        val client=clientBuilder.build()

        val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }
        return retrofit.create(ApiService::class.java)
    }
}