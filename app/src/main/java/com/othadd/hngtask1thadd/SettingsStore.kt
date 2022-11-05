package com.othadd.hngtask1thadd

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import java.util.*
import com.othadd.hngtask1thadd.dataStore

class SettingsStore(private val context: Context) {

    private val themeIdKey = stringPreferencesKey("themeIdKey")

    fun updateThemeMode(update: String) {
        runBlocking {
            context.dataStore.edit {
                it[themeIdKey] = update
            }
        }
    }

    fun themeModeFlow(): Flow<String> {
        return context.dataStore.data.map {
            it[themeIdKey] ?: LIGHT_THEME
        }
    }

}