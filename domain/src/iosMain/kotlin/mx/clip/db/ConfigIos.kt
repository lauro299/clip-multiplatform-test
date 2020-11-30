package mx.clip.db

import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

actual fun createDB(): ClipDataBase?{
    val driver = NativeSqliteDriver(ClipDataBase.Schema, "clipdatabase.db")
    return ClipDataBase(driver = driver)
}
