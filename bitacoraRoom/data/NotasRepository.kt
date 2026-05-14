package ar.edu.bitacora.data

import kotlinx.coroutines.flow.Flow

class NotasRepository(
    private val notaDao: NotaDao
) {

    fun obtenerNotas(): Flow<List<NotaEntity>> {
        return notaDao.obtenerNotas()
    }

    suspend fun guardarNota(texto: String) {
        val nota = NotaEntity(
            texto = texto,
            fecha = System.currentTimeMillis()
        )

        notaDao.insertarNota(nota)
    }

    suspend fun borrarNotas() {
        notaDao.borrarTodas()
    }
}