package org.iesharia.aplicaciontareas.iu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.launch
import org.iesharia.aplicaciontareas.data.Tarea
import org.iesharia.aplicaciontareas.data.TipoTarea
import org.iesharia.aplicaciontareas.data.TareaDao
import org.iesharia.aplicaciontareas.model.TareasRepository

/
val azulCielo = Color(0xFF00C4FF)
val rosaFucsia = Color(0xFFFF1493)
val rosaLavanda = Color(0xFFEE82EE)  

@Composable
fun TareasScreen(repository: TareasRepository) {
    val tareas = remember { mutableStateListOf<TareaDao.TareaConTipo>() }
    val scope = rememberCoroutineScope()
    var editarTarea by remember { mutableStateOf<TareaDao.TareaConTipo?>(null) }

   
    LaunchedEffect(Unit) {
        tareas.addAll(repository.getTareasConTipos())
    }

    
    Box(modifier = Modifier
        .fillMaxSize()
        .background(rosaFucsia)) {
        Column(modifier = Modifier.padding(16.dp)) {

           
            Text(
                "Tareas",
                modifier = Modifier.padding(vertical = 20.dp),
                style = TextStyle(fontSize = 30.sp, color = Color.Black)
            )

            
            var titulo by remember { mutableStateOf("") }
            var descripcion by remember { mutableStateOf("") }
            var tipo by remember { mutableStateOf("") }

            Row {
                TextField(
                    value = titulo,
                    onValueChange = { titulo = it },
                    placeholder = { Text("Título") },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                        .background(rosaLavanda)
                )
                TextField(
                    value = tipo,
                    onValueChange = { tipo = it },
                    placeholder = { Text("Tipo de Tarea") },
                    modifier = Modifier
                        .weight(1f)
                        .background(rosaLavanda)
                )
            }
            TextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                placeholder = { Text("Descripción") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .background(rosaLavanda)
                    .height(50.dp)
            )


            Button(
                onClick = {
                    scope.launch {
                        val tipoExistente = repository.getTipoTareaPorTitulo(tipo)
                        val tipoId = if (tipoExistente != null) {
                            tipoExistente.id
                        } else {
                            
                            val nuevoTipoTarea = TipoTarea(titulo = tipo)
                            repository.insertTipoTarea(nuevoTipoTarea)
                            repository.getTipoTareaPorTitulo(tipo)?.id ?: 0
                        }

                        val nuevaTarea = Tarea(
                            titulo = titulo,
                            descripcion = descripcion,
                            id_tipostareas = tipoId
                        )
                        val idGenerado = repository.insertTarea(nuevaTarea)

                        val tareaConTipo = TareaDao.TareaConTipo(
                            id = idGenerado.toInt(),
                            titulo = nuevaTarea.titulo,
                            descripcion = nuevaTarea.descripcion,
                            tipo = tipo
                        )
                        tareas.add(tareaConTipo)

                        titulo = ""
                        descripcion = ""
                        tipo = ""
                    }
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Agregar Tarea", color = Color.White)
            }

            LazyColumn(modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp)) {
                items(tareas) { tarea ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        backgroundColor = azulCielo
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Column(Modifier.weight(1f)) {
                                Text(
                                    text = "Tipo: ${tarea.tipo}",
                                    color = Color.Black,
                                    style = TextStyle(fontSize = 15.sp),
                                    modifier = Modifier.padding(end = 15.dp)

                                )
                                Text(
                                    text = "Título: ${tarea.titulo}",
                                    color = Color.Black,
                                    style = TextStyle(fontSize = 19.sp),
                                    modifier = Modifier.padding(top = 25.dp, bottom = 20.dp)
                                )
                                Text(
                                    text = "Descripción: ${tarea.descripcion}",
                                    color = Color.Black,
                                    style = TextStyle(fontSize = 15.sp),

                                )
                            }

                            
                            Column(
                                horizontalAlignment = Alignment.End
                            ) {
                                IconButton(onClick = {
                                    scope.launch {
                                        val tareaEliminar = Tarea(
                                            id = tarea.id,
                                            titulo = tarea.titulo,
                                            descripcion = tarea.descripcion,
                                            id_tipostareas = 1 
                                        )
                                        repository.deleteTarea(tareaEliminar)
                                        tareas.remove(tarea)
                                    }
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Eliminar",
                                        tint = Color.Black
                                    )
                                }

                                IconButton(onClick = { editarTarea = tarea }) {
                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = "Editar",
                                        tint = Color.Blue
                                    )
                                }
                                Text(
                                    text = "ID: ${tarea.id}",
                                    color = Color.Black,
                                    style = TextStyle(fontSize = 12.sp),
                                )
                            }
                        }
                    }
                }
            }
        }
    }



    
    if (editarTarea != null) {
        Dialog(onDismissRequest = { editarTarea = null }) {
            Surface(modifier = Modifier.padding(16.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    var nuevoTitulo by remember { mutableStateOf(editarTarea!!.titulo) }
                    var nuevaDescripcion by remember { mutableStateOf(editarTarea!!.descripcion) }
                    var nuevoTipo by remember { mutableStateOf(editarTarea!!.tipo) }

                    
                    TextField(
                        value = nuevoTitulo,
                        onValueChange = { nuevoTitulo = it },
                        label = { Text("Nuevo Título") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    TextField(
                        value = nuevaDescripcion,
                        onValueChange = { nuevaDescripcion = it },
                        label = { Text("Nueva Descripción") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    TextField(
                        value = nuevoTipo,
                        onValueChange = { nuevoTipo = it },
                        label = { Text("Nuevo Tipo") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(onClick = { editarTarea = null }) {
                            Text("Cancelar")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(onClick = {
                            scope.launch {
                                // Verificar si el tipo de tarea ya existe
                                val tipoExistente = repository.getTipoTareaPorTitulo(nuevoTipo)
                                val tipoId = if (tipoExistente != null) {
                                    tipoExistente.id
                                } else {
                                    // Si no existe, insertarlo
                                    val nuevoTipoTarea = TipoTarea(titulo = nuevoTipo)
                                    repository.insertTipoTarea(nuevoTipoTarea)
                                    repository.getTipoTareaPorTitulo(nuevoTipo)?.id ?: 0
                                }

                                // Crear la tarea actualizada con el ID del tipo correcto
                                val tareaActualizada = Tarea(
                                    id = editarTarea!!.id,
                                    titulo = nuevoTitulo,
                                    descripcion = nuevaDescripcion,
                                    id_tipostareas = tipoId
                                )
                                repository.updateTarea(tareaActualizada)

                                // Actualizar la lista local de tareas
                                val tareaConTipo = TareaDao.TareaConTipo(
                                    id = tareaActualizada.id,
                                    titulo = nuevoTitulo,
                                    descripcion = nuevaDescripcion,
                                    tipo = nuevoTipo
                                )
                                val index = tareas.indexOf(editarTarea!!)
                                tareas[index] = tareaConTipo

                                // Cerrar el diálogo de edición
                                editarTarea = null
                            }
                        }) {
                            Text("Guardar")
                        }
                    } 
                }
            }
        }
    }
}

