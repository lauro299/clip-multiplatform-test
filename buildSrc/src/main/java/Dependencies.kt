object Versions{
    const val kotlin = "1.4.10"
    const val kotlinCoroutines = "1.4.2-native-mt"
    const val ktor = "1.4.0"
    const val kotlinxSerialization = "1.0.0-RC"
    const val koin = "3.0.0-alpha-4"
    const val sqlDelight = "1.4.2"
    const val kermit = "0.1.8"

    const val sqliteJdbcDriver = "3.30.1"
    const val slf4j = "1.7.30"
    const val compose = "1.0.0-alpha07"
    const val nav_compose = "1.0.0-alpha02"
    const val accompanist = "0.3.3.1"

    const val junit = "4.13"
    const val testRunner = "1.3.0"
}

object AndroidSdk {
    const val min = 21
    const val compile = 29
    const val target = compile
}

object Serialization {
    val core = "org.jetbrains.kotlinx:kotlinx-serialization-core:${Versions.kotlinxSerialization}"
}

object SqlDelight {
    val runtime = "com.squareup.sqldelight:runtime:${Versions.sqlDelight}"
    val coroutineExtensions = "com.squareup.sqldelight:coroutines-extensions:${Versions.sqlDelight}"
    val androidDriver = "com.squareup.sqldelight:android-driver:${Versions.sqlDelight}"

    val nativeDriver = "com.squareup.sqldelight:native-driver:${Versions.sqlDelight}"
    val nativeDriverMacos = "com.squareup.sqldelight:native-driver-macosx64:${Versions.sqlDelight}"
    val jdbcDriver = "org.xerial:sqlite-jdbc:${Versions.sqliteJdbcDriver}"
    val sqlliteDriver = "com.squareup.sqldelight:sqlite-driver:${Versions.sqlDelight}"
}
