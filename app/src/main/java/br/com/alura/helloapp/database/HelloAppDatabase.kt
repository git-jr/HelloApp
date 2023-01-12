package br.com.alura.helloapp.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RenameColumn
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.AutoMigrationSpec
import br.com.alura.helloapp.data.Contato
import br.com.alura.helloapp.data.Usuario
import br.com.alura.helloapp.database.converters.Converters

@Database(
    entities = [Contato::class, Usuario::class],
    version = 4,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 2, to = 3),
        AutoMigration(
            from = 3, to = 4,
            spec = HelloAppDatabase.HelloAppAutoMigration::class
        )]
)
@TypeConverters(Converters::class)
abstract class HelloAppDatabase : RoomDatabase() {
    abstract fun contatoDao(): ContatoDao
    abstract fun usuarioDao(): UsuarioDao

    @RenameColumn(tableName = "Usuario", fromColumnName = "usuario", toColumnName = "nomeDeUsuario")
    class HelloAppAutoMigration : AutoMigrationSpec
}