package com.loki.coolacoola.di

import com.loki.coolacoola.data.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    private const val BASE_URL = "https://api.spoonacular.com/"
    private const val API_KEY = "7da0a452d87d470683c95d99b8a96dd0"


    @Singleton
    @Provides
    fun provideRetrofit() : Retrofit{

        val okHttpClient : OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(Interceptor {

                var request : Request = it.request()

                val httpUrl : HttpUrl = request.url().newBuilder()
                    .addQueryParameter("apiKey", API_KEY).build()

                request = Request.Builder().url(httpUrl).build()

                it.proceed(request)
            }).build()

        return  Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofitInstance(retrofit: Retrofit): ApiService {

        return retrofit.create(ApiService::class.java)
    }
}