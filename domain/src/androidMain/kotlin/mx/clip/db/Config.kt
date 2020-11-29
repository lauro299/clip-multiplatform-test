package mx.clip.db

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver

lateinit var appContext:Context

actual fun createDB(): ClipDataBase?{
    val driver = AndroidSqliteDriver(ClipDataBase.Schema, appContext, "clipdatabase.db")
    return ClipDataBase(driver)
}

