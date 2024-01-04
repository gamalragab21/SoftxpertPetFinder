package net.xpert.petfinder.android

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import net.xpert.android.helpers.logging.LoggerFactory
import net.xpert.android.helpers.logging.writers.LogcatWriter

@HiltAndroidApp
class PetFinderApp : Application(){
    override fun onCreate() {
        super.onCreate()
        LoggerFactory.setLogWriter(LogcatWriter("Softxpert-v1", true))
    }
}