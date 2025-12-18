plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.courseproject.mlkuniversity"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.courseproject.mlkuniversity"
        minSdk = 26
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildToolsVersion = "36.1.0"
}

dependencies {
    implementation("androidx.fragment:fragment:1.8.9")
    implementation("com.google.android.material:material:1.13.0")
    implementation("androidx.viewpager2:viewpager2:1.1.0")
    implementation("com.squareup.okhttp3:okhttp:5.3.2")
    implementation("com.karumi:dexter:6.2.3")

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.cardview)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}