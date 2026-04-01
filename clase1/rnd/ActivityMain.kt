package ar.edu.miprimerproyecto


import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import kotlin.random.Random



class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContent {
            PantallaPrincipal()
        }
    }
}

fun generarNumeroAleatorio(): Int {
    return Random.nextInt(0,101)
}

@Composable
fun PantallaPrincipal() {

    //Crea una variable que se llama numero
    //remember hace que Composable recuerde el valor entre composiciones
    //mutableStateOf(0) indica que el valor puede cambiar y cuando lo hace, la interfaz se redibuja

    var numero by remember {
        mutableIntStateOf(0)
    } //varibale de estado
    VistaNumeroAleatorio(
        numero = numero,
        cuandoHagoClick = { //La acción que llamo cuando hago click
            numero = generarNumeroAleatorio()
        }
    )
}

@Composable
fun VistaNumeroAleatorio(
    numero: Int, //la fun recibe como parámetro un número entero
    cuandoHagoClick: () -> Unit // Función sin parámetros que no devuelve nada y que se ejecuta al tocar el botón

) {
    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )

    {
       Text(
           text = "Numero rnd: $numero",
           fontSize = 30.sp
       )

        Button(onClick = cuandoHagoClick) {
            Text(
                text = "Genera tu numero"
            )
        }
    }
}

/**
 * se llama a generar numero aleatorio
 * se obtiene un nuevo numero
 * ese numero se gurda en numero
 * como numero es estado, compose recompone la interzaf en cada click
 * y text se actualiza dinámicamente
 * 
 * generarNumeroAleatorio --> Contiene la logica
 * PantallaNumeroAleatorio() --> Maneja el estado
 * VistaNumeroAleatorio() --> Muiestra la interfaz
 * 
 * Tenemos una función que genera el número, una función que guarda el estado actual (es decir, el nuevo valor), y
 * una función que lo dibuja. Cuando el usuario toca el botón, cambia el estado y Compose lo redibuja en la interfaz con el nuevo valor.
 */
