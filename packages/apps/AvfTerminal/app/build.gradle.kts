plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.kimocoder.avfterminal"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.kimocoder.avfterminal"
        minSdk = 35
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "../proguard.flags"
            )
        }
        debug {
            isMinifyEnabled = false
        }
    }

    sourceSets {
        getByName("main") {
            manifest.srcFile("../AndroidManifest.xml")
            java.srcDirs("../java")
            res.srcDirs("../res")
            assets.srcDirs("../assets")
        }
    }

    buildFeatures {
        aidl = false
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    lint {
        abortOnError = true
        checkReleaseBuilds = true
        // Disable lint checks that require missing platform SDK stubs
        disable += setOf("NewApi", "InlinedApi", "ObsoleteSdkInt")
    }
}

dependencies {
    // Compile-only stubs for hidden/platform APIs
    compileOnly(project(":stubs"))

    // AndroidX
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.1")
    implementation("androidx.navigation:navigation-fragment-ktx:2.8.9")
    implementation("androidx.window:window:1.3.0")
    implementation("androidx.work:work-runtime:2.10.1")
    implementation("androidx.lifecycle:lifecycle-process:2.8.7")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")
    implementation("androidx.fragment:fragment-ktx:1.8.6")
    implementation("androidx.viewpager2:viewpager2:1.1.0")
    implementation("androidx.startup:startup-runtime:1.2.0")
    implementation("androidx.annotation:annotation:1.9.1")
    implementation("androidx.core:core-ktx:1.15.0")

    // Material Design
    implementation("com.google.android.material:material:1.12.0")

    // Gson
    implementation("com.google.code.gson:gson:2.11.0")

    // Apache Commons Compress
    implementation("org.apache.commons:commons-compress:1.27.1")

    // gRPC
    implementation("io.grpc:grpc-stub:1.68.2")
    implementation("io.grpc:grpc-okhttp:1.68.2")
    implementation("io.grpc:grpc-api:1.68.2")
    implementation("io.grpc:grpc-core:1.68.2")
    implementation("io.grpc:grpc-protobuf-lite:1.68.2")

    // Protobuf
    implementation("com.google.protobuf:protobuf-javalite:4.29.3")
}
