package ar.edu.miprimerproyecto


import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight


//El MainActivity es el punto de entrada de mi app
class MainActivity : ComponentActivity() {

    //Cuando crea la pantalla, ejecuta onCreate()
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        //Dentro de setContent le indicamos a Composable cual va a ser la interfaz que vamos a mostrar
        setContent {
            Saludo("IFTS 18")
        }
    }
}

//La función saludo, como esta marcada como composable, significa que puede dibujar parte de la interfza
//Mi primer compoable muestra el texto en pantalla
@Composable
fun Saludo(nombre: String){
    Box (
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text= "Hola $nombre",
            color = Color.Red,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp
        )
    }
}
