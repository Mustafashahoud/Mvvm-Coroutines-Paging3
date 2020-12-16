# NewsApp

* A News Application uses [NEWS API](https://newsapi.org/) based on Kotlin and MVVM architecture.
* A single-activity pattern, using the Navigation component to manage fragment operations.
* Handles background tasks using coroutines + Flow.

## Paging 3
* The interesting thing  about this repository is that it implements the [Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) Library that has so many features that simplified complicated process creating RecyclerView with paging.
    - Loading small chunk of data that reduces usage of network bandwidth and system resources.
    - Built-in support for error handling, including refresh and retry capabilities.
    - Built-in separator, header, and footer support.
    - Automatically requests the correct page when the user has scrolled to the end of the list.
    - Ensures that multiple requests are not triggered at the same time.


## Libraries
- 100% Kotlin + [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/)
- MVVM Architecture
- Architecture Components (Lifecycle, Paging, ViewModel, DataBinding, Navigation, Room)
- [NEWS API](https://newsapi.org/)
- [Dagger2 Hilt](https://dagger.dev/hilt/) for dependency injection
- [Retrofit2 & Gson](https://github.com/square/retrofit) for REST API
- [leakCanary](https://github.com/square/leakcanary) Memory leak detection library for Android.
- [Coil](https://github.com/coil-kt/coil) for loading images

### Branches
|     Sample     | Description |
| ------------- | ------------- |
| [master](https://github.com/Mustafashahoud/Mvvm-Coroutines-Paging3/tree/master) | The base for the rest of the other branch. <br/>Uses Kotlin, Architecture Components, Coroutines + Flow, Dagger, Retrofit Data Binding, etc. |
| [paging3-network-db](https://github.com/Mustafashahoud/Mvvm-Coroutines-Paging3/tree/paging3-network-db)| It uses RemoteMediator with Room DAO + PagingSource as single source of truth|

## App Demo


<p align="center">
  <img src="https://user-images.githubusercontent.com/33812602/99159508-32646400-26dd-11eb-90bb-d20331befb0d.jpg"  width="250" />
  <img src="https://user-images.githubusercontent.com/33812602/99159524-5627aa00-26dd-11eb-9bca-12a8cbaf71ab.jpg"  width="250"/>
  <img src="https://user-images.githubusercontent.com/33812602/99159541-73f50f00-26dd-11eb-9c73-3f7bf9cc28e1.jpg"  width="250"/>
</p>


## License
```xml
Copyright 2020 The Android Open Source Project, Inc.
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
