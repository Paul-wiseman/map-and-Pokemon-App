package com.decagon.android.sq007.model.network

import com.decagon.android.sq007.utils.BASE_URL
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {

    fun getPokemonApi(): PokemonApi {

        return Retrofit.Builder()
            // Gson converter for parsing and converting json data from the API into kotlin object
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PokemonApi::class.java)
    }
}
