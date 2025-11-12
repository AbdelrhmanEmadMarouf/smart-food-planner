plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
<<<<<<< HEAD
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.homeui_foodplannerproject"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.homeui_foodplannerproject"
=======
    id("com.google.devtools.ksp")

    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.example.smart_food_planner"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.smart_food_planner"
>>>>>>> 1b0f7fa545ce69c9aa292906525cb436b6327c39
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
<<<<<<< HEAD
=======

>>>>>>> 1b0f7fa545ce69c9aa292906525cb436b6327c39
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
<<<<<<< HEAD
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
=======

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true // مهم جداً لتشغيل Compose
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3" // إصدار Compose Compiler
>>>>>>> 1b0f7fa545ce69c9aa292906525cb436b6327c39
    }
}

dependencies {

<<<<<<< HEAD
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.2")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0" )
    implementation(libs.androidx.ui.tooling.preview)
    implementation("androidx.datastore:datastore-preferences:1.1.1")
    implementation("io.coil-kt:coil-compose:2.5.0")
    implementation(libs.androidx.material3)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.3.9")
    //implementation("io.coil-kt.coil3:coil-compose:3.3.0")
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
=======

    // ViewModel for fragment
    implementation("androidx.fragment:fragment-ktx:1.6.2")

    //Gemini
    implementation("com.google.ai.client.generativeai:generativeai:0.4.0")

    // Compose UI
    implementation("androidx.compose.ui:ui:1.5.3")
    implementation("androidx.compose.ui:ui-tooling-preview:1.5.3")
    implementation("androidx.compose.material3:material3:1.2.0")
    implementation("androidx.activity:activity-compose:1.9.0")

    // Debug Tools (optional)
    debugImplementation("androidx.compose.ui:ui-tooling:1.5.3")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.5.3")

    // Room
    val room_version = "2.8.1"
    implementation("androidx.room:room-runtime:$room_version")
    ksp("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.google.code.gson:gson:2.11.0")

    // Glide
    implementation("com.github.bumptech.glide:glide:4.16.0")

    // Coroutine
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9")

    // AndroidX & Material
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // Tests
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)




}
>>>>>>> 1b0f7fa545ce69c9aa292906525cb436b6327c39
