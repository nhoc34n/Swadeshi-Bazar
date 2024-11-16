buildscript {
    repositories {
        google() // Add Google's Maven repository
        mavenCentral() // Optional, if needed
    }

    dependencies {
        classpath("com.google.gms:google-services:4.3.15") // Google services plugin classpath
    }
}

plugins {
    // Your plugin configurations for other dependencies go here
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.google.gms.google.services) apply false
}
