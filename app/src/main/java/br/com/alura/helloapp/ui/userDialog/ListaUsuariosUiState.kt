package br.com.alura.helloapp.ui.userDialog

import br.com.alura.helloapp.data.Usuario

data class ListaUsuariosUiState(
    val nomeDeUsuario: String = "",
    val nome: String = "",
    val outrosUsuarios: List<Usuario> = emptyList()
)