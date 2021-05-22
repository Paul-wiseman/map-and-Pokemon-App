package com.decagon.android.sq007.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.decagon.android.sq007.model.network.PokemonApi
import com.decagon.android.sq007.pokemon.AllPokemons
import com.decagon.android.sq007.pokemon.Pokemon
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
class PokemonViewModel(private val pokemonApi: PokemonApi) : ViewModel() {

    private var _pokemon = MutableLiveData<AllPokemons>()
    val pokemon: LiveData<AllPokemons>
        get() = _pokemon

    private var _pokemonDetails = MutableLiveData<Pokemon>()
    val pokemonDetails: LiveData<Pokemon>
        get() = _pokemonDetails

    private var _pokemonStats = MutableLiveData<Pokemon>()
    val pokemonStats: LiveData<Pokemon>
        get() = _pokemonDetails

    fun getPokemon() {
        CompositeDisposable().add(
            pokemonApi.getPokemon(300, 0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                    _pokemon.value = it
                }
        )
    }

    fun getPokemnonDetails(url: String) {
        CompositeDisposable().add(
            pokemonApi.getPokemonDetials(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                    _pokemonDetails.value = it
                    _pokemonStats.value = it
                }
        )
    }
}
//        pokemonApi.getPokemon(300, 0).enqueue(object : Callback<AllPokemons> {
//            override fun onFailure(call: Call<AllPokemons>, t: Throwable) {
//                Log.d("PokemonViewmodel", "onFailure: ${t.message}")
//            }
//
//            override fun onResponse(call: Call<AllPokemons>, response: Response<AllPokemons>) {
//                if (response.isSuccessful) {
//                    _pokemon.value = response.body()
//                } else {
//                    Log.d("output3", "${response.code()}")
//                    return
//                }
//            }
//        })

// pokemonApi.getPokemonDetials(url).enqueue(object : Callback<Pokemon> {
//    override fun onFailure(call: Call<Pokemon>, t: Throwable) {
//        Log.d("PokemonDetails", "onFailure: ${t.message}")
//    }
//
//    override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
//        if (response.isSuccessful) {
//            _pokemonDetails.value = response.body()
//        } else {
//            Log.d("PokemonDetail", "${response.code()}")
//        }
//    }
// })

// fun getPokemnonStats(url: String) {
//    pokemonApi.getPokemonDetials(url).enqueue(object : Callback<Pokemon> {
//        override fun onFailure(call: Call<Pokemon>, t: Throwable) {
//            Log.d("PokemonDetails", "onFailure: ${t.message}")
//        }
//
//        override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
//            if (response.isSuccessful) {
//                _pokemonStats.value = response.body()
//            } else {
//                Log.d("PokemonDetail", "${response.code()}")
//            }
//        }
//    })
// }
