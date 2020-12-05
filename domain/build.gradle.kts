import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("kotlin-android-extensions")
    id("com.squareup.sqldelight")
}
group = "test.multiplatform.project"
version = "1.0-SNAPSHOT"

repositories {
    gradlePluginPortal()
    google()
    jcenter()
    mavenCentral()
}
kotlin {
    android()
    iosX64("ios") {
        binaries {
            framework {
                baseName = "domain"
            }
        }
    }
    sourceSets {
        val commonMain by getting{
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutines}"){
                    isForce=true
                }
                implementation(Serialization.core)
                implementation(SqlDelight.runtime)
                implementation(SqlDelight.coroutineExtensions)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("androidx.core:core-ktx:1.2.0")
                implementation(SqlDelight.androidDriver)
            }
        }
        val androidTest by getting
        val iosMain by getting{
            dependencies{
                implementation(SqlDelight.nativeDriver)
            }
        }
        val iosTest by getting
    }
}
android {

    compileSdkVersion(AndroidSdk.compile)
    defaultConfig {
        minSdkVersion(24)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}
val packForXcode by tasks.creating(Sync::class) {
    group = "build"
    val mode = System.getenv("CONFIGURATION") ?: "DEBUG"
    val framework = kotlin.targets.getByName<KotlinNativeTarget>("ios").binaries.getFramework(mode)
    inputs.property("mode", mode)
    dependsOn(framework.linkTask)
    val targetDir = File(buildDir, "xcode-frameworks")
    from({ framework.outputDirectory })
    into(targetDir)
}
sqldelight{
    database("ClipDataBase"){
        packageName="mx.clip.db"
        sourceFolders = listOf("sqldelight")

    }
}
tasks.getByName("build").dependsOn(packForXcode)