package com.ramesh.virtusa.di.networkProviderModule
import android.content.Context
import com.google.gson.GsonBuilder
import com.ramesh.virtusa.BuildConfig

import com.ramesh.virtusa.data.network.remote.CategoryAPIService
import com.ramesh.virtusa.data.network.remote.ProductDataAPIService
import com.ramesh.virtusa.di.repositoryBindingModule.RepositoryModule.Companion.BASE_URL
import com.ramesh.virtusa.utilities.network.HttpRequestInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Provides
    @Singleton
    fun provideApplicationContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    @Singleton

    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return if (BuildConfig.DEBUG) HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        else HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
    }
    @Provides
    @Singleton
    fun provideCache(context:Context): Cache
    {
        val cacheSize = (10 * 1024 * 1024).toLong()
        val cacheDir = File(context.cacheDir, "http-cache")
        return Cache(cacheDir, cacheSize)
    }
    @Provides
    @Singleton
    fun provideOkHttpClient(logging: HttpLoggingInterceptor,
                            cache: Cache): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .cache(cache)// Add logging interceptor here.
            .addInterceptor(HttpRequestInterceptor())
            .build()
    }


   @Provides
    @Singleton
     fun provideConverterFactory(): GsonConverterFactory {
         return GsonConverterFactory.create(GsonBuilder().create())

   }

    @Provides
    @Singleton
    fun provideRetrofit(converterFactory: GsonConverterFactory, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(converterFactory)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): CategoryAPIService {
        return retrofit.create(CategoryAPIService::class.java)
    }
    @Provides
    @Singleton
    fun provideProductApiService(retrofit: Retrofit): ProductDataAPIService {
        return retrofit.create(ProductDataAPIService::class.java)
    }

}