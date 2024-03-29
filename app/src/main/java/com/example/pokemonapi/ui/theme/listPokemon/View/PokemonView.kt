
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.pokemonapi.ui.theme.data.api.PokemonResult

@Composable
fun PokemonView(pokemon: PokemonResult, navController: NavController) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .border(BorderStroke(2.dp, Color.Gray), RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .clickable { navController.navigate("pokemon_detail/${pokemon.id}") }
    ) {
        AsyncImage(
            model = pokemon.sprite,
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
        )
        Text(
            text = pokemon.name,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp)
        )
    }
}