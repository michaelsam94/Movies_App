🎬 # MoviesApp

An Android application that displays the best movies of 2024 using MVVM architecture, Retrofit, Room database, and Hilt for dependency injection. Users can view a list of top movies, see movie details, and add movies to their favorites.

📌 ## Features
✅ Fetches the best movies of 2024 from the TMDB API.
✅ Displays movie posters, names, ratings, and release dates.
✅ Users can view movie details including an overview, vote average, and original language.
✅ Add movies to favorites and access them offline using Room Database.
✅ Navigation Component with Bottom Navigation Bar.
✅ Pagination support for smooth scrolling.
✅ Dark mode support.

🚀 ## Tech Stack
Kotlin – Programming language
MVVM Architecture – Ensures separation of concerns
Hilt – Dependency injection
Retrofit + OkHttp – API calls
Room Database – Stores favorite movies locally
Navigation Component – Manages app navigation
LiveData + ViewModel – Reactive UI updates


🔧 ## Setup Instructions
1️⃣ Clone the Repository
`git clone https://github.com/yourusername/MoviesApp.git
cd MoviesApp`
2️⃣ Add TMDB API Key
This app uses The Movie Database (TMDB) API to fetch movies. You need to add your API key securely.

🔹 Option 1: Store API Key in local.properties (Recommended)
Open the local.properties file in the project's root directory.
Add the following line:
properties
`TMDB_API_KEY="your_api_key_here"`
Modify your build.gradle.kts (Module: app) to read the key dynamically:
`android {
defaultConfig {
buildConfigField("String", "TMDB_API_KEY", project.findProperty("TMDB_API_KEY") as String)
}
}`
Now, access the API key in your code using:
`BuildConfig.TMDB_API_KEY`
🔹 Option 2: Store API Key in a Constants File
Inside the utils package, create a Constants.kt file:
`package com.michael.moviesapp.utils
object Constants {
const val TMDB_API_KEY = "your_api_key_here"
}
Constants.TMDB_API_KEY`
🚨 Warning: This method exposes your API key if you push the code to GitHub. Prefer Option 1 for security.

📷 Screenshots
Movie List	Movie Details	Favorites
📜 License
This project is licensed under the MIT License – feel free to use, modify, and share!

⭐ Contributing
Fork the repository 🍴
Create a new branch feature/your-feature 🌱
Commit changes with meaningful messages 📝
Push to your fork and open a PR 📩