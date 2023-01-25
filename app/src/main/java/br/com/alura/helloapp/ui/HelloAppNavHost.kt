package br.com.alura.helloapp.ui

import androidx.compose.runtime.Composable
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
    navController: NavHostController, modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DestinosHelloApp.SplashScreen.rota,
        modifier = modifier
    ) {
        splashGraph(
            onNavegaParaLogin = {
                navController.limpaBackStackENavegaParaLogin()
            },
            onNavegaParaHome = {
                navController.navegaParaHome()
            },
        )
        loginGraph(
            onNavegaParaHome = {
                navController.navegaParaHome()
            },
            onNavegaParaFormularioLogin = {
                navController.navegaParaFormlarioLogin()
            },
            onNavegaParaLogin = {
                navController.limpaBackStackENavegaParaLogin()
            },
        )
        homeGraph(
            onNavegaParaDetalhes = { idContato ->
                navController.navegaParaDetalhes(idContato)
            },
            onNavegaParaFormularioContato = {
                navController.navegaParaFormularioContato()
            },
            onNavegaParaDialgoUsuarios = { idUsuario ->
                navController.navegaParaDialgoUsuarios(idUsuario)
            },
            onNavegaParaBuscaContatos = {
                navController.navegaParaBuscaContatos()
            },
        )
        formularioContatoGraph(
            onVolta = {
                navController.popBackStack()
            },
        )
        detalhesContatoGraph(
            onClickVolta = {
                navController.popBackStack()
            },
            onNavegaParaDialgoUsuarios = { idContato ->
                navController.navegaParaFormularioContato(idContato)
            },
        )
        usuariosGraph(
            onClickVolta = {
                navController.popBackStack()
            },
            onNavegaParaLogin = {
                navController.navegaParaLogin()
            },
            onNavegaParaHome = {
                navController.navegaParaHome()
            },
            onNavegaGerenciaUsuarios = {
                navController.navegaParaGerenciaUsuarios()
            },
            onNavegaParaFormularioUsuario = { idUsuario ->
                navController.navegaParaFormularioUsuario(idUsuario)
            },
        )
        buscaContatosGraph(
            onClickVoltar = {
                navController.popBackStack()
            },
            onClickNavegaParaDetalhesContato = { idContato ->
                navController.navegaParaDetalhes(idContato)
            },
        )
    }

    val viewModel = hiltViewModel<SessaoViewModel>()
    val state = viewModel.uiState.collectAsState()

    if (!state.value.logado) {
        navController.limpaBackStackENavegaParaLogin()
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

fun NavHostController.limpaBackStackENavegaParaLogin() {
    navegaLimpo(DestinosHelloApp.LoginGraph.rota)
}

fun NavHostController.navegaParaDialgoUsuarios(idUsuario: String) {
    navigate("${ListaUsuarios.rota}/$idUsuario")
}

fun NavHostController.navegaParaFormularioUsuario(idUsuario: String) {
    navigate("${FormularioUsuario.rota}/$idUsuario")
}

fun NavHostController.navegaParaLogin() {
    navigate(DestinosHelloApp.Login.rota)
}

fun NavHostController.navegaParaHome() {
    navegaLimpo(DestinosHelloApp.HomeGraph.rota)
}

fun NavHostController.navegaParaFormlarioLogin() {
    navigate(DestinosHelloApp.FormularioLogin.rota)
}

fun NavHostController.navegaParaBuscaContatos() {
    navigate(DestinosHelloApp.BuscaContatos.rota)
}

fun NavHostController.navegaParaGerenciaUsuarios() {
    navigate(DestinosHelloApp.GerenciaUsuarios.rota)
}