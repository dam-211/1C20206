package ar.edu.bitacora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import ar.edu.bitacora.data.NotasTxtRepository
import ar.edu.bitacora.data.PreferenciasRepository

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    BitacoraApp()
                }
            }
        }
    }
}

@Composable
fun BitacoraApp() {
    val context = LocalContext.current.applicationContext

    val preferenciasRepository = remember {
        PreferenciasRepository(context)
    }

    val notasRepository = remember {
        NotasTxtRepository(context)
    }

    var nombre by remember {
        mutableStateOf(preferenciasRepository.obtenerNombre())
    }

    var nota by remember {
        mutableStateOf("")
    }

    var notasGuardadas by remember {
        mutableStateOf(notasRepository.leerNotas())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Bitácora simple",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Ejemplo con SharedPreferences y archivo de texto",
            style = MaterialTheme.typography.bodyMedium
        )

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre del usuario") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                preferenciasRepository.guardarNombre(nombre)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar nombre")
        }

        Text(
            text = if (nombre.isNotBlank()) {
                "Hola, $nombre"
            } else {
                "No hay nombre guardado"
            }
        )

        OutlinedTextField(
            value = nota,
            onValueChange = { nota = it },
            label = { Text("Escribí una nota") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3
        )

        Button(
            onClick = {
                if (nota.isNotBlank()) {
                    notasRepository.guardarNota(nota)
                    notasGuardadas = notasRepository.leerNotas()
                    nota = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar nota en archivo")
        }

        TextButton(
            onClick = {
                notasRepository.borrarNotas()
                notasGuardadas = notasRepository.leerNotas()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Borrar notas")
        }

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Notas guardadas",
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = if (notasGuardadas.isBlank()) {
                        "Todavía no hay notas guardadas."
                    } else {
                        notasGuardadas
                    }
                )
            }
        }
    }
}