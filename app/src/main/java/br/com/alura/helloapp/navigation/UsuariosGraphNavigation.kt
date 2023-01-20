package br.com.alura.helloapp.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navigation
import br.com.alura.helloapp.DestinosHelloApp
import br.com.alura.helloapp.FormularioUsuario
import br.com.alura.helloapp.ListaUsuarios
import br.com.alura.helloapp.ui.userDialog.*
import br.com.alura.helloapp.util.USUARIO_ATUAL
import kotlinx.coroutines.launch

fun NavGraphBuilder.usuariosGraph(
    onClickVoltar: () -> Unit,
    onNavegaParaLogin: () -> Unit,
    onNavegaParaHome: () -> Unit,
    onNavegaGerenciaUsuarios: () -> Unit,
    onNavegaParaFormularioUsuario: (String) -> Unit,
) {
    navigation(
        startDestination = ListaUsuarios.rota,
        route = DestinosHelloApp.UsuariosGraph.rota
    ) {
        dialog(
            route = ListaUsuarios.rotaComArgumentos,
            arguments = ListaUsuarios.argumentos
        ) { navBackStackEntry ->
            navBackStackEntry.arguments?.getLong(
                USUARIO_ATUAL
            )?.let { usuarioAtual ->

                val viewModel = hiltViewModel<ListaUsuariosViewModel>()
                val state by viewModel.uiState.collectAsState()

                val coroutineScope = rememberCoroutineScope()

                CaixaDialogoContasUsuario(
                    state = state,
                    onClickDispensar = onClickVoltar,
                    onClickAdicionarNovaConta = {
                        onNavegaParaLogin()
                    },
                    onClickListarContatosPorUsuario = { novoUsuario ->
                        coroutineScope.launch {
                            viewModel.alteraUsuarioAtual(novoUsuario)
                            onNavegaParaHome()
                        }
                    },
                    onClickGerenciarUsuarios = {
                        onNavegaGerenciaUsuarios()
                    }
                )
            }
        }

        composable(
            route = DestinosHelloApp.GerenciaUsuarios.rota
        ) {
            val viewModel = hiltViewModel<GerenciaUsuariosViewModel>()
            val state by viewModel.uiState.collectAsState()

            GerenciaUsuariosTela(
                state = state,
                onClickAbreDetalhes = { usuarioAtual ->
                    onNavegaParaFormularioUsuario(usuarioAtual)
                },
                onClickVoltar = onClickVoltar
            )
        }

        composable(
            route = FormularioUsuario.rotaComArgumentos,
            arguments = FormularioUsuario.argumentos
        ) { usuarioAtual ->
            val viewModel = hiltViewModel<FormularioUsuarioViewModel>()
            val state by viewModel.uiState.collectAsState()
            val coroutineScope = rememberCoroutineScope()

            FormularioUsuarioTela(
                state = state,
                onClickVoltar = onClickVoltar,
                onClickSalvar = {
                    coroutineScope.launch {
                        viewModel.atualiza()
                        onClickVoltar()
                    }
                },
                onClickApagar = {
                    coroutineScope.launch {
                        viewModel.apagaUsuario()
                        onClickVoltar()
                    }
                }
            )
        }
    }
}
