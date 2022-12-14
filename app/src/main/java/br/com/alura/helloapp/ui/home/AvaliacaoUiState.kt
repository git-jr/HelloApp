package br.com.alura.helloapp.ui.home

data class AvaliacaoUiState(
    val mostrarCaixaDialogoAvaliacao: Boolean = false,
    val onMostrarCaixaDialogoAvaliacaoMudou: (mostrar: Boolean) -> Unit = {},
    val mostrarNovamente: Boolean = true,
    val onMostrarNovamenteMudou: (mostrar: Boolean) -> Unit = {}
)