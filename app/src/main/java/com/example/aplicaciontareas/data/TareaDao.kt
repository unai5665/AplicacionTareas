package org.iesharia.aplicaciontareas.data

import androidx.room.*
import com.example.aplicaciontareas.data.Tarea
import com.example.aplicaciontareas.data.TipoTarea

@Dao
interface TareaDao {

    @Query("DELETE FROM tareas WHERE id_tipostareas = :tipoId")
    suspend fun eliminarTareasPorTipo(tipoId: Int)

    @Delete
    suspend fun eliminarTipoTarea(tipoTarea: TipoTarea)

    @Update
    suspend fun updateTipoTarea(tipoTarea: TipoTarea)


    @Insert
    suspend fun insertarTarea(tarea: Tarea): Long

    @Query("SELECT * FROM tipostareas WHERE titulo = :titulo LIMIT 1")
    suspend fun getTipoTareaPorTitulo(titulo: String): TipoTarea?

    @Query("SELECT * FROM tipostareas")
    suspend fun getAllTiposTareas(): List<TipoTarea>



    @Insert
    suspend fun insertarTipoTarea(tipoTarea: TipoTarea)

    @Query("""
        SELECT tareas.*, tipostareas.titulo AS tipo
        FROM tareas
        INNER JOIN tipostareas ON tareas.id_tipostareas = tipostareas.id
    """)

    suspend fun getTareasConTipos(): List<TareaConTipo>

    @Update
    suspend fun updateTarea(tarea: Tarea)

    @Delete
    suspend fun deleteTarea(tarea: Tarea)

    data class TareaConTipo(
        val id: Int,
        val titulo: String,
        val descripcion: String,
        val tipo: String
    )
}
