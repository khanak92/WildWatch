plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.gms.google-services") //newl added

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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.play.services.maps)
    implementation(libs.androidx.core)
    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.database.ktx)
    implementation(libs.firebase.storage.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    this.implementation(platform("com.google.firebase:firebase-bom:33.6.0")) // Adjust version as needed
    implementation(libs.firebase.analytics.ktx) // Add relevant Firebase dependencies
    implementation(libs.lottie) //Lottie Files
    implementation ("androidx.recyclerview:recyclerview:1.2.1")
    implementation ("com.pierfrancescosoffritti.androidyoutubeplayer:core:12.1.0")  // YouTube Player
    implementation ("com.github.bumptech.glide:glide:4.15.1")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.15.1")
    implementation ("com.google.firebase:firebase-database:20.3.0")
    implementation ("com.google.firebase:firebase-auth:23.1.0")

    //Model's dependencies
    implementation ("org.tensorflow:tensorflow-lite:2.9.0")
    implementation ("org.tensorflow:tensorflow-lite-support:0.4.3")
    implementation ("com.google.firebase:firebase-database:20.2.2")
    implementation ("org.pytorch:pytorch_android_lite:1.10.0")
    implementation ("org.pytorch:pytorch_android_torchvision:1.10.0")
    implementation(project(":opencv"))




// ExoPlayer for RTSP Camera Stream
    implementation (libs.exoplayer)
    implementation (libs.exoplayer.v2190)

}

