plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.kimocoder.avfterminal.stubs"
    compileSdk = 35

    defaultConfig {
        minSdk = 35
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    compileOnly("io.grpc:grpc-stub:1.68.2")
    compileOnly("io.grpc:grpc-api:1.68.2")
    compileOnly("com.google.protobuf:protobuf-javalite:4.29.3")
}
