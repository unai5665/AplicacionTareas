package org.iesharia.aplicacintareas.data

import androidx.room.*

@Dao interface TareaDao {
    @Insert
    suspend fun insertarTarea(tarea: Tarea)



}