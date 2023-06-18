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
        versionCode = 46
        versionName = "1.12"
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
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
    implementation(Dependencies.hiltAndroid)
    kapt(Dependencies.hiltAndroidCompiler)
    kapt(Dependencies.hiltCompiler)
    implementation(Dependencies.hiltNavigationCompose)
    kaptAndroidTest(Dependencies.hiltAndroidCompiler)
    implementation(Dependencies.charts)
    implementation(Dependencies.pager)
    implementation(Dependencies.serialization)
}