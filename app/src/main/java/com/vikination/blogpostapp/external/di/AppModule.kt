package com.vikination.blogpostapp.external.di

import com.vikination.blogpostapp.BuildConfig
import com.vikination.blogpostapp.data.repos.MainRepository
import com.vikination.blogpostapp.data.service.ApiService
import com.vikination.blogpostapp.data.source.RemoteSource
import com.vikination.blogpostapp.domain.implementation.MainRepositoryImpl
import com.vikination.blogpostapp.domain.implementation.RemoteSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.DelicateCoroutinesApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideRetrofit() :ApiService{
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiService::class.java)
    }

    @DelicateCoroutinesApi
    @Provides
    fun provideRemoteSource(apiService: ApiService) :RemoteSource{
        return RemoteSourceImpl(apiService)
    }

    @Provides
    fun provideMainRepository(remoteSource: RemoteSource) :MainRepository{
        return MainRepositoryImpl(remoteSource)
    }
}