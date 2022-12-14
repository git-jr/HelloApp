package br.com.alura.helloapp.ui.home

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.alura.helloapp.preferences.PreferencesKey
import br.com.alura.helloapp.preferences.PreferencesKey.SOLICITAR_AVALIACAO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class AvaliacaoViewModel @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : ViewModel() {

    private val _uiState = MutableStateFlow(AvaliacaoUiState())
    val uiState: StateFlow<AvaliacaoUiState>
        get() = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            incrementaContadorSessao()
        }

        _uiState.update { state ->
            state.copy(onMostrarCaixaDialogoAvaliacaoMudou = {
                _uiState.value = _uiState.value.copy(
                    mostrarCaixaDialogoAvaliacao = it
                )
            }, onMostrarNovamenteMudou = {
                _uiState.value = _uiState.value.copy(
                    mostrarNovamente = it
                )
            })
        }
    }

    suspend fun incrementaContadorSessao() {
        // Se a opção "não mostrar novamente" já foi marcada antes,
        // saímos da função e nem fazemos o restante das verificações
        val solicitarAvaliacao = dataStore.data.first()[PreferencesKey.SOLICITAR_AVALIACAO]
        if (solicitarAvaliacao == false) {
            return
        }

        val numeroSessao = dataStore.data.first()[PreferencesKey.NUMERO_SESSAO]
        val numeroDaProximaSessaoParaMonstrarMensagem =
            dataStore.data.first()[PreferencesKey.NUMERO_PROXIMA_SESSAO]

        if (numeroSessao != null && numeroDaProximaSessaoParaMonstrarMensagem != null) {
            if (numeroSessao <= numeroDaProximaSessaoParaMonstrarMensagem) {
                dataStore.edit { preferencesEdit ->
                    preferencesEdit[PreferencesKey.NUMERO_SESSAO] = numeroSessao.plus(1)
                }
            }
        } else {
            dataStore.edit { preferencesEdit ->
                preferencesEdit[PreferencesKey.NUMERO_SESSAO] = 1
                preferencesEdit[PreferencesKey.NUMERO_PROXIMA_SESSAO] = 10
            }
        }
    }

    suspend fun atualizaOpcaoMostrarNovamente() {
        dataStore.edit { preferencesEdit ->
            preferencesEdit[SOLICITAR_AVALIACAO] = _uiState.value.mostrarNovamente
        }
    }

    suspend fun atualizarNumerosDaSessao() {
        val numeroDaProximaSessao = Random.nextInt(5, 10)
        dataStore.edit { preferences ->
            preferences[PreferencesKey.NUMERO_PROXIMA_SESSAO] = numeroDaProximaSessao
            preferences[PreferencesKey.NUMERO_SESSAO] = 1
        }
    }
}