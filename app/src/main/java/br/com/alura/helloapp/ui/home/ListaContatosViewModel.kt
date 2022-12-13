package br.com.alura.helloapp.ui.home

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.alura.helloapp.database.ContatoDao
import br.com.alura.helloapp.preferences.PreferencesKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListaContatosViewModel @Inject constructor(
    private val contatoDao: ContatoDao,
    private val dataStore: DataStore<Preferences>
) : ViewModel() {

    private val _uiState = MutableStateFlow(ListaContatosUiState())
    val uiState: StateFlow<ListaContatosUiState>
        get() = _uiState.asStateFlow()


    init {
        viewModelScope.launch {
            val contatos = contatoDao.buscaTodos()
            contatos.collect { contatosBuscados ->
                _uiState.value = _uiState.value.copy(
                    contatos = contatosBuscados
                )
            }
        }

        viewModelScope.launch {
            incrementaContadorSessao()
        }

        _uiState.update { state ->
            state.copy(onMostrarCaixaDialogoAvalicao = {
                _uiState.value = _uiState.value.copy(
                    mostrarCaixaDialogoAvalicao = it
                )
            })
        }
    }

    suspend fun desloga() {
        dataStore.edit { preferences ->
            preferences[booleanPreferencesKey("logado")] = false
        }
    }

    suspend fun incrementaContadorSessao() {
        val numeroSessao = dataStore.data.first()[PreferencesKey.NUMERO_SESSAO]
        if (numeroSessao != null) {
            if (numeroSessao <= 10) {
                dataStore.edit { preferencesEdit ->
                    preferencesEdit[PreferencesKey.NUMERO_SESSAO] = numeroSessao.plus(1)
                }
            }
            Log.i("Contador de sessões", "Sessão atual: ${numeroSessao.plus(1)} ")
        } else {
            dataStore.edit { preferencesEdit ->
                preferencesEdit[PreferencesKey.NUMERO_SESSAO] = 1
                Log.i("Contador de sessões", "É o primeiro uso do app")
            }
        }
    }

}