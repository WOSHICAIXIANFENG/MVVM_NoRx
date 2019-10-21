package com.cai.funs.network

import com.cai.funs.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val CONNECT_TIMEOUT = 5L
private const val WRITE_TIMEOUT = 5L
private const val READ_TIMEOUT = 5L

val networkModule = module {
  single<Cache> { Cache(androidApplication().cacheDir, 10L * 1024 * 1024) }

  single<Gson> { GsonBuilder().create() }

  single<Interceptor> {
    HttpLoggingInterceptor().apply {
      if (BuildConfig.DEBUG)
        level = HttpLoggingInterceptor.Level.BODY
    }
  }

  single<OkHttpClient> {
    OkHttpClient.Builder().apply {
      // set cache from endpoint layer
      cache(get())

      // set time out
      connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
      writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
      readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
      // set retry policy
      retryOnConnectionFailure(true)
      // add other interceptors
      // add log interceptor
      addInterceptor(get())
    }.build()
  }

  single<Retrofit> {
    Retrofit.Builder().apply {
      baseUrl("https://obscure-earth-55790.herokuapp.com")
      addConverterFactory(GsonConverterFactory.create(get()))
      // addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    }.client(get())
      .build()
  }
}