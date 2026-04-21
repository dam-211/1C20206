import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PantallaPerfil()
        }
    }
}

class PerfilPreferences(context: Context) {
    private val prefs = context.getSharedPreferences("perfil_prefs", Context.MODE_PRIVATE)

    fun guardarPerfil(nombre: String, email: String, carrera: String) {
        prefs.edit()
            .putString("nombre", nombre)
            .putString("email", email)
            .putString("carrera", carrera)
            .apply()
    }

    fun obtenerNombre(): String = prefs.getString("nombre", "") ?: ""
    fun obtenerEmail(): String = prefs.getString("email", "") ?: ""
    fun obtenerCarrera(): String = prefs.getString("carrera", "") ?: ""
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaPerfil() {
    val context = LocalContext.current
    val perfilPrefs = remember(context) { PerfilPreferences(context) }

    var nombreDraft by rememberSaveable { mutableStateOf("") }
    var emailDraft by rememberSaveable { mutableStateOf("") }
    var carreraDraft by rememberSaveable { mutableStateOf("") }

    var nombreGuardado by remember { mutableStateOf(perfilPrefs.obtenerNombre()) }
    var emailGuardado by remember { mutableStateOf(perfilPrefs.obtenerEmail()) }
    var carreraGuardada by remember { mutableStateOf(perfilPrefs.obtenerCarrera()) }

    var mensaje by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Perfil del estudiante") }
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
                text = "Carga de perfil",
                style = MaterialTheme.typography.headlineMedium
            )

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {

                    OutlinedTextField(
                        value = nombreDraft,
                        onValueChange = { nombreDraft = it },
                        label = { Text("Nombre") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = emailDraft,
                        onValueChange = { emailDraft = it },
                        label = { Text("Email") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = carreraDraft,
                        onValueChange = { carreraDraft = it },
                        label = { Text("Carrera") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {
                            if (nombreDraft.isBlank() || emailDraft.isBlank() || carreraDraft.isBlank()) {
                                mensaje = "Completa todos los campos"
                            } else {
                                perfilPrefs.guardarPerfil(nombreDraft, emailDraft, carreraDraft)

                                nombreGuardado = perfilPrefs.obtenerNombre()
                                emailGuardado = perfilPrefs.obtenerEmail()
                                carreraGuardada = perfilPrefs.obtenerCarrera()

                                mensaje = "Perfil guardado correctamente"
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Guardar perfil")
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            nombreDraft = ""
                            emailDraft = ""
                            carreraDraft = ""
                            mensaje = "Borrador limpiado"
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Limpiar borrador")
                    }
                }
            }

            if (mensaje.isNotBlank()) {
                Text(
                    text = mensaje,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Perfil guardado",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text("Nombre: $nombreGuardado")
                    Text("Email: $emailGuardado")
                    Text("Carrera: $carreraGuardada")
                }
            }
        }
    }
}
