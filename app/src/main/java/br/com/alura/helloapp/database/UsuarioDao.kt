package br.com.alura.helloapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import br.com.alura.helloapp.data.Usuario
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDao {

    @Insert(onConflict = REPLACE)
    fun insere(usuario: Usuario)

    @Query("SELECT * FROM Usuario")
    fun buscaTodos(): Flow<List<Usuario>?>

    @Query("SELECT * FROM Usuario WHERE id = :id")
    fun buscaPorId(id: Long): Flow<Usuario?>
}