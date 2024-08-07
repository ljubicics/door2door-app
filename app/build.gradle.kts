import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.compose.compiler)
}

val projectPropertiesFile = rootProject.file("project.properties")
val projectProperties = Properties()
projectProperties.load(FileInputStream(projectPropertiesFile))

android {
    namespace = "com.example.door2door_app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.door2door_app"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField("String", "CONSUMER_KEY", "\"${projectProperties["MAPS_DEFAULT-KEY"]}\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //Viewmodel
    implementation(libs.androidx.lifecycle.viewmodel)

    // Navigation
    implementation(libs.compose.navigation)

    //Serialization
    implementation(libs.kotlinx.serialization.json)

    //Koin
    implementation(libs.koin.android)

    //Ktor
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.negotiation)
    implementation(libs.ktor.serialization)

    // Font
    implementation(libs.google.font)

    // Maps
    implementation(libs.mapbox)
    implementation(libs.mapbox.compose)

//    implementation("com.mapbox.navigationcore:navigation:3.3.0-rc.1")
//    implementation("com.mapbox.navigationcore:copilot:3.3.0-rc.1")
//    implementation("com.mapbox.navigationcore:ui-maps:3.3.0-rc.1")
//    implementation("com.mapbox.navigationcore:voice:3.3.0-rc.1")
//    implementation("com.mapbox.navigationcore:tripdata:3.3.0-rc.1")
//    implementation("com.mapbox.navigationcore:android:3.3.0-rc.1")
//    implementation("com.mapbox.navigationcore:ui-components:3.3.0-rc.1")
}