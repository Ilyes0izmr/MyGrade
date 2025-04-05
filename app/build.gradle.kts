plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.myapplication"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.myapplication"
        minSdk = 33
        targetSdk = 35
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation (libs.retrofit)
    implementation (libs.retrofit.gson.converter)
    implementation (libs.coroutines.android)
    implementation (libs.coroutines.core)
    implementation(libs.glide)
    implementation(libs.okhttp) // OkHttp core
    implementation(libs.logging.interceptor) // Logging interceptor

    implementation(libs.espresso.core)
    implementation(libs.androidx.recyclerview)
    implementation(libs.firebase.firestore)
    annotationProcessor(libs.glideCompiler)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.androidx.core)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}