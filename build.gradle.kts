// 顶层构建脚本（所有子项目/模块共享配置）

// 1. 配置构建脚本的依赖（Kotlin DSL 推荐使用 buildscript 块）
buildscript {
    // 定义 Kotlin 版本变量（显式声明类型，或通过类型推断）
    val kotlinVersion = "1.9.10"  // 根据实际需求调整版本

    // 配置仓库（Maven Central 和 Google Maven 仓库）
    repositories {
        mavenCentral()  // Maven 中央仓库
        google()        // Google 官方 Maven 仓库（含 Android 相关库）
    }

    // 配置构建脚本的依赖（Gradle 插件和 Kotlin 插件）
    dependencies {
        // Android Gradle 插件（AGP）
        classpath("com.android.tools.build:gradle:8.6.0")
        // Kotlin Gradle 插件（与 kotlinVersion 变量绑定）
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    }
}

// 2. 配置所有子项目的公共仓库（可选，根据项目需求调整）
allprojects {
    repositories {
        mavenCentral()  // 子项目共享 Maven 中央仓库
        google()        // 子项目共享 Google Maven 仓库
    }
}
