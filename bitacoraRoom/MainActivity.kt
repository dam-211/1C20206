package ar.edu.bitacora

import android.os.Bundle
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
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
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import ar.edu.bitacora.data.BitacoraDatabase
import ar.edu.bitacora.data.NotaEntity
import ar.edu.bitacora.data.NotasRepository
import ar.edu.bitacora.data.PreferenciasRepository
import kotlinx.coroutines.launch

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
    val scope = rememberCoroutineScope()

    val preferenciasRepository = remember {
        PreferenciasRepository(context)
    }

    val database = remember {
        BitacoraDatabase.obtenerDatabase(context)
    }

    val notasRepository = remember {
        NotasRepository(database.notaDao())
    }

    var nombre by remember {
        mutableStateOf(preferenciasRepository.obtenerNombre())
    }

    var nota by remember {
        mutableStateOf("")
    }

    val notas by notasRepository
        .obtenerNotas()
        .collectAsState(initial = emptyList())

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
            text = "Ejemplo con SharedPreferences y Room",
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
                    scope.launch {
                        notasRepository.guardarNota(nota)
                        nota = ""
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar nota en Room")
        }

        TextButton(
            onClick = {
                scope.launch {
                    notasRepository.borrarNotas()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Borrar todas las notas")
        }

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Notas guardadas",
                    style = MaterialTheme.typography.titleMedium
                )

                if (notas.isEmpty()) {
                    Text("Todavía no hay notas guardadas.")
                } else {
                    notas.forEach { notaGuardada ->
                        NotaItem(notaGuardada)
                        Divider()
                    }
                }
            }
        }
    }
}

@Composable
fun NotaItem(nota: NotaEntity) {
    Column {
        Text(
            text = nota.texto,
            style = MaterialTheme.typography.bodyLarge
        )

        Text(
            text = formatearFecha(nota.fecha),
            style = MaterialTheme.typography.bodySmall
        )
    }
}

fun formatearFecha(fecha: Long): String {
    val formato = SimpleDateFormat(
        "yyyy-MM-dd HH:mm:ss",
        Locale.getDefault()
    )

    return formato.format(Date(fecha))
}