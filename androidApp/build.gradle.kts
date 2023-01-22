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
        minSdk = 28
        targetSdk = Dependencies.targetSDKVersion
        versionCode = 1
        versionName = "1.0"
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
    implementation("com.google.accompanist:accompanist-pager:0.22.0-rc")
    implementation("com.github.tehras:charts:0.2.4-alpha")
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