package net.soft.petFinder.android

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import net.soft.android.helpers.logging.LoggerFactory
import net.soft.android.helpers.logging.writers.LogcatWriter

@HiltAndroidApp
class PetFinderApp : Application(){
    override fun onCreate() {
        super.onCreate()
        LoggerFactory.setLogWriter(LogcatWriter("PetFinder-v1", true))
    }
}