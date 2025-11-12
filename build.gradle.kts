// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
<<<<<<< HEAD
    alias(libs.plugins.kotlin.compose) apply false
=======
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.compose.compiler) apply false
>>>>>>> 1b0f7fa545ce69c9aa292906525cb436b6327c39
}