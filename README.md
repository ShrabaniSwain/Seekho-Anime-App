# Seekho-Anime-App

SeekhoAnime is a simple Android app that shows a list of top-rated anime using the Jikan API. You can view anime posters, titles, number of episodes, ratings, and also watch trailers. If there’s no trailer, it shows the anime poster instead. It also supports offline mode by saving the anime list locally using Room database.

- Displays top anime in a grid layout.
- Opens a detail screen when you tap on an anime.
- Shows trailer using YouTube Player or poster image.
- Saves anime list to local database for offline use.
- Shows a toast message when there’s no internet and loads from DB.

## Tech Stack

Kotlin – main programming language
Retrofit – used for API calls
OkHttp Logging Interceptor – for network logs
Room – for storing anime data locally.
Hilt – for dependency injection
Coroutines + Flow – for background tasks
Glide – for image loading
YouTube Player – for playing anime trailers
ViewBinding – for view access

## API Used
Top Anime List: "https://api.jikan.moe/v4/top/anime"
Anime Details: "https://api.jikan.moe/v4/anime/{anime_id}"
