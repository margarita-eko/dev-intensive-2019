package ru.skillbranch.devintensive

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import android.preference.PreferenceManager
import com.google.android.material.chip.ChipDrawable
import ru.skillbranch.devintensive.repositories.PreferencesRepository

class App: Application() {

    companion object {
        private var instance:App? = null

        fun appliactionContext(): Context{
            return instance!!.applicationContext
        }

    }

    init {
        instance = this
    }
    override fun onCreate() {
        super.onCreate()

        PreferencesRepository.getAppTheme().also {
            AppCompatDelegate.setDefaultNightMode(it)
        }

    }

}