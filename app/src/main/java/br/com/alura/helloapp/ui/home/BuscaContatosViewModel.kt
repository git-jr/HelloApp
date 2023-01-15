package br.com.alura.helloapp.ui.home

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.alura.helloapp.database.ContatoDao
import br.com.alura.helloapp.preferences.PreferencesKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BuscaContatosViewModel @Inject constructor(
    private val contatoDao: ContatoDao,
    private val dataStore: DataStore<Preferences>
) : ViewModel() {

    private val _uiState = MutableStateFlow(BuscaContatosUiState())
    val uiState: StateFlow<BuscaContatosUiState>
        get() = _uiState.asStateFlow()

    init {
        _uiState.update { state ->
            state.copy(
                onValorBuscaMudou = {
                    _uiState.value = _uiState.value.copy(
                        valorBusca = it
                    )
                    buscaContatosPorValor()
                }
            )
        }

        viewModelScope.launch {
            dataStore.data.collect {
                _uiState.value = _uiState.value.copy(
                    usuarioAtual = it[PreferencesKey.USUARIO_ATUAL].toString()
                )
            }
        }

    }

    fun buscaContatosPorValor() {

        viewModelScope.launch {
            val contatos =
                contatoDao.buscaPorUsuarioEValor(
                    _uiState.value.usuarioAtual,
                    _uiState.value.valorBusca
                )
            contatos.collect { contatosBuscados ->
                contatosBuscados?.let {
                    _uiState.value = _uiState.value.copy(
                        contatos = contatosBuscados
                    )
                }
            }
        }
    }

}