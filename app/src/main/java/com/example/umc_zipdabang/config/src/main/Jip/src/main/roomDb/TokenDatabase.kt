package com.example.umc_zipdabang.config.src.main.Jip.src.main.roomDb

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import java.time.chrono.HijrahChronology.INSTANCE

//@Database(entities = [Token::class], version=1)
//abstract class TokenDatabase: RoomDatabase() {
//    abstract fun tokenDao(): TokenDao
//
//    companion object {
//        @Volatile
//        private var INSTANCE: TokenDatabase? = null
//
//        fun getTokenDatabase(context: Context): TokenDatabase {
//            val tempInstance = INSTANCE
//            if (tempInstance != null) {
//                return tempInstance
//            }
//            synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext, TokenDatabase::class.java, "tokenDb"
//                ).build()
//                INSTANCE = instance
//                return instance
//            }
//        }
//    }
//}

@Database(entities = [Token::class, RecipeDbClass::class, CommentDbClass::class], version=2, exportSchema = true)
abstract class TokenDatabase: RoomDatabase() {
    abstract fun tokenDao(): TokenDao
    abstract fun recipeDao(): RecipeDao
    abstract fun commentDao(): CommentDao

    companion object {
        @Volatile
        private var INSTANCE: TokenDatabase? = null

//        var MIGRATION_1_2: Migration = object: Migration(1, 2) {
//            override fun migrate(database: SupportSQLiteDatabase) {
//                database.execSQL("CREATE TABLE IF NOT EXISTS `RecipeDbClass` (`recipeId` INT NOT NULL, PRIMARY KEY(`recipeId`))")
//            }
//        }

        fun getTokenDatabase(context: Context): TokenDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, TokenDatabase::class.java, "tokenDb2"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}