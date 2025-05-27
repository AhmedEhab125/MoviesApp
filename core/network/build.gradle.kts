plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.android)

}

android {
    namespace = "com.example.network"
    compileSdk = 35
    defaultConfig {
        minSdk = 24
        buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org/3\"")
        buildConfigField("String", "USER_TOKEN", "\"Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI1YzQ2OTJkYTZjM2Y5MDE3ODlhZGNhZTJlYzNkNGVhZCIsIm5iZiI6MTc0ODM0NzUxMi4xODcsInN1YiI6IjY4MzVhYTc4ODZhYTU0YzdjMGE4NzM4MiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.gnaWwjVz7LXBQ-yj7iqDyaoVDC752SqpCqQYAucGIv83\"")

    }
    buildFeatures {
        buildConfig = true
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    implementation(libs.kotlinx.serialization.json.v173)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.client.content.negotiation)

}