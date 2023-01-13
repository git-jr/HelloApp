package br.com.alura.helloapp.ui.login

data class FormularioLoginUiState(
    val nome: String = "",
    val usuario: String = "",
    val senha: String = "",
    val voltarParaLogin: Boolean = false,
    val exibirErro: Boolean = false,
    val onErro: (Boolean) -> Unit = {},
    val voltarParaLoginMudou: (Boolean) -> Unit = {},
    val onNomeMudou: (String) -> Unit = {},
    val onUsuarioMudou: (String) -> Unit = {},
    val onSenhaMudou: (String) -> Unit = {},
    val onClickSalvar: () -> Unit = {}
) {
}