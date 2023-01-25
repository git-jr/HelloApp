package br.com.alura.helloapp.ui

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.alura.helloapp.preferences.PreferencesKey.LOGADO
import br.com.alura.helloapp.preferences.PreferencesKey.USUARIO_ATUAL
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SessaoViewModel @Inject constructor(
    private val dataStore: DataStore<androidx.datastore.preferences.core.Preferences>
) : ViewModel() {

    private val _state = MutableStateFlow(SessaoState())
    val uiState = _state.asStateFlow()

    init {
        viewModelScope.launch {
            verificaSeUsuarioAtualFoiApagado()
        }
    }


    private suspend fun verificaSeUsuarioAtualFoiApagado() {
        dataStore.data.collect {
            val usuarioAtual = it[USUARIO_ATUAL]
            if (usuarioAtual == null) {
                desloga()
            }
        }
    }

    suspend fun desloga() {
        _state.value = _state.value.copy(
            logado = false
        )
        dataStore.edit { preferences ->
            preferences[LOGADO] = false
        }
    }
}

data class SessaoState(val logado: Boolean = true)