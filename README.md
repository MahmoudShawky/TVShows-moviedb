# TVShows-moviedb
Android TVShows-moviedb is a minimalist android TV Shows application built on top of TMDb API

**Introduction**
This application uses pure Java, Android Jetpack and Google official libraries only.

# Getting Started
This project uses the Gradle build system. To build this project, use the `gradlew build` command or use "Import Project" in Android Studio.

# Application Architecture
Application implemented based on MVVM pattern and repository pattern.
[MVVM](https://developer.android.com/jetpack/guide#recommended-app-arch): (Model-View-ViewModel) pattern helps to completely separate the business and presentation logic from the UI, and the business logic and UI can be clearly separated for easier testing and easier maintenance.

<img src="https://github.com/MahmoudShawky/TVShows-moviedb/blob/master/screenshots/architecture.png">

# Used Libraries
[Architecture](https://developer.android.com/jetpack/arch/) - A collection of libraries that help you design robust, testable, and maintainable apps. Start with classes for managing your UI component lifecycle and handling data persistence.

 - [View Binding](https://developer.android.com/topic/libraries/view-binding)-  is a new view access mechanism that was released in conjunction with Android Studio, In most cases, view binding replaces `findViewById`.
 - [Lifecycles](https://developer.android.com/topic/libraries/architecture/lifecycle)  - Create a UI that automatically responds to lifecycle events.
 - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)  - Build data objects that notify views when the underlying database changes.
 - [Navigation](https://developer.android.com/topic/libraries/architecture/navigation/)  - Handle everything needed for in-app navigation.
 - [Room](https://developer.android.com/topic/libraries/architecture/room)  - Access your app's SQLite database with in-app objects and compile-time checks.
 - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)  - Store UI-related data that isn't destroyed on app rotations. Easily schedule asynchronous tasks for optimal execution.

[UI](): User Interface
 - [activity](https://developer.android.com/jetpack/androidx/releases/activity)- Access composable APIs built on top of Activity.
 - [appcompat](https://developer.android.com/jetpack/androidx/releases/appcompat) -Allows access to new APIs on older API versions of the platform (many using Material Design).
 - [Fragment](https://developer.android.com/guide/components/fragments)  - A basic unit of composable UI.
 - [cardview](https://developer.android.com/jetpack/androidx/releases/cardview) - Implement the Material Design card pattern with round corners and drop shadows.

[Dependency injection](https://developer.android.com/training/dependency-injection) - is a technique in which an object receives other objects that it depends on.

 - [Koin](https://insert-koin.io/)- A pragmatic lightweight dependency injection framework for Kotlin developers.
      Written in pure Kotlin, using functional resolution only: no proxy, no code generation, no reflection..
 
[Kotlin coroutines](https://developer.android.com/kotlin/coroutines)

[Unit Testing](https://developer.android.com/training/testing)
 
# Screenshots
<img src="https://github.com/MahmoudShawky/TVShows-moviedb/blob/master/screenshots/1.png"> <img src="https://github.com/MahmoudShawky/TVShows-moviedb/blob/master/screenshots/2.png"> <img src="https://github.com/MahmoudShawky/TVShows-moviedb/blob/master/screenshots/3.png"> <img src="https://github.com/MahmoudShawky/TVShows-moviedb/blob/master/screenshots/4.png"> 

# APK
  [**Download APK (Latest TV Moves)**](https://github.com/MahmoudShawky/TVShows-moviedb/blob/master/screenshots/TheAir_debug_1.0.0_latest.apk)
  [**Download APK (Popular TV Moves)**](https://github.com/MahmoudShawky/TVShows-moviedb/blob/master/screenshots/TheAir_debug_1.0.0_popular.apk)
