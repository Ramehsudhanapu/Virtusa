import com.android.build.api.dsl.Packaging
import dependency.MyDependency

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
}

android {
    namespace = "com.ramesh.virtusa"
    compileSdk = Versions.compile_sdk

    defaultConfig {
        applicationId = "com.ramesh.virtusa"
        minSdk = Versions.min_sdk
        targetSdk = Versions.target_sdk
        versionCode = Versions.version_code
        versionName = Versions.version_name
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }


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
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose_compiler
    }
    fun Packaging.() {
        resources {
            excludes += "META-INF/"
            excludes += "okhttp3/"
            excludes += "kotlin/"
            excludes += "org/"
            excludes += ".properties"
            excludes += ".bin"
        }
    }
    tasks.withType().configureEach {
        kotlinOptions {
            freeCompilerArgs = freeCompilerArgs + listOf(
                "-opt-in=kotlin.RequiresOptIn",
                "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
                "-opt-in=androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi",
            )
        }
    }
}
    dependencies {
        // DEFAULT DEPENDENCIES
        api(MyDependency.core_ktx)
        api(MyDependency.lifecycle_ktx)

        // COMPOSE
        api(MyDependency.activity_compose)
        api(platform("androidx.compose:compose-bom:2023.06.00"))
        api("androidx.compose.ui:ui")
        api("androidx.compose.ui:ui-graphics")
        api("androidx.compose.ui:ui-tooling-preview")
        api("androidx.compose.material3:material3")
        api("androidx.compose.material3:material3-window-size-class")
        api(MyDependency.navigation_compose)
        implementation("com.google.firebase:firebase-crashlytics-buildtools:3.0.3")
        implementation("androidx.glance:glance:1.1.1")


        // TESTING
        testImplementation(MyDependency.junit)
        testImplementation(MyDependency.instance)
        testImplementation(MyDependency.mocktio)


        androidTestImplementation(MyDependency.test_ext_junit)
        androidTestImplementation(MyDependency.espresso_core)
        androidTestImplementation(platform("androidx.compose:compose-bom:2023.06.00"))
        androidTestImplementation("androidx.compose.ui:ui-test-junit4")
        debugImplementation("androidx.compose.ui:ui-tooling")
        debugImplementation("androidx.compose.ui:ui-test-manifest")

        // MOCKITO-KOTLIN
        testImplementation(MyDependency.mockito_kotlin)

        // COROUTINES TEST
        testImplementation(MyDependency.coroutines_test)

        // REMOTE
        api(MyDependency.retrofit)
        api(MyDependency.retrofit2_converter_gson)
        api(MyDependency.retrofit2_adapter_rxjava2)
        api(MyDependency.okhttp3)

        // COIL
        api(MyDependency.coil)

        // Hilt
        implementation(MyDependency.hilt_android)
        kapt(MyDependency.hilt_android_compiler)
        api(MyDependency.hilt_compose) {
            exclude("androidx.lifecycle", "lifecycle-viewmodel-ktx")
        }
        kapt(MyDependency.hilt_compose_compiler)

        // PAGER
        api(MyDependency.accompanist_pager)
        api(MyDependency.accompanist_pager_indicator)




        // System UI Controller
        api(MyDependency.accompanist_systemuicontroller)


    }

