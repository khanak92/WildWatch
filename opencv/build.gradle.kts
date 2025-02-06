plugins {
    id("com.android.library")
}

android {
    namespace = "org.opencv"
    compileSdk = 34  // Use your app's compileSdk version

    defaultConfig {
        minSdk = 21
        @Suppress("DEPRECATION")
        targetSdk = 34
    }

    buildTypes {
        release {
            isMinifyEnabled = false  // FIXED: Use "isMinifyEnabled" instead of "minifyEnabled"
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
}
