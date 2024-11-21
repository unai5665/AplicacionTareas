package org.iesharia.aplicacintareas.data

import androidx.room.*

@Dao interface TareaDao {
    @Insert
    suspend fun insertarTarea(tarea: Tarea)

    @Insert
    suspend fun insertarTipoTarea(tipoTarea: TipoTarea)

    @Query("""
        SElECT Tareas.*, TiposTareas.titulo AS tipoTareasTitulo
        FROM tareas
        INNER JOIN tipostareas ON Tareas.id_tipostareas = tipostareas.id
    """
    )
    suspend fun getTareasConTipos(): List<TareaConTipo>

    @Update
    suspend fun updateTarea(tarea: Tarea)

    @Delete
    suspend fun deleteTarea(tarea: Tarea)

}