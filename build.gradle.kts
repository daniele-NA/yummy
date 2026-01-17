@file:Suppress("AndroidGradlePluginVersion", "NewerVersionAvailable")

// == this is necessary to avoid the plugins to be loaded multiple times in each subproject's classloader == //
plugins {
    id("com.android.application") version "8.11.2" apply false
    id("com.android.library") version "8.11.2" apply false
    id("org.jetbrains.compose") version "1.9.0" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.2.20" apply false
    id("org.jetbrains.kotlin.multiplatform") version "2.2.20" apply false
}

gradle.rootProject {
    ext["min_version"]= 29
    ext["compile_version"] = 36
    ext["target_version"] = 36

    ext["package"] = "me.app.yummy"
}