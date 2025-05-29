plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.services"
    compileSdk = VersionManger.COMPILE_SDK

    defaultConfig {
        minSdk = VersionManger.MIN_SDK

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Project dependencies
    implementation(project(Modules.CORE_NETWORK))
    implementation(project(Modules.DOMAIN_MODELS))
    implementation(project(Modules.CORE_CASHING))

    // Serialization
    implementation(libs.kotlinx.serialization.json.v173)

    // Paging 3
    implementation(libs.androidx.paging.runtime.ktx)

    // Ktor
    implementation(libs.bundles.ktor.client)

    // Koin for DI
    implementation(libs.bundles.koin)

    implementation(libs.bundles.pagination)

}