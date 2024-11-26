package org.iesharia.aplicaciontareas.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.aplicaciontareas.data.Tarea
import com.example.aplicaciontareas.data.TipoTarea


@Database(entities = [Tarea::class, TipoTarea::class], version = 1)
abstract class DataBase : RoomDatabase() {

    abstract fun TareaDao(): TareaDao

    companion object {
        @Volatile
        private var INSTANCE: DataBase? = null

        fun getDataBase(context: Context): DataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DataBase::class.java,
                    "tareas_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

