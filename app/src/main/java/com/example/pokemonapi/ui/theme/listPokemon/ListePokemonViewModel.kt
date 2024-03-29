package com.example.pokemonapi.ui.theme.listPokemon

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonapi.ui.theme.data.PokemonRepository
import com.example.pokemonapi.ui.theme.data.api.PokemonResult
import kotlinx.coroutines.launch

sealed class RequestState {
    data object LoadPokemon : RequestState()
    class Error(val t: Throwable) : RequestState()
    class Success(val data: List<PokemonResult>) : RequestState()
}

class ListePokemonViewModel : ViewModel() {
    private val repository = PokemonRepository()
    val pokemonList = mutableStateOf<RequestState>(RequestState.Success(emptyList()))


    fun getPokemonList() {
        viewModelScope.launch {
            pokemonList.value = RequestState.LoadPokemon
            try {
                val result = repository.getPokemonList()
                pokemonList.value = RequestState.Success(result)
            } catch (t: Throwable) {
                pokemonList.value = RequestState.Error(t)

            }
        }

    }

}