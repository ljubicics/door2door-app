package com.example.door2door_app.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath

const val APP_PREFERENCES_NAME = "door2door_app_preferences.preferences_pb"

fun createDataStore(context: Context): DataStore<Preferences> = PreferenceDataStoreFactory.createWithPath(
    produceFile = { context.filesDir.resolve(APP_PREFERENCES_NAME).path.toPath() }
)

