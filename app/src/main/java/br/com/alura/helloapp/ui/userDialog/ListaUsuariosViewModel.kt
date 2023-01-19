package br.com.alura.helloapp.ui.userDialog

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.alura.helloapp.database.UsuarioDao
import br.com.alura.helloapp.preferences.PreferencesKey
import br.com.alura.helloapp.util.USUARIO_ATUAL
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListaUsuariosViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val usuarioDao: UsuarioDao,
    private val dataStore: DataStore<Preferences>
) : ViewModel() {

    private val usuarioAtual = savedStateHandle.get<String>(USUARIO_ATUAL)

    private val _uiState = MutableStateFlow(ListaUsuariosUiState())
    val uiState: StateFlow<ListaUsuariosUiState>
        get() = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            carregaDados()
        }
    }

    private suspend fun carregaDados() {
        val usuario = usuarioAtual?.let { usuarioDao.buscaPorNomeDeUsuario(it).first() }

        usuario?.let {
            _uiState.value = _uiState.value.copy(
                nome = usuario.nome,
                nomeDeUsuario = usuario.nomeDeUsuario
            )
        }

        usuarioAtual?.let {
            usuarioDao.buscaTodos()
                .filterNotNull().collect { usuariosBuscados ->
                    usuariosBuscados.remove(usuario)
                    _uiState.value = _uiState.value.copy(
                        outrosUsuarios = usuariosBuscados
                    )
                }
        }
    }

    suspend fun alteraUsuarioAtual(novoUsuarioAtual: String) {
        dataStore.edit {
            it[PreferencesKey.USUARIO_ATUAL] = novoUsuarioAtual
        }
    }
}