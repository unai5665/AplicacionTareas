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
     }
