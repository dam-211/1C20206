package ar.edu.bitacora.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NotaDao {

    @Insert
    suspend fun insertarNota(nota: NotaEntity)

    @Query("SELECT * FROM notas ORDER BY fecha DESC")
    fun obtenerNotas(): Flow<List<NotaEntity>>

    @Query("DELETE FROM notas")
    suspend fun borrarTodas()
}