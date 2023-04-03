package com.frost.pingerchallenge.network

import com.frost.pingerchallenge.data.Endpoints
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class AppModules {

    @Provides
    fun provideAPI() = Retrofit.Builder()
        .baseUrl(Endpoints.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .build()
        .create(API::class.java)

    @Provides
    fun provideRepository(api: API) = Repository(api)

    @Provides
    fun provideFetchUC(repository: Repository) = FetchUC(repository)

}