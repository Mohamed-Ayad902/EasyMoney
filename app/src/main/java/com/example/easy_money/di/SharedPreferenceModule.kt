package com.example.easy_money.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.example.easy_money.others.Constants.SHARED_PREF_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object SharedPreferenceModule {

    @ViewModelScoped
    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context) =
        context.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE)!!

}