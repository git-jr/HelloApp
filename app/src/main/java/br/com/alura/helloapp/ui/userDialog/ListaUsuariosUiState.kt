package br.com.alura.helloapp.ui.userDialog

import br.com.alura.helloapp.data.Usuario

data class ListaUsuariosUiState(
    val usuarioAtual: Usuario = Usuario(),
    val outrosUsuarios: List<Usuario> = emptyList()
)