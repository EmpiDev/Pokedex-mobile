package com.example.pokemonapi

import FavoritePokemonScreen
import PokemonDetailScreen
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pokemonapi.ui.theme.PokemonApiTheme
import com.example.pokemonapi.ui.theme.listPokemon.ListePokemonScreen
import com.example.pokemonapi.ui.theme.listPokemon.ListePokemonViewModel
import com.example.pokemonapi.ui.theme.listPokemon.PokemonDetailScreenModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = getSharedPreferences("pokemon_prefs", Context.MODE_PRIVATE)

        setContent {
            PokemonApiTheme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = "pokemon_list") {
                    composable("pokemon_list") {
                        ListePokemonScreen(
                            viewModel = ListePokemonViewModel(),
                            navController = navController
                        )
                    }
                    composable("pokemon_detail/{pokemonId}") { backStackEntry ->
                        val pokemonId = backStackEntry.arguments!!.getString("pokemonId")!!.toInt()
                        PokemonDetailScreen(
                            viewModel = PokemonDetailScreenModel(sharedPreferences),
                            pokemonId,
                            navController = navController
                        )
                    }
                    composable("favorite") {
                        FavoritePokemonScreen(
                            viewModel = PokemonDetailScreenModel(sharedPreferences),
                            navController = navController,
                            scope = rememberCoroutineScope()
                        )
                    }
                }
            }
        }
    }
}