package com.example.pokemonapi.ui.theme.listPokemon

import PokemonView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController


@Composable
fun ListePokemonScreen(
    viewModel: ListePokemonViewModel, navController: NavController
) {
    val pokemonListResult = remember { viewModel.pokemonList }
    val searchQuery = remember { mutableStateOf("") }

    LaunchedEffect(key1 = null) {
        viewModel.getPokemonList()
    }

    Surface {
        Column {
            TextField(
                value = searchQuery.value,
                onValueChange = { newValue -> searchQuery.value = newValue },
                label = { Text("Search") },
                modifier = Modifier.fillMaxWidth()
            )

            LazyColumn {
                when (val pokemonList = pokemonListResult.value) {
                    is RequestState.LoadPokemon -> item { Text(text = "Loading...") }
                    is RequestState.Error -> {
                        item {
                            Text("Error : ${pokemonList.t.message}")
                            Button(onClick = {
                                viewModel.getPokemonList()
                            }) {
                                Text("Retry")
                            }
                        }
                    }

                    is RequestState.Success -> {
                        val filteredPokemonList = pokemonList.data.filter { pokemon ->
                            pokemon.name.contains(searchQuery.value, ignoreCase = true)
                        }

                        items(filteredPokemonList) { pokemon ->
                            Row() {
                                PokemonView(pokemon = pokemon, navController = navController)
                            }
                        }
                    }
                }
            }
        }
    }
}