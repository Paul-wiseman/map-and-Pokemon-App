package com.decagon.android.sq007.utils
const val BASE_URL = "https://pokeapi.co/api/v2/"
const val POKE_URL = "PokemonUrl"
fun getPokemonNo(url: String): String {
    val no = url.split("https://pokeapi.co/api/v2/pokemon/")[1].split("/")[0]
    return no
}
fun getPokemonName(url: String): String {
    val name = url.split("https://pokeapi.co/api/v2/")[1].split("/")[0]
    return name
}
