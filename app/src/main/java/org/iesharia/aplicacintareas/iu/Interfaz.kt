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

val azulCielo = Color(0xFF00C4FF) // Rosa fuerte
val rosaFucsia = Color(0xFFFF1493) // Rosa fucsia
val rosaLavanda = Color(0xFFEE82EE)  // Rosa lavanda
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
                            // Insertar el nuevo tipo de tarea y obtener su ID
                            val nuevoTipoTarea = TipoTarea(titulo = tipo)
                            repository.insertTipoTarea(nuevoTipoTarea)
                            repository.getTipoTareaPorTitulo(tipo)?.id ?: 0
                        }

                        // Insertar la tarea con el ID del tipo correcto
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

                        // Limpiar los campos del formulario
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
                            Column(Modifier.weight(1f)) {}
                        }
                    }
                }
             }








            
         }
        }


















     
     }
 
