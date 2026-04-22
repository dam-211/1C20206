package ar.edu.gasesideales

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            MaterialTheme {
                PantallaIngreso()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaIngreso() {

    val context = LocalContext.current

    var presionTexto by rememberSaveable { mutableStateOf("") }
    var volumenTexto by rememberSaveable { mutableStateOf("") }
    var mensajeError by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ley de los gases ideales") }
            )
        }
    ) { paddingInterno ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingInterno)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Ingreso de parámetros",
                style = MaterialTheme.typography.headlineMedium
            )

            Text(
                text = "Usaremos PV = nRT",
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = "Supuestos: n=1 mol, R=0.082 L*atm*mol",
                style = MaterialTheme.typography.bodyMedium
            )

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = presionTexto,
                        onValueChange = { presionTexto = it },
                        label = { Text("Presión (atm)") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = volumenTexto,
                        onValueChange = { volumenTexto = it },
                        label = { Text("Volumen (Litros)") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Button(
                        onClick = {
                            val presion = presionTexto.replace(',', '.').toDoubleOrNull()
                            val volumen = volumenTexto.replace(',', '.').toDoubleOrNull()

                            when {
                                presion == null || volumen == null -> { mensajeError = "Ingresá datos válidos" }


                            presion <=0 || volumen <= 0 -> { mensajeError = "La presion y el volumen deben ser mayores que cero" }

                            else -> {
                                mensajeError = "Todo ok"

                                val n = 1.0
                                val r = 0.082
                                val temperatura = (presion * volumen) / (n * r)

                                val intent = Intent(context, ResultadoActivity::class.java).apply {
                                    putExtra(ResultadoActivity.EXTRA_PRESION, presion)
                                    putExtra(ResultadoActivity.EXTRA_VOLUMEN, volumen)
                                    putExtra(ResultadoActivity.EXTRA_TEMPERATURA, temperatura)
                                }

                                context.startActivity(intent)
                            }
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Calcular")
                    }
                }
            }

            if (mensajeError.isNotBlank()) {
                Text(
                    text = mensajeError,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
