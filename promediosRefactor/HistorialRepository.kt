package ar.edu.gasesideales

import android.content.Context

class HistorialRepository(context: Context) {

    private val prefs =
        context.getSharedPreferences("promedio_academico_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val CLAVE_HISTORIAL = "clave_historial"
        private const val SEPARADOR = "|||"
    }

    fun agregarRegistro(registro: String) {
        val historialActual = obtenerHistorial().toMutableList()
        historialActual.add(0, registro)

        val ultimosCinco = historialActual.take(5)

        prefs.edit()
            .putString(CLAVE_HISTORIAL, ultimosCinco.joinToString(SEPARADOR))
            .apply()
    }

    fun obtenerHistorial(): List<String> {
        val datos = prefs.getString(CLAVE_HISTORIAL, "") ?: ""
        if (datos.isBlank()) return emptyList()
        return datos.split(SEPARADOR)
    }
}