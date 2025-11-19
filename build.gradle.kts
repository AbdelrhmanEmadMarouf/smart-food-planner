// Top-level build.gradle.kts
plugins {
    id("com.android.application") version "8.5.0" apply false
    id("com.android.library") version "8.5.0" apply false
    id("org.jetbrains.kotlin.android") version "2.0.10" apply false
    id("com.google.devtools.ksp") version "2.0.21-1.0.28" apply false

    // Firebase Google Services plugin (updated)
    id("com.google.gms.google-services") version "4.4.2" apply false}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}