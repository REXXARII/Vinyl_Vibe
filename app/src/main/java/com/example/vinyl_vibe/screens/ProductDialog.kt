package com.example.vinyl_vibe.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.vinyl_vibe.model.Vinyl

@Composable
fun ProductDialog(
    vinyl: Vinyl? = null, // Si es null, es AGREGAR. Si trae datos, es EDITAR.
    onDismiss: () -> Unit,
    onConfirm: (Vinyl) -> Unit
) {
    // Variables para guardar lo que escribe el usuario
    var titulo by remember { mutableStateOf(vinyl?.titulo ?: "") }
    var artista by remember { mutableStateOf(vinyl?.artista ?: "") }
    var genero by remember { mutableStateOf(vinyl?.genero ?: "") }
    var precio by remember { mutableStateOf(vinyl?.precio?.toString() ?: "") }
    var imagenUrl by remember { mutableStateOf(vinyl?.imagenUrl ?: "") }

    Dialog(onDismissRequest = onDismiss) {
        Card(modifier = Modifier.padding(16.dp)) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = if (vinyl == null) "Nuevo Disco" else "Editar Disco",
                    style = MaterialTheme.typography.headlineSmall
                )

                // Campos de texto
                OutlinedTextField(value = titulo, onValueChange = { titulo = it }, label = { Text("Título") })
                OutlinedTextField(value = artista, onValueChange = { artista = it }, label = { Text("Artista") })
                OutlinedTextField(value = genero, onValueChange = { genero = it }, label = { Text("Género") })

                OutlinedTextField(
                    value = precio,
                    onValueChange = { precio = it },
                    label = { Text("Precio") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                OutlinedTextField(
                    value = imagenUrl,
                    onValueChange = { imagenUrl = it },
                    label = { Text("URL Imagen (GitHub)") },
                    placeholder = { Text("https://raw.github...") }
                )

                // Botones
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
                ) {
                    TextButton(onClick = onDismiss) { Text("Cancelar") }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = {
                        // Creamos el objeto Vinyl con los datos nuevos
                        val newVinyl = Vinyl(
                            id = vinyl?.id ?: 0, // Si es nuevo el ID es 0 (el backend lo asigna)
                            titulo = titulo,
                            artista = artista,
                            genero = genero,
                            precio = precio.toIntOrNull() ?: 0,
                            descripcion = vinyl?.descripcion ?: "Sin descripción",
                            imagenUrl = imagenUrl
                        )
                        onConfirm(newVinyl) // Enviamos el disco listo
                    }) {
                        Text("Guardar")
                    }
                }
            }
        }
    }
}

