# WeatherApp ⛅

An Android weather forecast application, which was created using MVVM, Retrofit, Dagger Hilt, LiveData, Coroutines, Flow, Room, Navigation Components, Glide. The app fetches data from the [OpenWeatherMap API](https://openweathermap.org/api).<br />

<p align="center">
  <img src="https://i.imgur.com/1YiA1QC.gif" width="328" height="583">
</p>

# Architecture

The Model-View-ViewModel(MVVM) pattern was used, which separates the data presentation logic from the core business logic part of the application.
A single-activity architecture was used, using the Navigation Components to manage fragment operations.
![alt text](https://i.stack.imgur.com/rh3La.png)

# Technologies used:

• [Retrofit](https://square.github.io/retrofit/) - REST API Client <br />
• [Dagger Hilt](https://dagger.dev/hilt/) - dependency injection <br />
• [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - handles data with lifecycle awareness <br />
• [Coroutines, Flow](https://developer.android.com/kotlin/flow) - Thread management and data flow <br />
• [Room](https://developer.android.com/topic/libraries/architecture/room) - data persistance and offline caching <br />
• [Navigation Components](https://developer.android.com/guide/navigation) - handle navigations and passing of data between destinations <br />
• [Glide](https://github.com/bumptech/glide) - image loading <br />

# Installation

You will need an OpenWeatherMap API key. You need to put it in your local.properties file (create one if unavailable) and include the following line:
```
API_KEY = "YOUR_API_KEY"
```
You may need to Rebuild the project for it to work.
