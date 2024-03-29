package com.example.pokemonapi.ui.theme.listPokemon.View

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PokemonStatRow(statName: String, statValue: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = statName + ": ",
            modifier = Modifier.weight(1f)
        )
        Text(text = statValue.toString())
    }
}