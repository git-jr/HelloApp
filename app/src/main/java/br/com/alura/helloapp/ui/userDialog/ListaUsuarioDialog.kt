package br.com.alura.helloapp.ui.userDialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import br.com.alura.helloapp.data.Usuario
import br.com.alura.helloapp.sampleData.usuariosExemplo
import br.com.alura.helloapp.ui.components.AsyncImagePerfil
import br.com.alura.helloapp.ui.theme.HelloAppTheme


@Composable
fun CaixaDialogoContasUsuario(
    state: ListaUsuariosUiState, onClickDispensar: () -> Unit = {}
) {
    Dialog(
        onDismissRequest = onClickDispensar,
        content = {
            Column(
                Modifier
                    .clip(RoundedCornerShape(5))
                    .heightIn(250.dp, 400.dp)
                    .widthIn(200.dp)
                    .background(Color.White), horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Column(
                    Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Column(verticalArrangement = Arrangement.Center) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(IntrinsicSize.Min)
                        ) {
                            Row(
                                Modifier.fillMaxHeight(),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Icon(imageVector = Icons.Default.Close,
                                    contentDescription = "Fechar",
                                    tint = Color.Gray,
                                    modifier = Modifier.clickable { onClickDispensar() })
                            }
                            Row(
                                Modifier.fillMaxHeight(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "HelloApp",
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.h6,
                                    color = MaterialTheme.colors.primary,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }
                    Row(
                        Modifier.padding(vertical = 16.dp)
                    ) {
                        AsyncImagePerfil(
                            urlImagem = "contato.fotoPerfil",
                            modifier = Modifier
                                .size(46.dp)
                                .clip(CircleShape)
                        )
                        Column(
                            Modifier
                                .padding(start = 8.dp)
                                .align(Alignment.CenterVertically)
                        ) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = state.usuarioAtual.nome,
                                fontWeight = FontWeight.Bold,
                            )
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = state.usuarioAtual.usuario,
                                color = Color.Gray
                            )
                        }
                    }
                }

                Divider(thickness = 1.dp)

                LazyColumn(Modifier.padding(horizontal = 16.dp)) {
                    items(state.outrosUsuarios) { usuario ->
                        UsuarioItem(usuario) { idUsuario ->
                            // onClickPerfiUsuario(idUsuario)
                        }
                    }
                }
            }
        },
    )
}

@Composable
fun UsuarioItem(usuario: Usuario, onClickPerfiUsuario: (id: Long) -> Unit = {}) {
    Row(
        Modifier.padding(vertical = 12.dp)
    ) {
        AsyncImagePerfil(
            urlImagem = "contato.fotoPerfil", modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
        )
        Column(
            Modifier
                .padding(start = 8.dp)
                .align(Alignment.CenterVertically)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = usuario.nome,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = usuario.usuario,
                color = Color.Gray,
                fontSize = 12.sp
            )
        }
    }
}


@Preview
@Composable
fun UsuarioItemPreview() {
    UsuarioItem(usuario = usuariosExemplo.first())
}

@Preview
@Composable
fun CaixaDialogoContasUsuarioPreview() {

    HelloAppTheme {
        CaixaDialogoContasUsuario(
            ListaUsuariosUiState(
                usuarioAtual = Usuario(
                    nome = "Quem está logado agora", usuario = "@user_atual"
                )
            )
        )
    }

}
