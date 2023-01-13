package br.com.alura.helloapp.ui.login

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import br.com.alura.helloapp.database.UsuarioDao
import br.com.alura.helloapp.preferences.PreferencesKey
import br.com.alura.helloapp.preferences.PreferencesKey.LOGADO
import br.com.alura.helloapp.preferences.PreferencesKey.USUARIO_ATUAL
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val usuarioDao: UsuarioDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState>
        get() = _uiState.asStateFlow()

    init {
        _uiState.update { state ->
            state.copy(
                onUsuarioMudou = {
                    _uiState.value = _uiState.value.copy(
                        usuario = it
                    )
                },
                onSenhaMudou = {
                    _uiState.value = _uiState.value.copy(
                        senha = it
                    )
                },
                onErro = {
                    _uiState.value = _uiState.value.copy(
                        exibirErro = it
                    )
                },
            )
        }
    }

    suspend fun tentaLogar() {

        val usuarioBuscado = usuarioDao
            .buscaPorNomeDeUsuario(_uiState.value.usuario)
            .first()

        if (usuarioBuscado != null) {
            val senha = usuarioBuscado.senha
            if (senha == _uiState.value.senha) {
                dataStore.edit {
                    it[LOGADO] = true
                    it[USUARIO_ATUAL] = _uiState.value.usuario
                }
                logaUsuario()
            } else {
                _uiState.value.onErro(true)
            }
        } else {
            _uiState.value.onErro(true)
        }
    }

    private fun logaUsuario() {
        _uiState.value = _uiState.value.copy(
            logado = true
        )
    }
}


