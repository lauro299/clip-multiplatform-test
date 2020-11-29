package mx.clip.db

actual fun createDb():ClipDataBase?{
    val driver = NativeSqliteDriver()
}
