package org.iesharia.aplicaciontareas.iu

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.example.aplicaciontareas.data.Tarea
import com.example.aplicaciontareas.data.TipoTarea
import kotlinx.coroutines.launch
import org.iesharia.aplicaciontareas.data.TareaDao
import org.iesharia.aplicaciontareas.model.TareasRepository

val azulCielo = Color(0xFF2F2F2F)
val rosaFucsia = Color(0xFF8C8C8C)
val rosaLavanda = Color(0xFFA9A9A9)

@Composable
fun TareasScreen(repository: TareasRepository) {
    val tareas = remember { mutableStateListOf<TareaDao.TareaConTipo>() }
    val tiposTareas = remember { mutableStateListOf<TipoTarea>() }
    val scope = rememberCoroutineScope()
    var editarTarea by remember { mutableStateOf<TareaDao.TareaConTipo?>(null) }

    LaunchedEffect(Unit) {
        tareas.addAll(repository.getTareasConTipos())
        tiposTareas.addAll(repository.getAllTiposTareas()) // Cargar los tipos al iniciar
    }

    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var tipoSeleccionado by remember { mutableStateOf<TipoTarea?>(null) }
    var mostrarDialogoNuevoTipo by remember { mutableStateOf(false) }
    var menuExpandido by remember { mutableStateOf(false) }
    var mostrarDialogoEditarTipo by remember { mutableStateOf(false) }

    if (mostrarDialogoEditarTipo && tipoSeleccionado != null) {
        Dialog(onDismissRequest = { mostrarDialogoEditarTipo = false }) {
            Surface(modifier = Modifier.padding(16.dp)) {
                var nuevoTitulo by remember { mutableStateOf(tipoSeleccionado!!.titulo) }

                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Editar Tipo", style = TextStyle(fontSize = 18.sp))
                    TextField(
                        value = nuevoTitulo,
                        onValueChange = { nuevoTitulo = it },
                        placeholder = { Text("Nuevo Título del Tipo") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(onClick = { mostrarDialogoEditarTipo = false }) {
                            Text("Cancelar")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(onClick = {
                            if (nuevoTitulo.isNotEmpty()) {
                                scope.launch {
                                    // Actualizar tipo en la base de datos
                                    val tipoActualizado = tipoSeleccionado!!.copy(titulo = nuevoTitulo)
                                    repository.updateTipoTarea(tipoActualizado)

                                    // Recargar los tipos y tareas actualizados
                                    tiposTareas.clear()
                                    tiposTareas.addAll(repository.getAllTiposTareas())

                                    tareas.clear()
                                    tareas.addAll(repository.getTareasConTipos())

                                    if (tipoSeleccionado?.id == tipoActualizado.id) {
                                        tipoSeleccionado = tipoActualizado
                                    }

                                    // Cerrar el diálogo
                                    mostrarDialogoEditarTipo = false
                                }
                            }
                        }) {
                            Text("Guardar")
                        }
                    }
                }
            }
        }
    }


    if (mostrarDialogoNuevoTipo) {
        Dialog(onDismissRequest = { mostrarDialogoNuevoTipo = false }) {
            Surface(modifier = Modifier.padding(16.dp)) {
                var nuevoTipoTitulo by remember { mutableStateOf("") }

                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Agregar Nuevo Tipo", style = TextStyle(fontSize = 18.sp))
                    TextField(
                        value = nuevoTipoTitulo,
                        onValueChange = { nuevoTipoTitulo = it },
                        placeholder = { Text("Título del Tipo") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(onClick = { mostrarDialogoNuevoTipo = false }) {
                            Text("Cancelar")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(onClick = {
                            if (nuevoTipoTitulo.isNotEmpty()) {
                                scope.launch {
                                    val nuevoTipo = TipoTarea(titulo = nuevoTipoTitulo)
                                    repository.insertTipoTarea(nuevoTipo)
                                    tiposTareas.clear()
                                    tiposTareas.addAll(repository.getAllTiposTareas())
                                }
                                mostrarDialogoNuevoTipo = false
                            }
                        }) {
                            Text("Agregar")
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

                    TextField(
                        value = nuevoTitulo,
                        onValueChange = { nuevoTitulo = it },
                        placeholder = { Text("Nuevo Título") }
                    )
                    TextField(
                        value = nuevaDescripcion,
                        onValueChange = { nuevaDescripcion = it },
                        placeholder = { Text("Nueva Descripción") }
                    )
                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
                    ) {
                        Button(onClick = { editarTarea = null }) {
                            Text("Cancelar")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(onClick = {
                            scope.launch {
                                repository.updateTarea(
                                    Tarea(
                                        id = editarTarea!!.id,
                                        titulo = nuevoTitulo,
                                        descripcion = nuevaDescripcion,
                                        id_tipostareas = tiposTareas.find { it.titulo == editarTarea!!.tipo }?.id ?: 0
                                    )
                                )
                                editarTarea = null
                                tareas.clear()
                                tareas.addAll(repository.getTareasConTipos())
                            }
                        }) {
                            Text("Guardar")
                        }
                    }
                }
            }
        }
    }



    Box(modifier = Modifier.fillMaxSize().background(rosaFucsia)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Tareas", modifier = Modifier.padding(vertical = 20.dp), style = TextStyle(fontSize = 30.sp))

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

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .background(rosaLavanda)
                    .clickable { menuExpandido = !menuExpandido }
            ) {
                Text(
                    text = tipoSeleccionado?.titulo ?: "Seleccionar Tipo",
                    modifier = Modifier.padding(16.dp),
                    style = TextStyle(color = Color.Black)
                )
                DropdownMenu(
                    expanded = menuExpandido,
                    onDismissRequest = { menuExpandido = false }
                ) {
                    tiposTareas.forEach { tipo ->
                        DropdownMenuItem(
                            onClick = {
                                tipoSeleccionado = tipo
                                menuExpandido = false
                            }
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = tipo.titulo,
                                    modifier = Modifier.weight(1f)
                                )
                                IconButton(
                                    onClick = {
                                        scope.launch {
                                            repository.eliminarTipoYTareas(tipo)
                                            tiposTareas.remove(tipo)
                                            tareas.clear()
                                            tareas.addAll(repository.getTareasConTipos())
                                            
                                            if (tipoSeleccionado == tipo) {
                                                tipoSeleccionado = null
                                            }
                                        }
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Eliminar Tipo",
                                        tint = Color.Black
                                    )
                                }
                                IconButton(
                                    onClick = {
                                        tipoSeleccionado = tipo // Guarda el tipo para editarlo
                                        mostrarDialogoEditarTipo = true
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = "Editar Tipo",
                                        tint = Color.Black
                                    )
                                }
                            }
                        }
                    }
                }
            }

            if (tipoSeleccionado != null) {
                Text("Seleccionado: ${tipoSeleccionado!!.titulo}")
            }

            Button(
                onClick = {
                    if (titulo.isNotEmpty() && tipoSeleccionado != null) {
                        scope.launch {
                            val nuevaTarea = Tarea(
                                titulo = titulo,
                                descripcion = descripcion,
                                id_tipostareas = tipoSeleccionado!!.id
                            )
                            val idGenerado = repository.insertTarea(nuevaTarea)
                            tareas.add(
                                TareaDao.TareaConTipo(
                                    id = idGenerado.toInt(),
                                    titulo = nuevaTarea.titulo,
                                    descripcion = nuevaTarea.descripcion,
                                    tipo = tipoSeleccionado!!.titulo
                                )
                            )
                            titulo = ""
                            descripcion = ""
                            tipoSeleccionado = null
                        }
                    }
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Agregar Tarea", color = Color.White)
            }

            Button(
                onClick = { mostrarDialogoNuevoTipo = true },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Agregar Nuevo Tipo")
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
                                    style = TextStyle(fontSize = 15.sp)
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
                                            id_tipostareas = tiposTareas.find { it.titulo == tarea.tipo }?.id ?: 0
                                        )
                                        repository.deleteTarea(tareaEliminar)
                                        tareas.remove(tarea)
                                    }
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Eliminar tarea",
                                        tint = Color.Red
                                    )
                                }
                                IconButton(onClick = {
                                    editarTarea = tarea
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = "Editar tarea"
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
