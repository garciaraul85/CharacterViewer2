package com.xfinity.util

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferenceHelper @Inject
constructor(private val preferences: SharedPreferences) {

    fun putString(key: String, value: String) {
        this.preferences.edit().putString(key, value).apply()
    }

    fun getString(key: String, defaultValue: String): String? {
        return this.preferences.getString(key, defaultValue)
    }

    fun putBoolean(key: String, value: Boolean) {
        this.preferences.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String): Boolean {
        return this.preferences.getBoolean(key, false)
    }

    fun getBooleanDefaultTrue(key: String): Boolean {
        return this.preferences.getBoolean(key, true)
    }

    fun putInt(key: String, value: Boolean) {
        this.preferences.edit().putBoolean(key, value).apply()
    }

    fun getInt(key: String): Int {
        return this.preferences.getInt(key, -1)
    }

    fun clear() {
        this.preferences.edit().clear().apply()
    }

    companion object {
        val PREF_FILE_NAME = "angelcall_pref_file"
    }
}