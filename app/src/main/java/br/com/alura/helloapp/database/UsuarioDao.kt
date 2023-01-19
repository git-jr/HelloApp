package br.com.alura.helloapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.*
import androidx.room.Query
import androidx.room.Update
import br.com.alura.helloapp.data.Usuario
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDao {

    @Insert
    suspend fun insere(usuario: Usuario): Long

    @Query("SELECT * FROM Usuario")
    fun buscaTodos(): Flow<MutableList<Usuario>?>

    @Query("SELECT * FROM Usuario WHERE id = :nomeDeUsuario")
    fun buscaPorNomeDeUsuario(nomeDeUsuario: String): Flow<Usuario?>

    @Update
    suspend fun atualizar(usuario: Usuario)

    @Delete
    suspend fun apaga(usuario: Usuario)
}