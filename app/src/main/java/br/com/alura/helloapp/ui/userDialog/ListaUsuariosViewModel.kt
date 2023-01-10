package br.com.alura.helloapp.ui.userDialog

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.alura.helloapp.util.ID_USUARIO_ATUAL
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListaUsuariosViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    //private val usuarioDao: UsuarioDao,
) : ViewModel() {

    private val idUsuarioAtual = savedStateHandle.get<Long>(ID_USUARIO_ATUAL)

    private val _uiState = MutableStateFlow(ListaUsuariosUiState())
    val uiState: StateFlow<ListaUsuariosUiState>
        get() = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            carregaDados()
        }
    }

    private suspend fun carregaDados() {
//        val usuarioAtual = idUsuarioAtual?.let { usuarioDao.buscaPorId(it).first() }
//
//        usuarioAtual?.let {
//            usuarioDao.buscaTodos().collect { usuarios ->
//                usuarios?.let {
//                    _uiState.value = _uiState.value.copy(
//                        outrosUsuarios = usuarios.minusElement(usuarioAtual)
//                    )
//                }
//            }
//        }
    }
}