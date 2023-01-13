package br.com.alura.helloapp

import androidx.navigation.NavType
import androidx.navigation.navArgument
import br.com.alura.helloapp.util.ID_CONTATO
import br.com.alura.helloapp.util.USUARIO_ATUAL

sealed class DestinosHelloApp(val rota: String) {
    object LoginGraph : DestinosHelloApp("grafico_login")
    object HomeGraph : DestinosHelloApp("grafico_home")
    object SplashScreen : DestinosHelloApp("splashScreen")
    object ListaContatos : DestinosHelloApp("lista_contatos")
    object FormularioLogin : DestinosHelloApp("formulario_login")
    object Login : DestinosHelloApp("login")
}

object FormularioContato {
    const val rota = "formulario_contato"
    const val rotaComArgumentos = "$rota/{$ID_CONTATO}"
    val argumentos = listOf(
        navArgument(ID_CONTATO) {
            defaultValue = 0L
            type = NavType.LongType
        }
    )
}

object DetalhesContato {
    const val rota = "detalhes_contato"
    const val rotaComArgumentos = "$rota/{$ID_CONTATO}"
    val argumentos = listOf(
        navArgument(ID_CONTATO) {
            defaultValue = 0L
            type = NavType.LongType
        }
    )
}

object ListaUsuarios {
    const val rota = "lista_usuarios"
    const val rotaComArgumentos = "$rota/{$USUARIO_ATUAL}"
    val argumentos = listOf(
        navArgument(USUARIO_ATUAL) {
            defaultValue = ""
            type = NavType.StringType
        }
    )
}

