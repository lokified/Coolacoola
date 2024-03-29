package com.loki.coolacoola.network

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiUtil {

    private var retrofit : Retrofit? = null

    var BASE_URL = "https://api.spoonacular.com/"
    var API_KEY = "7da0a452d87d470683c95d99b8a96dd0"


    fun getApiInterface() : ApiInterface? {

        if(retrofit == null) {
            val okHttpClient : OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(Interceptor {

                    var request : Request = it.request()

                    val httpUrl : HttpUrl = request.url().newBuilder()
                        .addQueryParameter("apiKey", API_KEY).build()

                    request = Request.Builder().url(httpUrl).build()

                    it.proceed(request)
                }).build()


            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        return retrofit?.create(ApiInterface::class.java)
    }
}