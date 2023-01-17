package br.com.alura.helloapp.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.datastore.dataStore
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import br.com.alura.helloapp.DestinosHelloApp
import br.com.alura.helloapp.FormularioUsuario
import br.com.alura.helloapp.ListaUsuarios
import br.com.alura.helloapp.ui.navegaLimpo
import br.com.alura.helloapp.ui.navegaParaFormularioUsuario
import br.com.alura.helloapp.ui.navegaParaLoginDeslogado
import br.com.alura.helloapp.ui.userDialog.*
import br.com.alura.helloapp.util.USUARIO_ATUAL
import kotlinx.coroutines.launch

fun NavGraphBuilder.listaUsuarios(
    navController: NavHostController
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
                state,
                onClickDispensar = {
                    navController.popBackStack()
                },
                onClickAdicionarNovaConta = {
                    navController.navigate(DestinosHelloApp.LoginGraph.rota)
                },
                onClickListarContatosPorUsuario = { novoUsuario ->
                    coroutineScope.launch {
                        viewModel.alteraUsuarioAtual(novoUsuario)
                        navController.navegaLimpo(DestinosHelloApp.HomeGraph.rota)
                    }
                },
                onClickGerenciarUsuarios = {
                    navController.navigate(DestinosHelloApp.GerenciaUsuarios.rota)
                }
            )
        }
    }

    composable(
        route = DestinosHelloApp.GerenciaUsuarios.rota
    ) {
        val viewModel = hiltViewModel<GerenciaUsuariosViewModel>()
        val state by viewModel.uiState.collectAsState()

        GerenciaUsuariosTela(state = state,
            onClickAbreDetalhes = { usuarioAtual ->
                navController.navegaParaFormularioUsuario(usuarioAtual)
            },
            onClickVoltar = {
                navController.popBackStack()
            })
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
            onClickVoltar = {
                navController.popBackStack()
            },
            onClickSalvar = {
                coroutineScope.launch {
                    viewModel.atualizar()
                    navController.popBackStack()
                }
            },
            onClickApagar = {
                coroutineScope.launch {
                    viewModel.apaga()
                    if (state.contaUsuarioLogadoFoiApagada) {
                        navController.navegaParaLoginDeslogado()
                    } else {
                        navController.popBackStack()
                    }
                }
            }
        )

    }

}
