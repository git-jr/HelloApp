package br.com.alura.helloapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Usuario(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val nome: String = "",
    val sobrenome: String = "",
    val nomeDeUsuario: String = "",
)