plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
    alias(libs.plugins.kotlin.compose)
}

// Define environment variable for API key
val accountToken = System.getenv("ACCOUNT_TOKEN") ?: throw GradleException("ACCOUNT_TOKEN not found")
val authorizationToken = System.getenv("AUTHORIZATION_TOKEN") ?: throw GradleException("AUTHORIZATION_TOKEN not found")

android {
    namespace = "dev.emg.fleetio"
    compileSdk = 35

    defaultConfig {
        applicationId = "dev.emg.fleetio"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "dev.emg.fleetio.CustomTestRunner"

        buildConfigField("String", "ACCOUNT_TOKEN", "\"$accountToken\"")
        buildConfigField("String", "AUTHORIZATION_TOKEN", "\"$authorizationToken\"")
    }

    buildFeatures {
        buildConfig = true
        compose = true
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    kapt {
        correctErrorTypes = true
    }
}

dependencies {
    implementation(libs.androidx.runner)
    val composeBom = platform(libs.compose.bom)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.material)
    implementation(libs.retrofit)
    implementation(libs.loggingInterceptor)
    implementation(libs.moshi)
    implementation(libs.moshi.converter)
    ksp(libs.moshi.codegen)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(composeBom)
    implementation(libs.paging)
    implementation(libs.paging.compose)
    implementation(libs.compose.material3)
    implementation(libs.compose.activity)
    implementation(libs.compose.foundation)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling)
    implementation(libs.compose.tooling)
    implementation(libs.compose.preview)
    implementation(libs.timber)
    implementation(libs.coil.compose)
    implementation(libs.coil.network)
    implementation(libs.navigation)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.kotlin.serialization)

    // Unit test
    testImplementation(libs.junit)
    testImplementation(libs.hilt.android.testing)
    testImplementation(libs.kotlin.test)
    testImplementation(libs.androidx.archcore.testing)
    testImplementation(libs.kotlinx.coroutines.android)
    testImplementation(libs.kotlinx.coroutines.test)
    kaptTest(libs.dagger.hilt.android.compiler)

    // Instrumentation test
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(composeBom)
    androidTestImplementation(libs.paging.common)
}