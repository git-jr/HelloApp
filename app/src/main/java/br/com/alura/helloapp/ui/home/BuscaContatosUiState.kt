package br.com.alura.helloapp.ui.home

import br.com.alura.helloapp.data.Contato

data class BuscaContatosUiState(
    val contatos: List<Contato> = emptyList(),
    val usuarioAtual: String = "",
    val valorBusca: String = "",
    val onValorBuscaMudou: (String) -> Unit = {}
)