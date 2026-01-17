@file:Suppress("AndroidGradlePluginVersion", "NewerVersionAvailable", "GradleDependency")

import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("org.jetbrains.kotlin.multiplatform") version "2.2.20"
    id("org.jetbrains.kotlin.plugin.compose") version "2.2.20"
    kotlin("plugin.serialization") version "2.2.20"
    id("com.android.application") version "8.11.2"
    id("org.jetbrains.compose") version "1.9.0"

    id("app.cash.sqldelight") version "2.0.1"
}

kotlin {
    sqldelight {
        databases {
            // == IT GENERATES A KOTLIN-CLASS WITH THIS NAME == //
            create("YummyDb") {
                packageName = rootProject.extra["package"] as String
            }
        }
    }

    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
    listOf(
        iosArm64(),
        iosX64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "YummyIos"
            isStatic = true
            freeCompilerArgs += listOf("-Xbinary=bundleId=${rootProject.extra["package"] as String}")
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation("androidx.activity:activity-compose:1.11.0")
            implementation("androidx.core:core-splashscreen:1.0.1")
            implementation("com.google.android.material:material:1.12.0")

            implementation("io.insert-koin:koin-android:3.6.0-alpha3")
            implementation("io.insert-koin:koin-androidx-compose:3.6.0-alpha3")

            implementation("app.cash.sqldelight:android-driver:2.0.1")
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")
            implementation("org.jetbrains.androidx.navigation:navigation-compose:2.9.1")


            // Dependency Injection
            implementation("androidx.lifecycle:lifecycle-viewmodel:2.8.0-rc01")
            api("io.insert-koin:koin-core:3.6.0-alpha3")
            implementation("io.insert-koin:koin-compose:1.2.0-alpha3")

            // Preferences
            implementation("com.russhwolf:multiplatform-settings-no-arg:1.3.0")

            // Ui
            implementation("io.github.alexzhirkevich:compottie:2.0.2")

            // Db
            implementation("app.cash.sqldelight:coroutines-extensions:2.0.1")

            // Images loading
            implementation("media.kamel:kamel-image-default:1.0.8")
        }
        iosMain.dependencies{
            implementation("app.cash.sqldelight:native-driver:2.0.1")
        }

        commonTest.dependencies {
            implementation("org.jetbrains.kotlin:kotlin-test:2.2.20")
        }
    }
}

android {
    namespace = rootProject.extra["package"] as String
    compileSdk = rootProject.extra["compile_version"] as Int

    defaultConfig {
        applicationId = rootProject.extra["package"] as String
        minSdk = rootProject.extra["min_version"] as Int
        targetSdk = rootProject.extra["target_version"] as Int
        versionCode = 1
        versionName = "rc"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {

        // == TODO Debug & Release params from .jks == //
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
dependencies {
    debugImplementation(compose.uiTooling)
}

