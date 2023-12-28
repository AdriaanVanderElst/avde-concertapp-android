package com.example.androidconcertapp.data

import android.content.Context
import android.util.Log
import com.example.androidconcertapp.data.database.ConcertDatabase
import com.example.androidconcertapp.network.ConcertApiService
import com.example.androidconcertapp.network.UserApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit

interface AppContainer {
    val concertRepository: ConcertRepository
    val userRepository: UserRepository
}

class DefaultAppContainer(private val appContext: Context) : AppContainer {
    private val baseUrl = "http://10.0.2.2:9000/api/"
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(
            Json.asConverterFactory("application/json".toMediaType()),
        )
        .baseUrl(baseUrl)
        .client(okHttpClient(appContext))
        .build()

    private val concertApiService: ConcertApiService = retrofit.create(ConcertApiService::class.java)

    override val concertRepository: ConcertRepository by lazy {
//        ApiConcertRepository(concertApiService)
        CachingConcertRepository(ConcertDatabase.getDatabase(appContext = appContext).concertDao(), concertApiService)
    }
    override val userRepository: UserRepository by lazy {
        UserRepositoryImpl(retrofit.create(UserApiService::class.java))
    }

    // add bearer token of user to each request
    private fun okHttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(context))
            .build()
    }
}

class AuthInterceptor(context: Context) : Interceptor {
    private val sharedPrefs = context.getSharedPreferences("AuthPrefs", Context.MODE_PRIVATE)
    private val bearerToken = sharedPrefs.getString("bearerToken", "")

    override fun intercept(chain: Interceptor.Chain): Response {
        Log.d("AuthInterceptor", "intercept: $bearerToken")
        val requestBuilder = chain.request().newBuilder()
        requestBuilder.addHeader("Authorization", "Bearer $bearerToken").build()
        return chain.proceed(requestBuilder.build())
    }
}
