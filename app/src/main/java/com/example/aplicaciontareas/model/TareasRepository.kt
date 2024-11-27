package org.iesharia.aplicaciontareas.model

import com.example.aplicaciontareas.data.Tarea
import com.example.aplicaciontareas.data.TipoTarea
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.iesharia.aplicaciontareas.data.TareaDao
import org.iesharia.aplicaciontareas.data.TareaDao.TareaConTipo


class TareasRepository(private val tareaDao: TareaDao) {

    suspend fun eliminarTipoYTareas(tipoTarea: TipoTarea) {
        withContext(Dispatchers.IO) {
            // Eliminar todas las tareas asociadas a este tipo
            tareaDao.eliminarTareasPorTipo(tipoTarea.id)

            // Eliminar el tipo de tarea
            tareaDao.eliminarTipoTarea(tipoTarea)
        }
    }

    suspend fun updateTipoTarea(tipoTarea: TipoTarea) {
        withContext(Dispatchers.IO) {
            tareaDao.updateTipoTarea(tipoTarea)
        }
    }


    suspend fun insertTarea(tarea: Tarea): Long {
        return withContext(Dispatchers.IO) {
            tareaDao.insertarTarea(tarea)  // Esto devuelve el ID generado.
        }
    }


    suspend fun getAllTiposTareas(): List<TipoTarea> {
        return withContext(Dispatchers.IO) {
            tareaDao.getAllTiposTareas()
        }
    }





    suspend fun insertTipoTarea(tipoTarea: TipoTarea) {
        withContext(Dispatchers.IO) {
            tareaDao.insertarTipoTarea(tipoTarea)
        }
    }

    suspend fun getTareasConTipos(): List<TareaConTipo> {
        return withContext(Dispatchers.IO) {
            tareaDao.getTareasConTipos()
        }
    }
    suspend fun updateTarea(tarea: Tarea) {
        withContext(Dispatchers.IO) {
            tareaDao.updateTarea(tarea)
        }
    }

    suspend fun deleteTarea(tarea: Tarea) {
        withContext(Dispatchers.IO) {
            tareaDao.deleteTarea(tarea)
        }
    }

}
