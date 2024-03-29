package com.example.pokemonapi.ui.theme.listPokemon

import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonapi.ui.theme.data.PokemonRepository
import com.example.pokemonapi.ui.theme.data.PokemonRepositoryFav
import com.example.pokemonapi.ui.theme.data.api.PokemonDetail
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch

sealed class RequestStatePokemon {
    object LoadPokemon : RequestStatePokemon()
    data class Error(val t: Throwable) : RequestStatePokemon()
    data class Success(val data: PokemonDetail) : RequestStatePokemon()
}

class PokemonDetailScreenModel(private val sharedPreferences: SharedPreferences) : ViewModel() {
    private val repository = PokemonRepository()
    private val favRepository = PokemonRepositoryFav(sharedPreferences)
    val pokemon = mutableStateOf<RequestStatePokemon>(RequestStatePokemon.Success(PokemonDetail()))

    fun getPokemonById(id: Int) {
        viewModelScope.launch {
            pokemon.value = RequestStatePokemon.LoadPokemon
            try {
                val result = repository.getPokemonById(id)
                pokemon.value = RequestStatePokemon.Success(result)
            } catch (t: Throwable) {
                pokemon.value = RequestStatePokemon.Error(t)
            }
        }
    }



    private val gson = Gson()

    fun addPokemonToFavorites(pokemon: PokemonDetail) {
        val favoritePokemons = getFavoritePokemons().toMutableList()
        favoritePokemons.add(pokemon)
        val favoritePokemonsString = gson.toJson(favoritePokemons)
        sharedPreferences.edit().putString(Companion.FAVORITE_POKEMONS_KEY, favoritePokemonsString).apply()
    }

    fun getFavoritePokemons(): List<PokemonDetail> {
        val favoritePokemonsString = sharedPreferences.getString(Companion.FAVORITE_POKEMONS_KEY, null)
        return if (favoritePokemonsString != null) {
            val type = object : TypeToken<List<PokemonDetail>>() {}.type
            gson.fromJson(favoritePokemonsString, type)
        } else {
            emptyList()
        }
    }
    fun removePokemonFromFavorites(pokemon: PokemonDetail) {
        val favoritePokemons = getFavoritePokemons().toMutableList()
        favoritePokemons.remove(pokemon)
        val favoritePokemonsString = gson.toJson(favoritePokemons)
        sharedPreferences.edit().putString(Companion.FAVORITE_POKEMONS_KEY, favoritePokemonsString).apply()
    }

    companion object {
        const val FAVORITE_POKEMONS_KEY = "favoritePokemons"
    }

}
