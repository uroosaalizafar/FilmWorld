package com.nglb.filmworld.di

import com.nglb.filmworld.data.network.MovieApiInterface
import com.nglb.filmworld.utils.Constant.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


//return the retrofit object
@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            // we need to add converter factory to
            // convert JSON object to Java object
            .build()
    }
    @Provides
    @Singleton
    fun getMovieAPI(retrofit: Retrofit): MovieApiInterface {
        return retrofit.create(MovieApiInterface::class.java)
    }
}