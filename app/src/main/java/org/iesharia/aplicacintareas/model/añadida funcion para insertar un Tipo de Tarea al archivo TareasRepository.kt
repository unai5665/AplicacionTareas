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

}
