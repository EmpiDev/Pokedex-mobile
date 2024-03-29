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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.pokemonapi.R
import com.example.pokemonapi.ui.theme.data.api.PokemonDetail
import com.example.pokemonapi.ui.theme.listPokemon.PokemonDetailScreenModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritePokemonScreen(
    viewModel: PokemonDetailScreenModel,
    navController: NavController,
    scope: CoroutineScope
) {
    //Récupérer la liste des pokemons favoris
    val favoritePokemons: List<PokemonDetail> = viewModel.getFavoritePokemons()

    // Create a SnackbarHostState and remember it
    val snackbarHostState = remember { SnackbarHostState() }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(stringResource(id = R.string.PokemonFavoriteList)) },
            navigationIcon = {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Retour")
                }
            }
        )

        favoritePokemons.forEach { pokemon: PokemonDetail ->
            Column(
                modifier = Modifier.padding(16.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = pokemon.name)
                    val removeFromFav = stringResource(id = R.string.PokemonRemoveFromFavorite)
                    IconButton(onClick = {
                        viewModel.removePokemonFromFavorites(pokemon)
                        scope.launch {
                            snackbarHostState.showSnackbar(removeFromFav)
                        }
                    }) {
                        Icon(Icons.Filled.Favorite, contentDescription = removeFromFav)
                    }
                }

                AsyncImage(
                    model = pokemon.image,
                    contentDescription = null,
                    modifier = Modifier.size(100.dp)
                )

            }
        }


        SnackbarHost(hostState = snackbarHostState)
    }
}