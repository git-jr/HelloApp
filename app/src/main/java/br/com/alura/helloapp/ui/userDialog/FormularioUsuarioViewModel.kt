package br.com.alura.helloapp.ui.userDialog

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.alura.helloapp.data.Usuario
import br.com.alura.helloapp.database.UsuarioDao
import br.com.alura.helloapp.preferences.PreferencesKey
import br.com.alura.helloapp.util.ID_USUARIO_ATUAL
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FormularioUsuarioViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val usuarioDao: UsuarioDao,
    private val dataStore: DataStore<Preferences>
) : ViewModel() {

    private val nomeUsuario = savedStateHandle.get<String>(ID_USUARIO_ATUAL)

    private val _uiState = MutableStateFlow(FormularioUsuarioUiState())
    val uiState: StateFlow<FormularioUsuarioUiState>
        get() = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            carregaUsuario()
        }

        _uiState.update { state ->
            state.copy(onNomeMudou = {
                _uiState.value = _uiState.value.copy(
                    nome = it
                )
            })
        }
    }


    private suspend fun carregaUsuario() {
        nomeUsuario?.let {
            val usuario = usuarioDao.buscaPorNomeDeUsuario(nomeUsuario)
            usuario.collect { usuarioBuscado ->
                usuarioBuscado?.let {
                    _uiState.value = _uiState.value.copy(
                        nomeUsuario = it.nomeDeUsuario, nome = it.nome, senha = it.senha.toString()
                    )
                }
            }
        }
    }

    suspend fun atualiza() {
        with(_uiState.value) {
            usuarioDao.atualiza(
                Usuario(
                    nomeDeUsuario = nomeUsuario, nome = nome, senha = null
                )
            )
        }
    }

    suspend fun apagaUsuario() {
        with(_uiState.value) {
            usuarioDao.apaga(
                Usuario(nomeDeUsuario = nomeUsuario)
            )
            mostraMensagemExclusaoMudou(false)
        }
        verificaRotaDeVolta()
    }

    private suspend fun verificaRotaDeVolta() {
        val usarioLogado = dataStore.data.first()[PreferencesKey.USUARIO_ATUAL]
        val usuarioQueFoiApagado = _uiState.value.nomeUsuario
        if (usarioLogado == usuarioQueFoiApagado) {
            dataStore.edit {
                it.remove(PreferencesKey.USUARIO_ATUAL)
            }
        }
    }

    fun onClickMostraMensagemExclusao() {
        _uiState.value = _uiState.value.copy(
            mostraMensagemExclusao = true
        )
    }
}