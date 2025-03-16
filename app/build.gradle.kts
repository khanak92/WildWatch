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
    // AndroidX Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.core)
    implementation(libs.androidx.runner)

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:33.6.0")) // Ensure correct BOM version
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-database-ktx")
    implementation("com.google.firebase:firebase-storage-ktx")
    implementation("com.google.firebase:firebase-messaging")
    implementation("com.google.firebase:firebase-analytics-ktx")

    // UI & Animations
    implementation("com.airbnb.android:lottie:5.2.0") // Lottie Animations
    implementation("androidx.recyclerview:recyclerview:1.2.1") // RecyclerView
    implementation("com.github.bumptech.glide:glide:4.15.1")
    annotationProcessor("com.github.bumptech.glide:compiler:4.15.1")

    // YouTube Player
    implementation("com.pierfrancescosoffritti.androidyoutubeplayer:core:12.1.0")

    // TensorFlow Lite (for AI model inference)
    implementation("org.tensorflow:tensorflow-lite:2.9.0")
    implementation("org.tensorflow:tensorflow-lite-support:0.4.3")

    // PyTorch (for AI model inference)
    implementation("org.pytorch:pytorch_android:1.10.0") // Standard PyTorch
    implementation("org.pytorch:pytorch_android_torchvision:1.10.0") // TorchVision for image preprocessing

    // OpenCV
    implementation(project(":opencv"))

    // ExoPlayer for RTSP Camera Stream
    implementation(libs.exoplayer)
    implementation(libs.exoplayer.v2190)

    // HTTP Requests (OkHttp for communicating with Python Server)
    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //widget
    implementation("androidx.appcompat:appcompat:1.6.1")
}
