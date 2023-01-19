package com.example.umc_zipdabang.src.main.roomDb

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import java.time.chrono.HijrahChronology.INSTANCE

@Database(entities = [Token::class], version=1)
abstract class TokenDatabase: RoomDatabase() {
    abstract fun tokenDao(): TokenDao
}