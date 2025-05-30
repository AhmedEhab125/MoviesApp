# Movies App

A modern Android application built using Clean Architecture with MVI pattern to browse popular
movies, view details, and search for movies using The Movie Database (TMDb) API.
## 📱 Demo

https://github.com/user-attachments/assets/1c9177f1-01e0-4ca5-95c9-62be29336208

**Demo Video Show:**
- 🎬 Browse popular movies in grid/list view
- 🔍 Real-time search functionality  
- 📱 Movie details with beautiful UI
- 🔄 Pull-to-refresh and pagination
- 🌓 Dark/Light theme switching
- ⚡ Smooth animations and loading states

## 🏗️ Architecture

This project follows **Clean Architecture** principles with **MVI (Model-View-Intent)** pattern and
is organized into multiple modules:

### Core Modules

- **`core:base`** - Base classes for MVI pattern (BaseViewModel, ViewState, ViewEvent,
  ViewSideEffect)
- **`core:network`** - Network configuration using Ktor client with authentication
- **`core:ui`** - Shared UI components, themes, and utilities
- **`core:cashing`** - Room database implementation for local caching

### Domain Modules

- **`domain:models`** - Domain entities and business models
- **`domain:repositories_interfaces`** - Repository interfaces (contracts)
- **`domain:use_case`** - Business logic and use cases

### Data Modules

- **`data:services`** - API services and paging sources
- **`data:repositories`** - Repository implementations

### Feature Modules

- **`features:movies`** - Movies feature with screens and ViewModels

## 🎯 Features

### ✅ Implemented Features

1. **Popular Movies List**
    - ✅ Fetch and display popular movies from TMDb API
    - ✅ Pagination support using Paging 3
    - ✅ Both List and Grid view modes
    - ✅ Pull-to-refresh functionality
    - ✅ Movie cards with poster, title, rating, and release date

2. **Movie Details**
    - ✅ Detailed movie information screen
    - ✅ Movie poster, title, rating, overview, and release date
    - ✅ Beautiful UI with gradient overlays
    - ✅ Navigation back to movie list

3. **Search Feature**
    - ✅ Real-time search functionality
    - ✅ Search movies by title
    - ✅ Debounced search with 500ms delay
    - ✅ Search results pagination
    - ✅ Clear search functionality

4. **Caching System**
    - ✅ Local caching using Room database
    - ✅ Offline-first approach with RemoteMediator
    - ✅ Cache-first strategy for better performance
    - ✅ Automatic cache management

5. **Error Handling**
    - ✅ Network error handling
    - ✅ User-friendly error messages
    - ✅ Retry mechanisms
    - ✅ Loading states

6. **Modern UI/UX**
    - ✅ Material Design 3
    - ✅ Dark/Light theme support
    - ✅ Smooth animations and transitions
    - ✅ Responsive design
    - ✅ Beautiful movie cards with ratings

## 🛠️ Tech Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: Clean Architecture + MVI
- **Dependency Injection**: Koin
- **Network**: Ktor Client
- **Local Database**: Room
- **Pagination**: Paging 3
- **Image Loading**: Coil
- **Asynchronous**: Coroutines + Flow
- **Navigation**: Jetpack Navigation Compose

## 📱 API Integration

The app uses **The Movie Database (TMDb) API** to fetch movie data:

- **Base URL**: `https://api.themoviedb.org/3`
- **Endpoints Used**:
    - `GET /movie/popular` - Popular movies
    - `GET /search/movie` - Search movies
    - `GET /movie/{id}` - Movie details
- **Authentication**: Bearer token (configured in BuildConfig)

## 🗂️ Project Structure

```
app/
├── src/main/java/com/example/trianglezmoviesapp/
│   ├── navigation/         # Navigation setup
│   ├── ui/theme/          # App theme and colors
│   └── MainActivity.kt     # Main activity
│
core/
├── base/                  # Base MVI classes
├── network/               # Network configuration
├── ui/                    # Shared UI components
└── cashing/               # Room database setup
│
domain/
├── models/                # Domain entities
├── repositories_interfaces/ # Repository contracts
└── use_case/              # Business logic
│
data/
├── services/              # API services and paging
└── repositories/          # Repository implementations
│
features/
└── movies/                # Movies feature module
    ├── screens/
    │   ├── allMoviesScreen/    # Movies list screen
    │   └── movieDetailsScreen/ # Movie details screen
    └── di/                # Feature-specific DI
```

## 🧪 Testing

The project includes comprehensive unit tests:

- **Repository Tests**: Testing data layer logic
- **Use Case Tests**: Testing business logic
- **ViewModel Tests**: Testing UI logic
- **Paging Tests**: Testing pagination functionality

Run tests:

```bash
./gradlew test
```

## 📐 Design Principles

### SOLID Principles

- **Single Responsibility**: Each class has one reason to change
- **Open/Closed**: Open for extension, closed for modification
- **Liskov Substitution**: Derived classes are substitutable for base classes
- **Interface Segregation**: Clients depend only on interfaces they use
- **Dependency Inversion**: Depend on abstractions, not concretions

### Clean Architecture

- **Independence**: UI, Database, and Framework independence
- **Testability**: Easy to unit test business logic
- **Separation of Concerns**: Clear boundaries between layers

## 🎨 UI/UX Features

- **Material Design 3** with custom color schemes
- **Adaptive design** for different screen sizes
- **Smooth animations** and transitions
- **Loading states** with skeleton screens
- **Error handling** with retry options
- **Pull-to-refresh** functionality
- **View mode toggle** (List/Grid)
- **Search with debouncing** for better performance

## 📦 Dependencies

Key dependencies used in the project:

```kotlin
// UI & Compose
implementation(libs.androidx.compose.bom)
implementation(libs.androidx.material3)

// Network
implementation(libs.bundles.ktor.client)

// Database
implementation(libs.androidx.room.runtime)
implementation(libs.androidx.room.ktx)

// Dependency Injection
implementation(libs.bundles.koin)

// Pagination
implementation(libs.bundles.pagination)

// Image Loading
implementation(libs.coil.compose)
```

## 🔄 Data Flow

1. **UI Layer**: Compose screens observe ViewModel state
2. **Presentation Layer**: ViewModels execute use cases
3. **Domain Layer**: Use cases orchestrate business logic
4. **Data Layer**: Repositories manage data from API and cache
5. **Caching Strategy**: RemoteMediator handles cache-network coordination

## 📊 Performance Optimizations

- **Pagination**: Efficient data loading with Paging 3
- **Image Caching**: Coil handles image caching automatically
- **Database Caching**: Local storage for offline functionality
- **Debounced Search**: Reduces unnecessary API calls
- **LazyLoading**: Compose lazy layouts for smooth scrolling

