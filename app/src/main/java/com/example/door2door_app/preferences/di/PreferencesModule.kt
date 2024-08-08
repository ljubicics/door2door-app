package com.example.door2door_app.preferences.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.door2door_app.preferences.createDataStore
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val preferencesModule = module {
    single<DataStore<Preferences>> {
        createDataStore(androidContext())
    }
}