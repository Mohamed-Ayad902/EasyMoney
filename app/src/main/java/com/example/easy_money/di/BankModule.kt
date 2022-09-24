package com.example.easy_money.di

import android.content.Context
import androidx.room.Room
import com.example.easy_money.db.BankDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BankModule {

    @Singleton
    @Provides
    fun provideBankDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, BankDatabase::class.java, "bank.db")
            .build()

    @Singleton
    @Provides
    fun provideBankDao(db: BankDatabase) = db.dao()

}