package br.com.alura.helloapp.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import br.com.alura.helloapp.DestinosHelloApp
import br.com.alura.helloapp.ListaUsuarios
import br.com.alura.helloapp.ui.navegaLimpo
import br.com.alura.helloapp.ui.userDialog.CaixaDialogoContasUsuario
import br.com.alura.helloapp.ui.userDialog.ListaUsuariosViewModel
import br.com.alura.helloapp.util.USUARIO_ATUAL
import kotlinx.coroutines.launch

fun NavGraphBuilder.listaUsuarios(
    navController: NavController
) {
    dialog(
        route = ListaUsuarios.rotaComArgumentos,
        arguments = ListaUsuarios.argumentos
    ) { navBackStackEntry ->
        navBackStackEntry.arguments?.getLong(
            USUARIO_ATUAL
        )?.let { idUsuarioAtual ->

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
                        navController.navigate(DestinosHelloApp.HomeGraph.rota) {
                            popUpTo(0)
                        }
                    }
                }
            )
        }
    }
}
