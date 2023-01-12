package br.com.alura.helloapp.database.converters

import androidx.room.AutoMigration
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("CREATE TABLE IF NOT EXISTS `Usuario` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `nome` TEXT NOT NULL, `sobrenome` TEXT NOT NULL, `usuario` TEXT NOT NULL)")
    }
}

//val MIGRATION_2_3 = AutoMigration(
//    from = 2, to = 3
//)