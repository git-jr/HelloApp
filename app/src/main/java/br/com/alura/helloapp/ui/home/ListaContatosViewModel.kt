package br.com.alura.helloapp.ui.home

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.alura.helloapp.database.ContatoDao
import br.com.alura.helloapp.preferences.PreferencesKey
import br.com.alura.helloapp.preferences.PreferencesKey.USUARIO_ATUAL
import br.com.alura.helloapp.util.USUARIO_ATUAL
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
        buscaContatos()
    }

    private fun buscaContatos() {
        viewModelScope.launch {
            dataStore.data.collect {
                _uiState.value = _uiState.value.copy(
                    usuarioAtual = it[PreferencesKey.USUARIO_ATUAL].toString()
                )
            }

        }
        viewModelScope.launch {
            val contatos = contatoDao.buscaTodosPorUsuario(_uiState.value.usuarioAtual)
            contatos.collect { contatosBuscados ->
                _uiState.value = _uiState.value.copy(
                    contatos = contatosBuscados
                )
            }
        }
    }

    suspend fun desloga() {
        dataStore.edit { preferences ->
            preferences[booleanPreferencesKey("logado")] = false
        }
    }


}