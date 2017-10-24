package br.nardi.party.util

import android.content.Context

class SecurityPreferences (context: Context){

    val sharedPreferences = context.getSharedPreferences("EndOfYear", Context.MODE_PRIVATE)

    fun storeString(key: String, value: String) {
        this.sharedPreferences.edit().putString(key, value).apply()
    }

    fun getString(key: String): String {
        return this.sharedPreferences.getString(key, "")
    }

}