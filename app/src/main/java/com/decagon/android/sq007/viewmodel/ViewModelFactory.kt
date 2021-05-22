package com.decagon.android.sq007.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.decagon.android.sq007.model.network.PokemonApi

class ViewModelFactory(private val pokeApi: PokemonApi) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(PokemonApi::class.java).newInstance(pokeApi)
    }
}
