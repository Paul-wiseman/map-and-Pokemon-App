package com.decagon.android.sq007.model.network

import com.decagon.android.sq007.pokemon.AllPokemons
import com.decagon.android.sq007.pokemon.Pokemon
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface PokemonApi {
    @GET("pokemon") // the REST method with end point url it maps to
    fun getPokemon(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Observable<AllPokemons>

    @GET // the REST method with end point url it maps to
    fun getPokemonDetials(@Url url: String): Observable<Pokemon>
}
