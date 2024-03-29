package com.example.pokemonapi.ui.theme.listPokemon.View

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.pokemonapi.R
import com.example.pokemonapi.ui.theme.data.api.ApiType
import com.example.pokemonapi.ui.theme.data.api.Stat

@Composable
fun PokemonDescription(pokemonStats: Stat, pokemonTypes: List<ApiType>) {
    PokemonStatRow(
        statName = stringResource(id = R.string.PokemonHP),
        statValue = pokemonStats.HP.toString()
    )
    PokemonStatRow(
        statName = stringResource(id = R.string.Pokemonattack),
        statValue = pokemonStats.attack.toString()
    )
    PokemonStatRow(
        statName = stringResource(id = R.string.Pokemondefense),
        statValue = pokemonStats.defense.toString()
    )
    PokemonStatRow(
        statName = stringResource(id = R.string.Pokemonspecial_attack),
        statValue = pokemonStats.special_attack.toString()
    )
    PokemonStatRow(
        statName = stringResource(id = R.string.Pokemonspecial_defense),
        statValue = pokemonStats.special_defense.toString()
    )
    PokemonStatRow(
        statName = stringResource(id = R.string.Pokemonspeed),
        statValue = pokemonStats.speed.toString()
    )
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(id = R.string.PokemonTypes) + ": ",
            modifier = Modifier.weight(1f)
        )
        //Liste d'images
        for (type in pokemonTypes) {
            AsyncImage(
                model = type.image,
                contentDescription = null,
                modifier = Modifier.size(50.dp)
            )
        }
    }
}