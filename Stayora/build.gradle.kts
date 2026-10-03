plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
}

allprojects {
    layout.buildDirectory.set(file("C:/Users/vamshi/.gradle-builds/Stayora/${project.name}"))
}
