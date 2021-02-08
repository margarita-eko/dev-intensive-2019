package ru.skillbranch.devintensive.repositories

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import android.preference.PreferenceManager
import ru.skillbranch.devintensive.App
import ru.skillbranch.devintensive.models.Profile
import java.util.prefs.Preferences

object PreferencesRepository {

    private const val RESPECT = "RESPECT"
    private const val RATING = "RATING"
    private const val FIRST_NAME = "FIRST_NAME"
    private const val LAST_NAME = "LAST_NAME"
    private const val REPOSITORY = "REPOSITORY"
    private const val ABOUT= "ABOUT"
    private const val APP_THEME= "APP_THEME"

    private val prefs: SharedPreferences by lazy {
        val ctx = App.appliactionContext()
        PreferenceManager.getDefaultSharedPreferences(ctx)
    }

    fun saveAppTheme(theme:Int) {
        putValue(APP_THEME to theme)
    }

    fun getAppTheme() = prefs.getInt(APP_THEME,AppCompatDelegate.MODE_NIGHT_NO)

    fun getProfile() = Profile(
        prefs.getString(FIRST_NAME,"")?: "",
        prefs.getString(LAST_NAME,"")?: "",
        prefs.getString(ABOUT,"")?: "",
        prefs.getString(REPOSITORY,"") ?: "",
        prefs.getInt(RATING, 0),
        prefs.getInt(RESPECT,0)
    )


    fun saveProfile(profile: Profile){

        with(profile){
            putValue(RESPECT to respect)
            putValue(RATING to rating)
            putValue(FIRST_NAME to firstname)
            putValue(LAST_NAME to lastname)
            putValue(REPOSITORY to repository)
            putValue(ABOUT to about)
        }

    }

    private fun putValue(pair: Pair<String,Any>) = with(prefs.edit()) {
        val key = pair.first
        val value = pair.second
        when (value){
            is String -> putString(key,value)
            is Int -> putInt(key,value)
            is Boolean -> putBoolean(key,value)
            is Float -> putFloat(key,value)
            is Long -> putLong(key,value)
            else -> error("Only primitive types can be stored in Shared Preferences")
        }
        apply()
    }

}