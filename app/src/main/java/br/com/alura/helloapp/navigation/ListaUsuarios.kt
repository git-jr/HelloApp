package br.com.alura.helloapp.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import br.com.alura.helloapp.ListaUsuarios
import br.com.alura.helloapp.ui.userDialog.CaixaDialogoContasUsuario
import br.com.alura.helloapp.ui.userDialog.ListaUsuariosViewModel
import br.com.alura.helloapp.util.ID_USUARIO_ATUAL

fun NavGraphBuilder.listaUsuarios(
    navController: NavController
) {
    dialog(
        route = ListaUsuarios.rotaComArgumentos,
        arguments = ListaUsuarios.argumentos
    ) { navBackStackEntry ->
        navBackStackEntry.arguments?.getLong(
            ID_USUARIO_ATUAL
        )?.let { idUsuarioAtual ->

            val viewModel = hiltViewModel<ListaUsuariosViewModel>()
            val state by viewModel.uiState.collectAsState()

            CaixaDialogoContasUsuario(
                state,
                onClickDispensar = {
                    navController.popBackStack()
                }
            )
        }
    }
}
