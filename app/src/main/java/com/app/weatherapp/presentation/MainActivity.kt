package com.app.weatherapp.presentation

import MainUiEvents
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.weatherapp.data.ProductRepoImpl
import com.app.weatherapp.ui.theme.WeatherAppTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherAppTheme {
                MyWeatherApp()
            }
        }
    }
}


@Composable
fun MyWeatherApp(viewModel: ProductViewModel = koinViewModel()) {

    val weatherState by viewModel.product.collectAsStateWithLifecycle()
    val mainState by viewModel.mainState.collectAsState()

    val countryAbr = weatherState?.sys?.country
    val cityName = weatherState?.name
    val condition = weatherState?.weather?.first()?.main
    val temperature = weatherState?.main?.temp?.let { it - 275 }?.toInt() // Safe null-check and subtraction

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 26.dp),
                value = mainState.searchWord,
                onValueChange = { newText ->
                    viewModel.onEvent(MainUiEvents.OnSeacrchWordChange(newText))
                    },
                singleLine = true,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .size(30.dp)
                            .clickable {
                                viewModel.onEvent(
                                    MainUiEvents.OnSearchClick
                                )
                            }
                    )

                },
                label = {
                    Text(
                        text = "Search a City",
                        fontSize = 15.sp,
                        modifier = Modifier.alpha(0.7f)
                    )
                },
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 19.5.sp
                )
            )
        }
    ){
        paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding())
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            MaterialTheme.colorScheme.primary,
                            Color.Red.copy(0.4f),
                        )
                    )
                )
        ) {
            Column (
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
                ){

                Text("$condition",
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontSize = 70.sp,
                    fontWeight = FontWeight.Bold
                )

                Text("$cityName, $countryAbr",
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(15.dp),
                    fontSize = 20.sp,
                )
                Row {
                    Text("${temperature}",
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(5.dp),
                        fontSize = 60.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Text("\u2103",
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(5.dp),
                        fontSize = 60.sp,
                    )

                }


            }

        }
    }


}
