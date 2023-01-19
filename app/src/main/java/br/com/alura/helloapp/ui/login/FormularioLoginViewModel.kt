package br.com.alura.helloapp.ui.login

import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import androidx.lifecycle.ViewModel
import br.com.alura.helloapp.data.Usuario
import br.com.alura.helloapp.database.UsuarioDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class FormularioLoginViewModel @Inject constructor(
    private val usuarioDao: UsuarioDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(FormularioLoginUiState())
    val uiState: StateFlow<FormularioLoginUiState>
        get() = _uiState.asStateFlow()

    init {
        _uiState.update { state ->
            state.copy(
                onNomeMudou = {
                    _uiState.value = _uiState.value.copy(
                        nome = it
                    )
                },
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
                voltarParaLoginMudou = {
                    _uiState.value = _uiState.value.copy(
                        voltarParaLogin = it
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

    suspend fun salvarLogin() {
        with(_uiState.value) {
            try {
                val insere = usuarioDao.insere(
                    Usuario(
                        nomeDeUsuario = usuario,
                        nome = nome,
                        senha = senha
                    )
                )
                _uiState.value.voltarParaLoginMudou(true)
            } catch (e: Exception) {
                _uiState.value.onErro(true)
            }
//            if (insere == -1L) { // Graças a @Insert(onConflict = IGNORE), -1 é "já existe"
//                Log.i("salvarLogin", "Usuário já existe")
//                _uiState.value.onErro(true)
//            } else {
//                _uiState.value.voltarParaLoginMudou(true)
//            }
        }
    }
}
