
import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.pokemonapi.R
import com.example.pokemonapi.ui.theme.data.api.PokemonDetail
import com.example.pokemonapi.ui.theme.listPokemon.PokemonDetailScreenModel
import com.example.pokemonapi.ui.theme.listPokemon.RequestStatePokemon
import com.example.pokemonapi.ui.theme.listPokemon.View.PokemonDescription
import com.example.pokemonapi.ui.theme.listPokemon.View.PokemonStatRow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonDetailScreen(
    viewModel: PokemonDetailScreenModel, pokemonId: Int, navController: NavController
) {

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("pokemon_prefs", Context.MODE_PRIVATE)
    val viewModel = PokemonDetailScreenModel(sharedPreferences)
    //récupère la liste des pokémons favoris
    val favoritePokemons: List<PokemonDetail> = viewModel.getFavoritePokemons()

    val pokemonDetailResult = remember { viewModel.pokemon }
    LaunchedEffect(pokemonId) {
        viewModel.getPokemonById(pokemonId)
    }

    Column {
        TopAppBar(title = {
            Text(
                text = "Pokémon Details"
            )
        }, navigationIcon = {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Retour")
            }
        })

        when (val pokemonDetail = pokemonDetailResult.value) {
            is RequestStatePokemon.LoadPokemon -> Text(text = stringResource(id = R.string.Loading))
            is RequestStatePokemon.Error -> Text(text = "Erreur : ${pokemonDetail.t.message}")
            is RequestStatePokemon.Success -> {
                Card(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize()
                ) {

                    Column() {
                        val addToFav = stringResource(id = R.string.PokemonAddToFavorite)
                        //Affichage de l'image du pokémon et du bouton pour ajouter aux favoris a droite  toute
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween)
                            {
                            AsyncImage(
                                model = pokemonDetail.data.image,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(200.dp)
                                    .padding(16.dp)
                            )
                            IconButton(onClick = {
                                viewModel.addPokemonToFavorites(pokemonDetail.data)
                                //Log message
                                Log.d(
                                    "PokemonDetailScreen",
                                    "Le pokémon ${pokemonDetail.data.name} a été ajouté aux favoris"
                                )

                                scope.launch {
                                    snackbarHostState.showSnackbar("${pokemonDetail.data.name}"+ addToFav)
                                }
                            }) {
                                Icon(
                                    Icons.Filled.Favorite,
                                    contentDescription =addToFav
                                )
                            }
                        }
                        Column {
                            PokemonStatRow(
                                statName = stringResource(id = R.string.PokemonName),
                                statValue = pokemonDetail.data.name
                            )
                            PokemonStatRow(
                                statName = stringResource(id = R.string.PokemonGeneration),
                                statValue = pokemonDetail.data.apiGeneration
                            )
                        }
                    }

                    Column {
                        PokemonDescription(
                            pokemonDetail.data.stats,
                            pokemonDetail.data.apiTypes
                        )

                    }


                    //Bouton qui renvoie vers la liste des favoris
                    Button(onClick = {
                        navController.navigate("favorite")
                    }) {
                        Text(text = stringResource(id = R.string.PokemonFavoriteList))
                    }
                    SnackbarHost(hostState = snackbarHostState)
                }

            }


        }
    }
}

