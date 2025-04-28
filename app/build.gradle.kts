import org.gradle.kotlin.dsl.project

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.gms.google-services") // Firebase services
}

android {
    namespace = "com.example.wildwatch1"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.wildwatch1"
        minSdk = 24
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

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    // AndroidX Core & UI
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    implementation("androidx.core:core-ktx:1.12.0")


    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:33.6.0"))
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-database-ktx")
    implementation("com.google.firebase:firebase-storage-ktx")
    implementation("com.google.firebase:firebase-messaging")
    implementation("com.google.firebase:firebase-analytics-ktx")

    // ExoPlayer & RTSP Support (media3)
    implementation("androidx.media3:media3-exoplayer:1.3.1")
    implementation("androidx.media3:media3-ui:1.3.1")
    implementation("androidx.media3:media3-exoplayer-rtsp:1.3.1")

    // Networking (OkHttp)
    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

    // Lottie (UI animations)
    implementation("com.airbnb.android:lottie:5.2.0")

    // YouTube Player
    implementation("com.pierfrancescosoffritti.androidyoutubeplayer:core:12.1.0")

    // Glide (image loading)
    implementation("com.github.bumptech.glide:glide:4.15.1")
    implementation(libs.gms.play.services.maps)
    implementation(libs.androidx.gridlayout)
    annotationProcessor("com.github.bumptech.glide:compiler:4.15.1")

    // TensorFlow Lite (for AI model inference)
    implementation("org.tensorflow:tensorflow-lite:2.9.0")
    implementation("org.tensorflow:tensorflow-lite-support:0.4.3")

// PyTorch (for AI model inference)
    implementation("org.pytorch:pytorch_android:1.10.0") // Standard PyTorch
    implementation("org.pytorch:pytorch_android_torchvision:1.10.0") // TorchVision for image preprocessing

// OpenCV
    implementation(project(":opencv"))

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation ("com.airbnb.android:lottie:6.1.0")
}



