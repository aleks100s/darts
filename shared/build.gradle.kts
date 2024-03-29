plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("plugin.serialization") version Dependencies.kotlinVersion
    id("com.squareup.sqldelight")
}

kotlin {
    android()
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Dependencies.sqlDelightRuntime)
                implementation(Dependencies.sqlDelightCoroutinesExtensions)
                implementation(Dependencies.kotlinDateTime)
                implementation(Dependencies.coroutines)
                implementation(Dependencies.serialization)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(Dependencies.assertK)
                implementation(Dependencies.turbine)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(Dependencies.sqlDelightAndroidDriver)
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)

            dependencies {
                implementation(Dependencies.sqlDelightNativeDriver)
            }
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    namespace = "com.alextos.darts"
    compileSdk = Dependencies.targetSDKVersion
    defaultConfig {
        minSdk = Dependencies.minimumSDKVersion
        targetSdk = Dependencies.targetSDKVersion
    }
}

sqldelight {
    database("DartsDatabase") {
        packageName = "com.alextos.darts.database"
        schemaOutputDirectory = file("src/commonMain/sqldelight/database")
    }
}