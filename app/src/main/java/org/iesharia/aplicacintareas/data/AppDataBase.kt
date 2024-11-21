package org.iesharia.aplicacintareas.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Tarea::class, TipoTarea::class], version = 1)
abstract class AppDataBase : RoomDatabase() {

    abstract fun TareaDao(): TareaDao

    companion object {
        @Volatile
        private var INSTANCE: Database? = null

        fun getDatabase(context: Context): Database {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "tareas_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}