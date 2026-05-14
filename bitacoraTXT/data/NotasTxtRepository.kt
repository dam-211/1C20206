package ar.edu.bitacora.data

import android.content.Context
import java.io.FileNotFoundException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NotasTxtRepository(
    private val context: Context
) {
    private val nombreArchivo = "notas.txt"

    fun guardarNota(nota: String) {
        val fecha = SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss",
            Locale.getDefault()
        ).format(Date())

        val texto = "[$fecha] $nota\n"

        context.openFileOutput(nombreArchivo, Context.MODE_APPEND).use { output ->
            output.write(texto.toByteArray())
        }
    }

    fun leerNotas(): String {
        return try {
            context.openFileInput(nombreArchivo).bufferedReader().use {
                it.readText()
            }
        } catch (e: FileNotFoundException) {
            ""
        }
    }

    fun borrarNotas() {
        context.deleteFile(nombreArchivo)
    }
}