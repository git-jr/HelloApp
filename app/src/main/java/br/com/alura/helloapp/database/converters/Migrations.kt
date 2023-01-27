package br.com.alura.helloapp.database.converters

import androidx.room.DeleteColumn
import androidx.room.RenameColumn
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("CREATE TABLE IF NOT EXISTS `Usuario` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `nome` TEXT NOT NULL, `sobrenome` TEXT NOT NULL, `usuario` TEXT NOT NULL)")
    }
}

val MIGRATION_11_12 = object : Migration(11, 12) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("CREATE TABLE IF NOT EXISTS `ContatoCopia` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `nome` TEXT NOT NULL, `sobrenome` TEXT NOT NULL, `telefone` TEXT NOT NULL, `fotoPerfil` TEXT NOT NULL, `aniversario` INTEGER, `nomeUsuario` TEXT NOT NULL DEFAULT '0', FOREIGN KEY(`nomeUsuario`) REFERENCES `Usuario`(`nomeDeUsuario`) ON UPDATE CASCADE ON DELETE CASCADE )")
        database.execSQL("INSERt INTO `ContatoCopia` SELECT * FROM `Contato`")
        database.execSQL("drop table Contato")
        database.execSQL("alter table ContatoCopia RENAME to Contato")
    }
}


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