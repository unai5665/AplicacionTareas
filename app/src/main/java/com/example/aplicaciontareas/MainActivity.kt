package com.example.aplicaciontareas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import org.iesharia.aplicaciontareas.data.DataBase
import org.iesharia.aplicaciontareas.iu.TareasScreen
import org.iesharia.aplicaciontareas.model.TareasRepository

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = DataBase.getDataBase(this)
        val repository = TareasRepository(database.TareaDao())

        setContent {
            MaterialTheme {
                TareasScreen(repository = repository)
            }
        }
    }
}

