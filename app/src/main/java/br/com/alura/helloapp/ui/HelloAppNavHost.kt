package br.com.alura.helloapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import br.com.alura.helloapp.*
import br.com.alura.helloapp.navigation.*

@Composable
fun HelloAppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DestinosHelloApp.SplashScreen.rota,
        modifier = modifier
    ) {
        splashGraph(navController)
        loginGraph(navController)
        homeGraph(navController)
        formularioContatoGraph(navController)
        detalhesContatoGraph(navController)
        usuariosGraph(navController)
        buscaContatosGraph(navController)
    }

    val viewModel = hiltViewModel<SessaoViewModel>()
    val state = viewModel.uiState.collectAsState()

    if (!state.value.logado) {
        navController.navegaParaLoginDeslogado()
    }

}


fun NavHostController.navegaDireto(rota: String) = this.navigate(rota) {
    popUpTo(this@navegaDireto.graph.findStartDestination().id) {
        saveState = true
    }
    launchSingleTop = true
    restoreState = true
}

fun NavHostController.navegaLimpo(rota: String) = this.navigate(rota) {
    popUpTo(0)
}

fun NavHostController.navegaParaDetalhes(idContato: Long) {
    navegaDireto("${DetalhesContato.rota}/$idContato")
}

fun NavHostController.navegaParaFormularioContato(idContato: Long = 0L) {
    navigate("${FormularioContato.rota}/$idContato")
}

fun NavHostController.navegaParaLoginDeslogado() {
    popBackStack(DestinosHelloApp.ListaContatos.rota, true)
    navegaDireto(DestinosHelloApp.LoginGraph.rota)
}

fun NavHostController.navegaParaDialgoUsuarios(idUsuarioAtual: String) {
    navigate("${ListaUsuarios.rota}/$idUsuarioAtual")
}

fun NavHostController.navegaParaFormularioUsuario(idUsuarioAtual: String) {
    navigate("${FormularioUsuario.rota}/$idUsuarioAtual")
}