buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Dependencies.kotlinGradlePlugin)
        classpath(Dependencies.androidBuildTools)
        classpath(Dependencies.sqlDelightGradlePlugin)
        classpath(Dependencies.hiltGradlePlugin)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url = java.net.URI("https://jitpack.io") }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}