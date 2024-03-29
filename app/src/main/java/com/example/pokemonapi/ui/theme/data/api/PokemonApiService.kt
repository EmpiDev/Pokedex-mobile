package com.example.pokemonapi.ui.theme.data.api

import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonApiService {
    @GET("limit/250")
    suspend fun get250pokemons(): List<PokemonResult>

    @GET("{id}")
    suspend fun getPokemonById(@Path("id") id: Int): PokemonDetail
}