// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    val HILT_VERSION= "2.51.1"

    id("com.google.dagger.hilt.android") version "${HILT_VERSION}" apply false


}