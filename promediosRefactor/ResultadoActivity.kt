package ar.edu.gasesideales

import android.annotation.SuppressLint
import android.app.Activity
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

class ResultadoActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val nota1 = intent.getDoubleExtra(EXTRA_NOTA1, 0.0)
        val nota2 = intent.getDoubleExtra(EXTRA_NOTA2, 0.0)
        val nota3 = intent.getDoubleExtra(EXTRA_NOTA3, 0.0)
        val asistencia = intent.getDoubleExtra(EXTRA_ASISTENCIA, 0.0)
        val promedio = intent.getDoubleExtra(EXTRA_PROMEDIO, 0.0)
        val condicion = intent.getStringExtra(EXTRA_CONDICION) ?: "Sin condición"

        val historialRepository = HistorialRepository(this)

        val registroActual =
            "N1=${nota1.formato(1)} | N2=${nota2.formato(1)} | N3=${nota3.formato(1)} | " +
                    "Asist=${asistencia.formato(0)}% | Prom=${promedio.formato(2)} | $condicion"

        val yaGuardado = savedInstanceState?.getBoolean(STATE_GUARDADO) ?: false
        if (!yaGuardado) {
            historialRepository.agregarRegistro(registroActual)
        }

        val historial = historialRepository.obtenerHistorial()

        setContent {
            MaterialTheme {
                PantallaResultado(
                    nota1 = nota1,
                    nota2 = nota2,
                    nota3 = nota3,
                    asistencia = asistencia,
                    promedio = promedio,
                    condicion = condicion,
                    historial = historial
                )
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(STATE_GUARDADO, true)
    }

    companion object {
        const val EXTRA_NOTA1 = "extra_nota1"
        const val EXTRA_NOTA2 = "extra_nota2"
        const val EXTRA_NOTA3 = "extra_nota3"
        const val EXTRA_ASISTENCIA = "extra_asistencia"
        const val EXTRA_PROMEDIO = "extra_promedio"
        const val EXTRA_CONDICION = "extra_condicion"

        private const val STATE_GUARDADO = "state_guardado"
    }
}

@SuppressLint("ContextCastToActivity")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaResultado(
    nota1: Double,
    nota2: Double,
    nota3: Double,
    asistencia: Double,
    promedio: Double,
    condicion: String,
    historial: List<String>
) {
    val activity = LocalContext.current as? Activity

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Resultado académico") }
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
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Resultado actual",
                        style = MaterialTheme.typography.titleLarge
                    )

                    Text("Nota 1: ${nota1.formato(1)}")
                    Text("Nota 2: ${nota2.formato(1)}")
                    Text("Nota 3: ${nota3.formato(1)}")
                    Text("Asistencia: ${asistencia.formato(0)} %")
                    Text("Promedio: ${promedio.formato(2)}")
                    Text("Condición: $condicion")
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Últimos 5 cálculos",
                        style = MaterialTheme.typography.titleLarge
                    )

                    if (historial.isEmpty()) {
                        Text("No hay cálculos guardados.")
                    } else {
                        historial.forEachIndexed { index, item ->
                            Text("${index + 1}. $item")
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { activity?.finish() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Volver")
            }
        }
    }
}