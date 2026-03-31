package ar.edu.miprimerproyecto


import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit


//El MainActivity es el punto de entrada de mi app
class MainActivity : ComponentActivity() {

    //Cuando crea la pantalla, ejecuta onCreate()
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        //Dentro de setContent le indicamos a Composable cual va a ser la interfaz que vamos a mostrar

        val nombreAlumno = "Anibal"
        val curso = "Desarrollo Movil"
        
        setContent {
            PantallaPrincipal(
                nombre = nombreAlumno,
                curso = curso
            )
        }
    }
}

//La función saludo, como esta marcada como composable, significa que puede dibujar parte de la interfza
//Mi primer compoable muestra el texto en pantalla
@Composable
fun PantallaPrincipal(nombre: String, curso: String){

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Mensaje("Hola $nombre", 30.sp, Color.Blue)
        Mensaje("Tu curso es: $curso", 30.sp, Color.Red)
        Mensaje("Bienvenida", 30.sp, Color.Green)
    }
}

@Composable
fun Mensaje(texto: String, tamanio: TextUnit, color: Color) {
    Text(
        text= texto,
        color = color,
        fontSize = tamanio
    )
}
