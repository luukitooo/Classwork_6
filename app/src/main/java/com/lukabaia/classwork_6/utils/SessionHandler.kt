package com.lukabaia.classwork_6.utils

import android.app.Application
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

class SessionHandler(private val application: Application) {

    companion object{
        private val Application.store by preferencesDataStore(
            name = "session_store",
        )
    }

    suspend fun save(key: String, value: String) {
        val prefKey = stringPreferencesKey(key)
        application.store.edit {
            it[prefKey] = value
        }
    }

    suspend fun remove() {
        application.store
    }

    suspend fun read(key: String): String? {
        return application.store.data.first()[stringPreferencesKey(key)]
    }
}