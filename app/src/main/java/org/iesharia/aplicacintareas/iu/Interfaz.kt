package org.iesharia.aplicacintareas.iu
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.TextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.iesharia.aplicaciontareas.data.Tarea
import org.iesharia.aplicaciontareas.data.TareaDao.TareaConTipo
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import org.iesharia.aplicaciontareas.model.TareasRepository

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
            ){
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
                }
        }}


















     
     }
 
