package com.example.pokemonapi.ui.theme.data

import android.content.SharedPreferences
import com.example.pokemonapi.ui.theme.data.api.ApiDataSource
import com.example.pokemonapi.ui.theme.data.api.PokemonDetail
import com.example.pokemonapi.ui.theme.data.api.PokemonResult
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PokemonRepository() {

    private val apiDataSource = ApiDataSource()


    suspend fun getPokemonList(): List<PokemonResult> {
        return apiDataSource.getPokemonList()
    }

    suspend fun getPokemonById(id: Int): PokemonDetail {
        return apiDataSource.getPokemonById(id)
    }


}

class PokemonRepositoryFav(private val sharedPreferences: SharedPreferences) {
    private val gson = Gson()

    fun addPokemonToFavorites(pokemon: PokemonDetail) {
        val favorites = getFavoritePokemons().toMutableList()
        favorites.add(pokemon)
        val json = gson.toJson(favorites)
        sharedPreferences.edit().putString("favorites", json).apply()
    }

    fun getFavoritePokemons(): List<PokemonDetail> {
        val json = sharedPreferences.getString("favorites", null)
        val type = object : TypeToken<List<PokemonDetail>>() {}.type
        return gson.fromJson(json, type) ?: emptyList()
    }
}


