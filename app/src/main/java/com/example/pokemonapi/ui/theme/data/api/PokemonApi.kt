package com.example.pokemonapi.ui.theme.data.api

data class PokemonResult(
    val id: Int = 0,
    val name: String = "",
    val sprite: String = "",
    val apiTypes: List<ApiType> = emptyList(),
    val apiGeneration: String = "",
)


data class PokemonDetail(
    val id: Int = 0,
    val name: String = "",
    val image: String = "",
    val stats: Stat = Stat(),
    val apiTypes: List<ApiType> = listOf(ApiType()),
    val apiGeneration: String = "",
)


data class Stat(

    val HP: Int = 0,
    val attack: Int = 0,
    val defense: Int = 0,
    val special_attack: Int = 0,
    val special_defense: Int = 0,
    val speed: Int = 0,
)

data class ApiType(
    val name: String = "",
    val image: String = ""

)

