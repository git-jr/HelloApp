package br.com.alura.helloapp.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.alura.helloapp.data.Contato
import br.com.alura.helloapp.data.Usuario
import br.com.alura.helloapp.database.converters.*

@Database(
    entities = [Contato::class, Usuario::class],
    version = 13,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(
            from = 2, to = 3
        ),
        AutoMigration(
            from = 3, to = 4, spec = Migration3To4::class
        ),
        AutoMigration(
            from = 4, to = 5, spec = Migration4To5::class
        ),
        AutoMigration(
            from = 5, to = 6
        ),
        AutoMigration(
            from = 6, to = 7, spec = Migration6To7::class
        ),
        AutoMigration(
            from = 7, to = 8, spec = Migration7To8::class
        ),
        AutoMigration(
            from = 9, to = 10
        ),
        AutoMigration(
            from = 10, to = 11
        ),
        AutoMigration(
            from = 12, to = 13, spec = Migration12To13::class
        ),
    ]
)
@TypeConverters(Converters::class)
abstract class HelloAppDatabase : RoomDatabase() {
    abstract fun contatoDao(): ContatoDao
    abstract fun usuarioDao(): UsuarioDao
}