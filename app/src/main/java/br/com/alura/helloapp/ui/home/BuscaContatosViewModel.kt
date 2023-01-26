package br.com.alura.helloapp.ui.home

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.alura.helloapp.database.ContatoDao
import br.com.alura.helloapp.preferences.PreferencesKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BuscaContatosViewModel @Inject constructor(
    private val contatoDao: ContatoDao,
    private val dataStore: DataStore<Preferences>
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        BuscaContatosUiState()
    )
    val uiState: StateFlow<BuscaContatosUiState>
        get() = _uiState.asStateFlow()

    init {
        _uiState.update { state ->
            state.copy(onValorBuscaMudou = {
                _uiState.value = _uiState.value.copy(
                    valorBusca = it
                )
                buscaContatosPorValor()
            })
        }

        viewModelScope.launch {
            val usuarioAtual = dataStore.data.first()[PreferencesKey.USUARIO_ATUAL].toString()

            contatoDao.buscaTodosPorUsuario(usuarioAtual).collect { listaDeContatos ->
                _uiState.value = _uiState.value.copy(
                    usuarioAtual = usuarioAtual,
                    contatos = listaDeContatos
                )
            }
        }
    }

    fun buscaContatosPorValor() {
        with(_uiState) {
            viewModelScope.launch {
                val contatos = contatoDao.buscaPorUsuarioEValor(
                    value.usuarioAtual.toString(),
                    value.valorBusca
                )
                contatos.collect { contatosBuscados ->
                    contatosBuscados?.let {
                        value = value.copy(
                            contatos = contatosBuscados
                        )
                    }
                }
            }
        }
    }
}