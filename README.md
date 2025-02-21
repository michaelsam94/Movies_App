# 🎬 MoviesApp  
An Android application that displays the best movies of 2024 using **MVVM architecture**, **Retrofit**, **Room database**, and **Hilt** for dependency injection. Users can view a list of top movies, see movie details, and add movies to their favorites.

---

## 📌 Features  
✅ Fetches the **best movies of 2024** from the TMDB API.  
✅ Displays **movie posters, names, ratings, and release dates**.  
✅ Users can **view movie details** including an overview, vote average, and original language.  
✅ **Add movies to favorites** and access them offline using **Room Database**.  
✅ **Navigation Component** with Bottom Navigation Bar.  
✅ **Pagination support** for smooth scrolling.  
✅ **Dark mode support**.  

---

## 🚀 Tech Stack  
- **Kotlin** – Programming language  
- **MVVM Architecture** – Ensures separation of concerns  
- **Hilt** – Dependency injection  
- **Retrofit + OkHttp** – API calls  
- **Room Database** – Stores favorite movies locally  
- **Navigation Component** – Manages app navigation  
- **LiveData + ViewModel** – Reactive UI updates  

---

## 🔧 Setup Instructions  

### 1️⃣ Clone the Repository  
```sh
git clone https://github.com/yourusername/MoviesApp.git
cd MoviesApp
```

### 2️⃣ Add TMDB API Key  
This app uses **The Movie Database (TMDB) API** to fetch movies. You need to add your **API key** securely.  

#### 🔹 **Option 1: Store API Key in `local.properties` (Recommended)**
1. Open the **`local.properties`** file in the project's root directory.  
2. Add the following line:  
   ```properties
   TMDB_API_KEY="your_api_key_here"
   ```
3. Modify your `build.gradle.kts` (**Module: app**) to read the key dynamically:  
   ```kotlin
   android {
       defaultConfig {
           buildConfigField("String", "TMDB_API_KEY", project.findProperty("TMDB_API_KEY") as String)
       }
   }
   ```
4. Now, access the API key in your code using:  
   ```kotlin
   BuildConfig.TMDB_API_KEY
   ```

#### 🔹 **Option 2: Store API Key in a Constants File**
1. Inside the **`utils`** package, create a `Constants.kt` file:  
   ```kotlin
   package com.michael.moviesapp.utils

   object Constants {
       const val TMDB_API_KEY = "your_api_key_here"
   }
   ```
2. Access it anywhere in your code:  
   ```kotlin
   Constants.TMDB_API_KEY
   ```
🚨 **Warning:** This method exposes your API key if you push the code to GitHub. Prefer **Option 1** for security.  

---

## 📷 Screenshots  

| Movie List  | Movie Details | Favorites |
|-------------|--------------|-----------|
| ![Movie List](https://i.ibb.co/pj277Ntw/Screenshot-1740174545.png) | ![Movie Details](https://i.ibb.co/rGmbLCnG/Screenshot-1740174549.png) | ![Favorites](https://i.ibb.co/XkfWHQQ1/Screenshot-1740174552.png) |

---

## 📜 License  
This project is licensed under the **MIT License** – feel free to use, modify, and share!  

---

## ⭐ Contributing  
1. Fork the repository 🍴  
2. Create a new branch `feature/your-feature` 🌱  
3. Commit changes with meaningful messages 📝  
4. Push to your fork and open a PR 📩  
