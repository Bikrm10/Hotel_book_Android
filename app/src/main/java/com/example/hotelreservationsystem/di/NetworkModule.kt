package com.example.hotelreservationsystem.di

import com.example.hotelreservationsystem.api.AuthInterceptors
import com.example.hotelreservationsystem.api.HotelsApi
import com.example.hotelreservationsystem.api.OwnerApi
import com.example.hotelreservationsystem.api.UserApi
import com.example.hotelreservationsystem.api.getallHotelsApi
import com.example.hotelreservationsystem.utils.constants.BASEURL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {
    @Singleton
    @Provides
    fun providesRetrofitBuilder(): Retrofit.Builder{
        return  Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASEURL)
        //.build()
    }

    @Singleton
    @Provides
    fun provideOkHTTPClient(authInterceptors: AuthInterceptors) : OkHttpClient{
        return OkHttpClient.Builder().addInterceptor(authInterceptors).build()
    }



    @Singleton
    @Provides
    fun providesOwnerAPI(retrofitBuilder: Retrofit.Builder):OwnerApi{
        return  retrofitBuilder.build().create(OwnerApi::class.java)
    }
    @Singleton
    @Provides
    fun providesUerAPI(retrofitBuilder: Retrofit.Builder): UserApi {
        return  retrofitBuilder.build().create(UserApi::class.java)
    }

    @Singleton
    @Provides
    fun providesHotelApi(retrofitBuilder: Retrofit.Builder,okHttpClient: OkHttpClient):HotelsApi{
        return  retrofitBuilder
            .client(okHttpClient)
            .build().create(HotelsApi::class.java)
    }

    @Singleton
    @Provides
    fun providesUserAuthApi(retrofitBuilder: Builder,okHttpClient: OkHttpClient):getallHotelsApi
    {
        return  retrofitBuilder
            .client(okHttpClient)
            .build()
            .create(getallHotelsApi::class.java)
    }

}