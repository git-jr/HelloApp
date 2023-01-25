package br.com.alura.helloapp.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.DeleteColumn
import androidx.room.RenameColumn
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.AutoMigrationSpec
import br.com.alura.helloapp.data.Contato
import br.com.alura.helloapp.data.Usuario
import br.com.alura.helloapp.database.converters.Converters

@Database(
    entities = [Contato::class, Usuario::class],
    version = 13,
    exportSchema = true,
    autoMigrations = [
//        AutoMigration(from = 2, to = 3),
//        AutoMigration(
//            from = 3, to = 4,
//            spec = HelloAppDatabase.Migration3To4::class
//        ),
//        AutoMigration(
//            from = 4, to = 5,
//            spec = HelloAppDatabase.Migration4To5::class
//        ),
        AutoMigration(from = 5, to = 6),
        AutoMigration(from = 6, to = 7, spec = HelloAppDatabase.Migration6To7::class),
        AutoMigration(from = 7, to = 8, spec = HelloAppDatabase.Migration7To8::class),
        AutoMigration(from = 9, to = 10),
        AutoMigration(from = 10, to = 11),
        AutoMigration(from = 12, to = 13,spec = HelloAppDatabase.Migration12To13::class),


    ]
)
@TypeConverters(Converters::class)
abstract class HelloAppDatabase : RoomDatabase() {
    abstract fun contatoDao(): ContatoDao
    abstract fun usuarioDao(): UsuarioDao

    @RenameColumn(tableName = "Usuario", fromColumnName = "usuario", toColumnName = "nomeDeUsuario")
    class Migration3To4 : AutoMigrationSpec

    @DeleteColumn(tableName = "Usuario", columnName = "id")
    class Migration4To5 : AutoMigrationSpec

    @DeleteColumn(tableName = "Usuario", columnName = "sobrenome")
    class Migration6To7 : AutoMigrationSpec

    @RenameColumn(tableName = "Contato", fromColumnName = "idUsuario", toColumnName = "nomeUsuario")
    class Migration7To8 : AutoMigrationSpec

    @RenameColumn(tableName = "Contato", fromColumnName = "nomeUsuario", toColumnName = "id_usuario")
    @RenameColumn(tableName = "Usuario", fromColumnName = "nomeDeUsuario", toColumnName = "id")
    class Migration12To13 : AutoMigrationSpec
}