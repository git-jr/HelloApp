package br.com.alura.helloapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Usuario(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val nomeDeUsuario: String = "",
    val nome: String = "",
    @ColumnInfo(defaultValue = "")
    val senha: String = "",
)