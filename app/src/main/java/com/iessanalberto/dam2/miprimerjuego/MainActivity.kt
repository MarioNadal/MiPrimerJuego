package com.iessanalberto.dam2.juegodepreguntas3

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iessanalberto.dam2.miprimerjuego.R
import com.iessanalberto.dam2.miprimerjuego.data.listaGentilicios
import com.iessanalberto.dam2.miprimerjuego.data.mapaGentilicios
import com.iessanalberto.dam2.miprimerjuego.ui.theme.MiPrimerJuegoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MiPrimerJuegoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    for(gentilicio in mapaGentilicios){
                        listaGentilicios.add(gentilicio.key)
                    }
                    listaGentilicios.shuffle()
                    BodyContent()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BodyContent() {
    var numPartida by remember { mutableStateOf(0)}
    var puntuacion by remember { mutableStateOf(0)}
    Scaffold(
        topBar = {CenterAlignedTopAppBar(
            title = { Text(text = "Puntuación: $puntuacion") },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary
            ))
        }
    )
    {
        //Variables
        //Context es usado para los Toast
        var context = LocalContext.current
        var capital by remember {mutableStateOf("")}
        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            Image(
                painter = painterResource(id = R.drawable.interrogante),
                contentDescription = "Interrogante",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(225.dp)
                    .clip(CircleShape)
            )
            Text(text = "¿Cúal es el gentilicio de ${listaGentilicios.get(numPartida)}?", fontSize = 20.sp)
            OutlinedTextField(value = capital, onValueChange = {capital=it})
            Button(onClick = {
                if(capital == (mapaGentilicios.get(listaGentilicios.get(numPartida)))){
                    Toast.makeText(context,"Enhorabuena has acertado", Toast.LENGTH_SHORT).show()
                    puntuacion++
                    numPartida++
                    //Para poner el TextField vacio
                    capital = ""
                }else{
                    Toast.makeText(context,"Error el gentilicio de ${listaGentilicios.get(numPartida)} es  ${mapaGentilicios.get(listaGentilicios.get(numPartida))}",Toast.LENGTH_SHORT).show()
                    numPartida++
                    capital = ""
                }
            },
                modifier = Modifier.size(125.dp,60.dp)) {
                Text(text = "Validar", fontSize = 20.sp)
            }
        }
    }
}