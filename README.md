<h1 align="center">News Zone</h1>

<p align="center">  
News Zone is an android application based on modern Android application tech-stacks and MVVM architecture.<br>This project is for focusing especially on the new library Dagger-Hilt of implementing dependency injection.<br>
Also fetching data from the network and integrating persisted data in the database via repository pattern.
</p>
</br>

## Tech stack & Open-source libraries
- Minimum SDK level 24
- [Kotlin](https://kotlinlang.org/) based, [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) for asynchronous.
- Dagger-Hilt for dependency injection.
- JetPack
  - LiveData - notify domain layer data to views.
  - Lifecycle - dispose of observing data when lifecycle state changes.
  - ViewModel - UI related data holder, lifecycle aware.
  - Room Persistence - construct a database using the abstract layer.
- Architecture
  - MVVM Architecture (View - DataBinding - ViewModel - Model)
  - Repository pattern
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit) - construct the REST APIs and paging network data.
- [Gson](https://github.com/square/retrofit/tree/master/retrofit-converters/gson) - A JSON library.
- [Coil](https://github.com/coil-kt/coil) - Coroutine Image Loader Library for loading images.
- [Lottie](https://github.com/airbnb/lottie-android) - Library for Android and iOS that parses Adobe After Effects animations.
- [Leak Canary](https://github.com/square/leakcanary) - A memory leak detection library for Android.

## Architecture
News Zone is based on MVVM architecture and a repository pattern.

![architecture](https://user-images.githubusercontent.com/24237865/77502018-f7d36000-6e9c-11ea-92b0-1097240c8689.png)

## Open API

<img src="newsapi.png" align="right" width="5%"/>

News Zone using the [NewsAPI](https://newsapi.org/) for constructing RESTful API.<br>
News API is a simple and easy-to-use API that returns JSON metadata for headlines and articles live all over the web right now.

## :camera_flash: Screenshots
<img src="/results/screenshot_1.jpeg" width="260">&emsp;<img src="/results/screenshot_2.jpeg" width="260">&emsp;<img src="/results/screenshot_6.jpeg" width="260">&emsp;<img src="/results/screenshot_3.jpeg" width="260">&emsp;<img src="/results/screenshot_4.jpeg" width="260">&emsp;<img src="/results/screenshot_5.jpeg" width="260">

Cached Results
<img src="/results/screenshot_7.jpeg" width="260">&emsp;<img src="/results/screenshot_2.png" width="260">
