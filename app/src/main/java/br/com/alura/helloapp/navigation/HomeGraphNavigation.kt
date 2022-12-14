package br.com.alura.helloapp.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import br.com.alura.helloapp.DestinosHelloApp
import br.com.alura.helloapp.preferences.PreferencesKey.NUMERO_PROXIMA_SESSAO
import br.com.alura.helloapp.preferences.PreferencesKey.NUMERO_SESSAO
import br.com.alura.helloapp.preferences.dataStore
import br.com.alura.helloapp.ui.components.CaixaDialogoAvaliacao
import br.com.alura.helloapp.ui.home.AvaliacaoViewModel
import br.com.alura.helloapp.ui.home.ListaContatosTela
import br.com.alura.helloapp.ui.home.ListaContatosViewModel
import br.com.alura.helloapp.ui.navegaParaDetalhes
import br.com.alura.helloapp.ui.navegaParaFormularioContato
import br.com.alura.helloapp.ui.navegaParaLoginDeslogado
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

fun NavGraphBuilder.homeGraph(
    navController: NavHostController
) {
    navigation(
        startDestination = DestinosHelloApp.ListaContatos.rota,
        route = DestinosHelloApp.HomeGraph.rota,
    ) {
        composable(route = DestinosHelloApp.ListaContatos.rota) {
            val viewModel = hiltViewModel<ListaContatosViewModel>()
            val state by viewModel.uiState.collectAsState()
            val coroutineScope = rememberCoroutineScope()

            ListaContatosTela(
                state = state,
                onClickAbreDetalhes = { idContato ->
                    navController.navegaParaDetalhes(idContato)
                },
                onClickAbreCadastro = {
                    navController.navegaParaFormularioContato()
                },
                onClickDesloga = {
                    coroutineScope.launch {
                        viewModel.desloga()
                        navController.navegaParaLoginDeslogado()
                    }
                })


            val dataStore = LocalContext.current.dataStore
            val viewModelAvaliacao = hiltViewModel<AvaliacaoViewModel>()
            val stateAvaliacao by viewModelAvaliacao.uiState.collectAsState()

            LaunchedEffect(Unit) {
                val numeroSessaoAtual = dataStore.data.first()[NUMERO_SESSAO]
                val numeroDaProximaSessaoParaMonstrarMensagem =
                    dataStore.data.first()[NUMERO_PROXIMA_SESSAO]
                if (numeroSessaoAtual == numeroDaProximaSessaoParaMonstrarMensagem && stateAvaliacao.mostrarNovamente) {
                    stateAvaliacao.onMostrarCaixaDialogoAvaliacaoMudou(true)

                    // Assim que a caixa de diálogo é exibida, geramos o número
                    // da proxima sessão que ela poderá ser exibida novamente
                    viewModelAvaliacao.atualizarNumerosDaSessao()
                }
            }

            if (stateAvaliacao.mostrarCaixaDialogoAvaliacao) {
                CaixaDialogoAvaliacao(onClickAvaliar = {
                    coroutineScope.launch {
                        viewModelAvaliacao.atualizaOpcaoMostrarNovamente()
                    }
                    stateAvaliacao.onMostrarCaixaDialogoAvaliacaoMudou(false)
                }, onClickDispensar = {
                    coroutineScope.launch {
                        viewModelAvaliacao.atualizaOpcaoMostrarNovamente()
                    }
                    stateAvaliacao.onMostrarCaixaDialogoAvaliacaoMudou(false)
                },
                    onClickNaoMostrarMais = { mostrarNovamente ->
                        stateAvaliacao.onMostrarNovamenteMudou(mostrarNovamente)
                    },
                    monstrarNovamente = stateAvaliacao.mostrarNovamente
                )
            }
        }

    }
}

