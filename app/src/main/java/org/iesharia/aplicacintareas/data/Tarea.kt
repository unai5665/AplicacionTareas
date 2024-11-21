package org.iesharia.aplicacintareas.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tareas")
data class Tarea( @PrimaryKey(autoGenerate = true)
                    val id: Int = 0,
                    val titulo: String,
                    val descripcion: String,
                    val id_tipostareas: Int
)

@Entity(tableName = "tipostareas")
data class TipoTarea( @PrimaryKey(autoGenerate = true)
                  val id: Int = 0,
                  val titulo: String
)