package ar.edu.bitacora.data

import android.content.Context

class PreferenciasRepository(context: Context) {

    private val prefs = context.getSharedPreferences(
        "configuracion_app",
        Context.MODE_PRIVATE
    )

    fun guardarNombre(nombre: String) {
        prefs.edit()
            .putString("nombre_usuario", nombre)
            .apply()
    }

    fun obtenerNombre(): String {
        return prefs.getString("nombre_usuario", "") ?: ""
    }

    fun borrarNombre() {
        prefs.edit()
            .remove("nombre_usuario")
            .apply()
    }
}