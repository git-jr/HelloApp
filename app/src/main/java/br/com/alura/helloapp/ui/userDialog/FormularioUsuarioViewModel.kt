package br.com.alura.helloapp.ui.userDialog

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.alura.helloapp.data.Usuario
import br.com.alura.helloapp.database.UsuarioDao
import br.com.alura.helloapp.util.USUARIO_ATUAL
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FormularioUsuarioViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val usuarioDao: UsuarioDao,
) : ViewModel() {

    private val nomeUsuario = savedStateHandle.get<String>(USUARIO_ATUAL)

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
            }, onSenhaMudou = {
                _uiState.value = _uiState.value.copy(
                    senha = it
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
                        nomeUsuario = it.nomeDeUsuario,
                        nome = it.nome,
                        senha = it.senha
                    )
                }
            }
        }
    }

    suspend fun atualizar() {
        with(_uiState.value) {
            usuarioDao.atualizar(
                Usuario(
                    nomeDeUsuario = nomeUsuario,
                    nome = nome,
                    senha = senha,
                )
            )
        }
    }

}