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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import ar.edu.gasesideales.logic.PromedioAcademicoLogic
import ar.edu.gasesideales.logic.ResultadoCalculo
import ar.edu.gasesideales.ui.theme.GasesidealesTheme
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GasesidealesTheme {
                PantallaIngreso()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaIngreso() {
    val context = LocalContext.current

    //El formulario usa rememberSaveable y conserva los datos si el activity se recrea

    //Historial utiliza SharedPreference dado que debe sobrevivir aunque la app se cierre

    //ar.edu.gasesideales

    var nota1Texto by rememberSaveable { mutableStateOf("") }
    var nota2Texto by rememberSaveable { mutableStateOf("") }
    var nota3Texto by rememberSaveable { mutableStateOf("") }
    var asistenciaTexto by rememberSaveable { mutableStateOf("") }
    var mensajeError by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Promedio académico") }
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
                text = "Ingreso de datos",
                style = MaterialTheme.typography.headlineMedium
            )

            Text(
                text = "Ingrese 3 notas y el porcentaje de asistencia.",
                style = MaterialTheme.typography.bodyLarge
            )

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = nota1Texto,
                        onValueChange = { nota1Texto = it },
                        label = { Text("Nota 1") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = nota2Texto,
                        onValueChange = { nota2Texto = it },
                        label = { Text("Nota 2") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = nota3Texto,
                        onValueChange = { nota3Texto = it },
                        label = { Text("Nota 3") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = asistenciaTexto,
                        onValueChange = { asistenciaTexto = it },
                        label = { Text("Asistencia (%)") },
                        modifier = Modifier.fillMaxWidth()
                    )
//El composable ya no valida ni calucla niguna lógica
                    //La lógica queda conectada en un unico lugar
                    //Si cambia la regla de negocio, se cambie en PromedioAcademicoLogic
                    //La UI solo se ocupa de mostrar campos, leer entradas, y navegar.


                    Button(
                        onClick = {
                                when (val resultado = PromedioAcademicoLogic.calcularResultado(
                                    nota1Texto,
                                    nota2Texto,
                                    nota3Texto,
                                    asistenciaTexto
                                )) {
                                    is ResultadoCalculo.Error -> {
                                        mensajeError = resultado.mensaje
                                    }

                                    is ResultadoCalculo.Exito -> {
                                        mensajeError = ""

                                        val datos = resultado.resultado

                                        val intent = Intent(context, ResultadoActivity::class.java).apply {
                                            putExtra(ResultadoActivity.EXTRA_NOTA1, datos.nota1)
                                            putExtra(ResultadoActivity.EXTRA_NOTA2, datos.nota2)
                                            putExtra(ResultadoActivity.EXTRA_NOTA3, datos.nota3)
                                            putExtra(ResultadoActivity.EXTRA_ASISTENCIA, datos.asistencia)
                                            putExtra(ResultadoActivity.EXTRA_PROMEDIO, datos.promedio)
                                            putExtra(ResultadoActivity.EXTRA_CONDICION, datos.condicion)
                                        }

                                        context.startActivity(intent)
                                    }
                                }
                         },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Calcular resultado")
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

fun String.normalizarNumero(): String = trim().replace(',', '.')

fun Double.formato(decimales: Int): String {
    return "%.${decimales}f".format(Locale.US, this)
}