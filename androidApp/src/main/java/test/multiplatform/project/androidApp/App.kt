package test.multiplatform.project.androidApp

import android.app.Application
import mx.clip.db.appContext

class App: Application(){
    override fun onCreate() {
        super.onCreate()
        appContext = this
    }
}