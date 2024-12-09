import org.jetbrains.kotlin.gradle.targets.js.npm.importedPackageDir

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)

    // hilt
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")

}

android {
    namespace = "com.example.clockappbyrohan"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.clockappbyrohan"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
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
 val HILT_VERSION= "2.51.1"
 val ROOM_VERSION = "2.6.1"
 val MOCKITO_VERSION = "4.11.0"
 val CORE_TESTING_VERSION = "2.1.0"
 val KOTLINX_COROUTINES_TEST_VERSION = "1.7.3"

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

    // play services
    implementation ("com.google.android.gms:play-services-location:21.3.0")

    // hilt
    // FOR ANY VERSION CHANGE , ALSO CHANGE IT IN BUILD.GRADLE.KTS IN PROJECT LEVEL
    implementation("com.google.dagger:hilt-android:${HILT_VERSION}")
    kapt("com.google.dagger:hilt-android-compiler:${HILT_VERSION}")
    implementation ("androidx.hilt:hilt-navigation-compose:1.0.0")

    // for PermissionBox.kt
    implementation ("com.google.accompanist:accompanist-permissions:0.31.2-alpha")

    // for Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.11.0")




    // Room components
    implementation("androidx.room:room-runtime:${ROOM_VERSION}")
    implementation("androidx.room:room-ktx:${ROOM_VERSION}")
    annotationProcessor("androidx.room:room-compiler:${ROOM_VERSION}")


    testImplementation ("org.mockito:mockito-core:${MOCKITO_VERSION}")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:${KOTLINX_COROUTINES_TEST_VERSION}")
    androidTestImplementation ("androidx.arch.core:core-testing:${CORE_TESTING_VERSION}") // For LiveData/StateFlow








}

// allowing the references to the generated code
kapt{
    correctErrorTypes=true
}