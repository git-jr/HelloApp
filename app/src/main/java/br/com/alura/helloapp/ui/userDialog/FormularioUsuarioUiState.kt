package br.com.alura.helloapp.ui.userDialog

data class FormularioUsuarioUiState(
    val nomeUsuario: String = "",
    val nome: String = "",
    val senha: String = "",
    val mostraMensagemExclusao: Boolean = false,
    val contaUsuarioLogadoFoiApagada: Boolean = false,
    val onNomeMudou: (String) -> Unit = {},
    val onSenhaMudou: (String) -> Unit = {},
    val mostraMensagemExclusaoMudou: (Boolean) -> Unit = {},
) {
}