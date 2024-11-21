package org.iesharia.aplicacintareas.model

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.iesharia.aplicacintareas.data.Tarea
import org.iesharia.aplicacintareas.data.TareaDao
import org.iesharia.aplicacintareas.data.TipoTarea
import org.iesharia.aplicacintareas.data.TareaDao.TareaConTipo


class TareasRepository(private val tareaDao: TareaDao) {

    suspend fun insertTarea(tarea: Tarea) {
        withContext(Dispatchers.IO) {
            tareaDao.insertarTarea(tarea)
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
