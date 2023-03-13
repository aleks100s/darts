plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    kotlin("plugin.serialization") version Dependencies.kotlinVersion
}

android {
    namespace = "com.alextos.darts.android"
    compileSdk = Dependencies.targetSDKVersion
    defaultConfig {
        applicationId = "com.alextos.darts.android"
        minSdk = Dependencies.minimumSDKVersion
        targetSdk = Dependencies.targetSDKVersion
        versionCode = 24
        versionName = "1.2.5"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Dependencies.composeVersion
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(Dependencies.composeUi)
    implementation(Dependencies.composeUiTooling)
    implementation(Dependencies.composeUiToolingPreview)
    implementation(Dependencies.composeFoundation)
    implementation(Dependencies.composeMaterial)
    implementation(Dependencies.activityCompose)
    implementation(Dependencies.composeIconsExtended)
    implementation(Dependencies.composeNavigation)
    implementation(Dependencies.coilCompose)
    implementation(Dependencies.charts)
    implementation(Dependencies.pager)
    implementation(Dependencies.hiltAndroid)
    kapt(Dependencies.hiltAndroidCompiler)
    kapt(Dependencies.hiltCompiler)
    implementation(Dependencies.hiltNavigationCompose)

    androidTestImplementation(Dependencies.testRunner)
    androidTestImplementation(Dependencies.jUnit)
    androidTestImplementation(Dependencies.composeTesting)
    debugImplementation(Dependencies.composeTestManifest)

    kaptAndroidTest(Dependencies.hiltAndroidCompiler)
    androidTestImplementation(Dependencies.hiltTesting)
}