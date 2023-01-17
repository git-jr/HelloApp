package br.com.alura.helloapp.ui.userDialog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.alura.helloapp.R
import br.com.alura.helloapp.ui.components.CaixaDialogoConfirmacao
import br.com.alura.helloapp.ui.theme.HelloAppTheme

@Composable
fun FormularioUsuarioTela(
    state: FormularioUsuarioUiState,
    modifier: Modifier = Modifier,
    onClickVoltar: () -> Unit = {},
    onClickSalvar: () -> Unit = {},
    onClickApagar: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            FormularioUsuarioAppBar(onClickVoltar = onClickVoltar)
        },
    ) { paddingValues ->

        Column(
            modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Column(
                Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val focuAtual = LocalFocusManager.current

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person, contentDescription = null
                        )
                    },
                    enabled = false,
                    value = state.nomeUsuario,
                    onValueChange = {},
                    label = { Text(stringResource(id = R.string.usuario)) },
                )

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Face, contentDescription = null
                        )
                    },
                    value = state.nome,
                    onValueChange = state.onNomeMudou,
                    label = { Text(stringResource(id = R.string.nome)) },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words, imeAction = ImeAction.Next
                    ),
                    keyboardActions = (KeyboardActions(onNext = { focuAtual.moveFocus(FocusDirection.Next) }))
                )

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Lock, contentDescription = null)
                    },
                    value = state.senha,
                    onValueChange = state.onSenhaMudou,
                    label = { Text(stringResource(id = R.string.senha)) },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words, imeAction = ImeAction.Next
                    ),
                    keyboardActions = (KeyboardActions(onNext = { focuAtual.moveFocus(FocusDirection.Next) }))
                )

                Spacer(Modifier.height(16.dp))

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(56.dp), onClick = onClickSalvar
                ) {
                    Text(text = stringResource(R.string.salvar))
                }

                Spacer(Modifier.height(8.dp))

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(56.dp),
                    onClick = {
                        state.mostraMensagemExclusaoMudou(true)
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Red
                    )
                ) {
                    Text(
                        text = stringResource(R.string.excluir_usuario),
                        color = Color.White
                    )
                }
            }
        }
    }

    if (state.mostraMensagemExclusao) {
        CaixaDialogoConfirmacao(
            titulo = stringResource(R.string.tem_certeza),
            mensagem = stringResource(R.string.aviso_apagar_usuario),
            onClikConfirma = onClickApagar,
            onClickCancela = { state.mostraMensagemExclusaoMudou(false) },
        )
    }
}

@Composable
fun FormularioUsuarioAppBar(onClickVoltar: () -> Unit = {}) {
    TopAppBar(title = { Text(text = stringResource(R.string.editar_usuario)) }, navigationIcon = {
        IconButton(
            onClick = onClickVoltar
        ) {
            Icon(
                Icons.Default.ArrowBack,
                tint = Color.White,
                contentDescription = stringResource(R.string.voltar)
            )
        }
    })
}

@Preview
@Composable
fun FormularioUsuarioTelaPreview() {
    HelloAppTheme {
        FormularioUsuarioTela(state = FormularioUsuarioUiState())
    }
}