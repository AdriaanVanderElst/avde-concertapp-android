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

/**
 * The container of the application.
 *
 * @property concertRepository the [ConcertRepository] that is used to save Concerts.
 * @property userRepository the [UserRepository] that is used to save Users.
 */
interface AppContainer {
    val concertRepository: ConcertRepository
    val userRepository: UserRepository
}

/**
 * The default implementation of the [AppContainer].
 *
 * @property appContext the application context.
 */
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
        CachingConcertRepository(ConcertDatabase.getDatabase(appContext = appContext).concertDao(), concertApiService)
    }
    override val userRepository: UserRepository by lazy {
        UserRepositoryImpl(retrofit.create(UserApiService::class.java))
    }

    private fun okHttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(context))
            .addInterceptor(HttpLoggingInterceptor())
            .addInterceptor(NonSuccessfullResponseInterceptor())
            .build()
    }
}

/**
 * An interceptor that adds the bearer token to the request header.
 *
 * @property context the application context.
 */
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

/**
 * An interceptor that logs the request url.
 */
class HttpLoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        Log.d("RetrofitLog", "intercept: ${request.url}")
        return chain.proceed(request)
    }
}

/**
 * An interceptor that logs the response code and body if the response is not successful.
 */
class NonSuccessfullResponseInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        if (!response.isSuccessful) {
            Log.e("nonSuccessfullResponseInterceptor", "intercept: ${response.code}")
            Log.e("nonSuccessfullResponseInterceptor", "response body: ${response.body?.string()}")
        }
        return response
    }
}