package ar.edu.bitacora.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [NotaEntity::class],
    version = 1
)
abstract class BitacoraDatabase : RoomDatabase() {

    abstract fun notaDao(): NotaDao

    companion object {
        @Volatile
        private var INSTANCE: BitacoraDatabase? = null

        fun obtenerDatabase(context: Context): BitacoraDatabase {
            return INSTANCE ?: synchronized(this) {
                val instancia = Room.databaseBuilder(
                    context.applicationContext,
                    BitacoraDatabase::class.java,
                    "bitacora_database"
                ).build()

                INSTANCE = instancia
                instancia
            }
        }
    }
}