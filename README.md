# Anime Discovery App

## 📱 Application Overview
An Android application built with modern Android development practices that allows users to discover and explore anime from the Jikan API (MyAnimeList API). The app features a clean UI, robust architecture, and support for both light/dark themes.

## 🏗️ Architecture
The application follows a layered **MVVM (Model-View-ViewModel)** architecture with clear separation of concerns:

```
┌─────────────────────────────────────────────────────────────┐
│                        Presentation Layer                    │
│  (UI Components, ViewModels, Screens, Navigation)           │
├─────────────────────────────────────────────────────────────┤
│                        Domain Layer                          │
│  (Use Cases, Repositories Interfaces, Domain Models)        │
├─────────────────────────────────────────────────────────────┤
│                        Data Layer                            │
│  (Repository Implementations, DTOs, API Clients, Managers)  │
└─────────────────────────────────────────────────────────────┘
```

## 📁 Complete Project Structure Tree

```
com.diiage.template/
├── Application.kt                    # Koin initialization
├── MainActivity.kt                   # Main entry point
│
├── data/
│   ├── dto/                          # Data Transfer Objects
│   │   ├── AnimeDto.kt
│   │   ├── AnimeListResponseDto.kt
│   │   ├── ImagesDto.kt
│   │   └── PaginationDto.kt
│   │
│   ├── manager/                      # Platform-specific managers
│   │   └── SoundManager.kt
│   │
│   ├── remote/                       # Network layer
│   │   ├── HttpClient.kt
│   │   └── JikanApi.kt
│   │
│   └── repository/                   # Data source implementations
│       └── AnimeRepositoryImpl.kt
│
├── di/                               # Dependency Injection
│   └── AppModule.kt
│
├── domain/                           # Business logic layer
│   ├── model/                        # Domain models
│   │   ├── Anime.kt
│   │   └── SoundType.kt
│   │
│   └── repository/                   # Repository interfaces
│       ├── AnimeRepository.kt
│       └── SoundManagerRepository.kt
│
└── ui/                               # Presentation layer
    ├── core/
    │   ├── components/               # Reusable UI components
    │   │   ├── AnimeCard.kt
    │   │   ├── ErrorDialog.kt
    │   │   ├── input/
    │   │   │   ├── PrimaryButton.kt
    │   │   │   ├── PrimaryTextField.kt
    │   │   │   ├── SearchBar.kt
    │   │   │   ├── ToggleElementButton.kt
    │   │   │   └── ToggleSwitch.kt
    │   │   ├── layout/
    │   │   │   ├── CenteredBox.kt
    │   │   │   ├── CenteredColumn.kt
    │   │   │   ├── MainScaffold.kt
    │   │   │   └── Spacers.kt
    │   │   ├── Screen.kt
    │   │   └── state/                # UI state components
    │   │       ├── EmptySearchResult.kt
    │   │       ├── EmptyState.kt
    │   │       ├── ErrorState.kt
    │   │       └── LoadingState.kt
    │   │
    │   ├── Navigation.kt              # Navigation setup
    │   ├── theme/                     # Theme management
    │   │   ├── Color.kt
    │   │   ├── Theme.kt
    │   │   ├── ThemeManager.kt
    │   │   └── Type.kt
    │   │
    │   └── ViewModel.kt              # Base ViewModel
    │
    └── screens/                       # Screen implementations
        ├── anime/
        │   ├── AnimeListScreen.kt
        │   └── AnimeListViewModel.kt
        └── splash/
            ├── SplashScreen.kt
            └── SplashViewModel.kt
```

## 🏗️ Architecture Diagram

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                           PRESENTATION LAYER                                │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│  ┌─────────────────┐    ┌─────────────────┐    ┌─────────────────────┐    │
│  │   Screen/       │    │   ViewModel     │    │   UI Components     │    │
│  │   Composables   │◄──►│   (State Holder)│◄──►│   (Reusable)        │    │
│  │                 │    │                 │    │                     │    │
│  │ • AnimeListScreen│    │ • AnimeListViewModel│ • AnimeCard         │    │
│  │ • SplashScreen  │    │ • SplashViewModel  │ • SearchBar         │    │
│  └─────────────────┘    └─────────────────┘    • LoadingState      │    │
│         │                         │              • ErrorDialog       │    │
│         │                         ▼              └─────────────────────┘    │
│         │             ┌─────────────────────┐                              │
│         └────────────►│    UI Events       │                              │
│                       │  (User Actions)     │                              │
│                       └─────────────────────┘                              │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│                           DOMAIN LAYER                                      │
│                           (Business Logic)                                  │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│  ┌─────────────────────────────────────────────────────────────────────┐    │
│  │                    Repository Interfaces                             │    │
│  │  ┌─────────────────┐          ┌─────────────────┐                  │    │
│  │  │ AnimeRepository │          │ SoundManager    │                  │    │
│  │  │                 │          │ Repository      │                  │    │
│  │  │ • getTopAnime() │          │ • playSound()   │                  │    │
│  │  │ • searchAnime() │          │ • stopSounds()  │                  │    │
│  │  └─────────────────┘          └─────────────────┘                  │    │
│  │                                                                     │    │
│  │  ┌─────────────────────────────────────────────────────────────┐    │    │
│  │  │                    Domain Models                             │    │    │
│  │  │    ┌─────────────┐               ┌─────────────┐            │    │    │
│  │  │    │    Anime    │               │  SoundType  │            │    │    │
│  │  │    │             │               │             │            │    │    │
│  │  │    │ • id        │               │ • Click     │            │    │    │
│  │  │    │ • title     │               │ • Success   │            │    │    │
│  │  │    │ • imageUrl  │               │ • Error     │            │    │    │
│  │  │    │ • score     │               └─────────────┘            │    │    │
│  │  │    └─────────────┘                                          │    │    │
│  │  └─────────────────────────────────────────────────────────────┘    │    │
│  └─────────────────────────────────────────────────────────────────────┘    │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│                           DATA LAYER                                        │
│                           (Data Sources)                                    │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│  ┌─────────────────────────────────────────────────────────────────────┐    │
│  │                   Repository Implementations                         │    │
│  │  ┌─────────────────┐          ┌─────────────────┐                  │    │
│  │  │ AnimeRepository │          │ SoundManager    │                  │    │
│  │  │     Impl        │          │ (Implementation)│                  │    │
│  │  │                 │          │                 │                  │    │
│  │  │ • Maps DTOs     │          │ • MediaPlayer   │                  │    │
│  │  │ to Domain Models│          │ • RingtoneManager│                 │    │
│  │  └────────┬────────┘          └─────────────────┘                  │    │
│  │           │                                                         │    │
│  │           ▼                                                         │    │
│  │  ┌─────────────────┐                                                │    │
│  │  │    JikanApi     │                                                │    │
│  │  │   (Service)     │                                                │    │
│  │  │                 │                                                │    │
│  │  │ • getTopAnime() │                                                │    │
│  │  │ • searchAnime() │                                                │    │
│  │  └────────┬────────┘                                                │    │
│  │           │                                                         │    │
│  │           ▼                                                         │    │
│  │  ┌─────────────────┐          ┌─────────────────┐                  │    │
│  │  │  HttpClient     │          │      DTOs       │                  │    │
│  │  │   (Ktor)        │          │   (Data Objects)│                  │    │
│  │  │                 │          │                 │                  │    │
│  │  │ • Base URL      │◄────────►│ • AnimeDto      │                  │    │
│  │  │ • Timeouts      │          │ • ImagesDto     │                  │    │
│  │  │ • Logging       │          │ • PaginationDto │                  │    │
│  │  │ • Serialization │          └─────────────────┘                  │    │
│  │  └─────────────────┘                                                │    │
│  └─────────────────────────────────────────────────────────────────────┘    │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│                       DEPENDENCY INJECTION                                  │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│  ┌─────────────────────────────────────────────────────────────────────┐    │
│  │                        AppModule.kt                                 │    │
│  │                                                                     │    │
│  │  single<HttpClient> { createHttpClient(baseUrl = RMAPI_URL) }      │    │
│  │  single { JikanApi(get()) }                                        │    │
│  │  single<AnimeRepository> { AnimeRepositoryImpl(get()) }            │    │
│  │  single<SoundManager> { SoundManager(androidContext()) }           │    │
│  │                                                                     │    │
│  └─────────────────────────────────────────────────────────────────────┘    │
└─────────────────────────────────────────────────────────────────────────────┘
```

## 🔄 Data Flow Sequence

### 1. **User Action Flow** (Searching for Anime)
```
┌─────────┐    ┌─────────────┐    ┌─────────────┐    ┌─────────────┐    ┌─────────────┐
│   UI    │    │ ViewModel   │    │ Repository  │    │   API       │    │  Jikan API  │
│         │    │             │    │             │    │  Service    │    │             │
└────┬────┘    └──────┬──────┘    └──────┬──────┘    └──────┬──────┘    └──────┬──────┘
     │                │                   │                  │                  │
     │ User types     │                   │                  │                  │
     │ search query   │                   │                  │                  │
     │───────────────►│                   │                  │                  │
     │                │                   │                  │                  │
     │                │ Calls             │                  │                  │
     │                │ searchAnime()     │                  │                  │
     │                │──────────────────►│                  │                  │
     │                │                   │                  │                  │
     │                │                   │ Calls JikanApi  │                  │
     │                │                   │ searchAnime()   │                  │
     │                │                   │────────────────►│                  │
     │                │                   │                  │                  │
     │                │                   │                  │ Makes HTTP GET   │
     │                │                   │                  │ to /anime?q=...  │
     │                │                   │                  │─────────────────►│
     │                │                   │                  │                  │
     │                │                   │                  │                  │ Processes
     │                │                   │                  │                  │ request
     │                │                   │                  │                  │───┐
     │                │                   │                  │                  │   │
     │                │                   │                  │◄─────────────────│   │
     │                │                   │                  │ JSON Response    │   │
     │                │                   │◄─────────────────│                  │◄──┘
     │                │                   │ Maps DTO to      │                  │
     │                │                   │ Domain Model     │                  │
     │                │◄──────────────────│                  │                  │
     │                │ Updates UI State  │                  │                  │
     │◄───────────────│                   │                  │                  │
     │ Displays       │                   │                  │                  │
     │ Results        │                   │                  │                  │
```

### 2. **Dependency Injection Flow**
```
┌─────────────────┐      ┌─────────────────┐      ┌─────────────────┐
│  Application.kt │      │   AppModule     │      │   ViewModel     │
│                 │      │                 │      │                 │
│  startKoin {    │      │  module {       │      │                 │
│    modules(     │─────►│    single<      │      │  class Anime-   │
│      appModule  │      │      AnimeRepo- │      │  ListViewModel( │
│    )            │      │      sitory> {  │─────►│    private val  │
│  }              │      │        Anime-   │      │    repository:  │
└─────────────────┘      │        Repo-    │      │    AnimeRepo-   │
                         │        sitory-  │      │    sitory       │
                         │        Impl(    │      │  ) { ... }      │
                         │          get()  │      │                 │
                         │        )        │      │                 │
                         │    }            │      │                 │
                         └─────────────────┘      └─────────────────┘
```

### 3. **Layer Communication Rules**
```
┌─────────────────────────────────────────────────────────────────┐
│                     DEPENDENCY DIRECTION                        │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  Presentation Layer → Domain Layer → Data Layer                 │
│         ↑                   ↑                   ↑               │
│  (Depends on)        (Depends on)        (Concrete            │
│  Domain Layer        Data Layer           Implementations)     │
│                                                                 │
│  KEY:                                                           │
│  • → = Depends on                                               │
│  • ← = Injected into                                            │
│                                                                 │
│  PRESENTATION can:                                              │
│  • Call Domain interfaces                                       │
│  • Observe Domain models                                        │
│  • NOT call Data layer directly                                 │
│                                                                 │
│  DOMAIN can:                                                    │
│  • Define Repository interfaces                                 │
│  • Define Use Cases                                            │
│  • NOT depend on Android SDK                                   │
│                                                                 │
│  DATA can:                                                      │
│  • Implement Domain interfaces                                  │
│  • Use Android SDK                                             │
│  • Make network calls                                          │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

### Key Architectural Patterns:
- **Clean Architecture**: Separation of concerns with domain, data, and presentation layers
- **Unidirectional Data Flow**: State management through ViewModels
- **Dependency Injection**: Using Koin for managing dependencies
- **Reactive UI**: Jetpack Compose for declarative UI development

## 📁 Project Structure

### Domain Layer (`domain/`)
Contains business logic and domain models:
- **`model/`**: Core domain entities (`Anime`, `SoundType`)
- **`repository/`**: Repository interfaces defining data operations

### Data Layer (`data/`)
Handles data retrieval and persistence:
- **`dto/`**: Data Transfer Objects for API responses
- **`remote/`**: API client setup and HTTP communication
- **`repository/`**: Repository implementations
- **`manager/`**: Platform-specific services (SoundManager)

### Presentation Layer (`ui/`)
Manages UI components and state:
- **`screens/`**: Complete screen implementations
    - `splash/`: Splash screen with loading state
    - `anime/`: Anime listing and search functionality
- **`core/`**: Reusable components and utilities
    - `components/`: Shared UI components (cards, buttons, etc.)
    - `theme/`: Theme management and styling
    - `state/`: UI state representations

### Dependency Injection (`di/`)
- **`AppModule.kt`**: Koin module defining all dependencies

## 🛠️ Technical Stack

### Core Technologies
- **Kotlin**: Primary programming language
- **Jetpack Compose**: Modern declarative UI toolkit
- **Koin**: Dependency injection framework
- **Ktor**: HTTP client for network requests
- **Kotlinx Serialization**: JSON serialization/deserialization

### Key Dependencies
- **`androidx.compose.*`**: UI components and Material Design 3
- **`androidx.navigation.compose`**: Navigation between screens
- **`io.insert-koin`**: Dependency injection
- **`io.ktor`**: HTTP client with logging and timeout support
- **`io.coil-kt`**: Image loading library
- **`kotlinx.serialization`**: JSON serialization

## 🔧 Configuration

### Build Configuration (`build.gradle.kts`)
- **Compile SDK**: 36
- **Min SDK**: 29 (Android 10)
- **Target SDK**: 36
- **Java Version**: 11
- **Kotlin**: 2.0.21

### API Integration
- **Base URL**: `https://api.jikan.moe/v4/`
- **Rate Limiting**: Built-in handling for Jikan API limits (30 req/min, 3 req/sec)
- **Error Handling**: Comprehensive HTTP exception handling

## 🎨 UI/UX Features

### Theme Support
- Light and dark theme support
- Custom color schemes using Material Design 3
- Responsive design for various screen sizes

### Components Library
- **`AnimeCard`**: Display anime information with images
- **`SearchBar`**: Custom search functionality
- **`PrimaryButton` & `PrimaryTextField`**: Consistent input components
- **`ToggleSwitch`**: Theme switching capability
- **State Components**: Loading, error, and empty states

### Navigation
- Single Activity architecture
- Compose Navigation for screen transitions
- NavHost configuration in `Navigation.kt`

## 🔌 Network Layer

### HTTP Client Features
- **Timeout Configuration**: 15 seconds for connect/socket/request
- **Logging**: Full HTTP traffic logging
- **Content Negotiation**: JSON serialization with lenient parsing
- **Error Validation**: Status code validation and custom exceptions

### API Services
- **`JikanApi`**: Interface to Jikan API endpoints
    - `getTopAnime()`: Fetch top-rated anime
    - `searchAnime()`: Search anime by title

## 🔊 Audio Features
- **`SoundManager`**: System sound playback using Android MediaPlayer
- **Sound Types**: Click, Success, Error notifications
- **Volume Control**: Configurable volume levels
- **Resource Management**: Proper MediaPlayer lifecycle handling

## 📊 Module Dependencies Graph

```
                          ┌─────────────────┐
                          │   Presentation  │
                          │    (UI Layer)   │
                          └────────┬────────┘
                                   │ depends on
                                   ▼
                          ┌─────────────────┐
                          │     Domain      │
                          │  (Interfaces)   │
                          └────────┬────────┘
                                   │ implemented by
                                   ▼
                          ┌─────────────────┐
                          │      Data       │
                          │ (Implementations)│
                          └─────────────────┘
                                   │ uses
               ┌───────────────────┼───────────────────┐
               ▼                   ▼                   ▼
      ┌──────────────┐    ┌──────────────┐    ┌──────────────┐
      │   Ktor/      │    │   Android    │    │   Kotlinx    │
      │   Network    │    │    SDK       │    │ Serialization│
      └──────────────┘    └──────────────┘    └──────────────┘
```

## 🚀 Getting Started

### Prerequisites
- Android Studio Hedgehog or later
- JDK 11 or higher
- Android SDK 29+

### Building the Project
1. Clone the repository
2. Open in Android Studio
3. Sync Gradle dependencies
4. Build and run on emulator or device

### Configuration Notes
- No API keys required (uses public Jikan API)
- Internet permission is required
- Network state permission for connection monitoring

## 📱 Screens

### Splash Screen
- Initial loading screen
- App branding display
- Network connectivity check

### Anime List Screen
- Top anime listing with pagination
- Search functionality
- Anime details display
- Image loading with Coil
- Error handling and retry mechanisms

## 🧪 Testing
- **Unit Tests**: JUnit for business logic
- **UI Tests**: Espresso and Compose UI testing
- **Test Architecture**: Mockable dependencies via Koin

## 🔒 Permissions
- `INTERNET`: Required for API calls
- `ACCESS_NETWORK_STATE`: For connectivity monitoring

## 📄 License & Credits
- **Jikan API**: Unofficial MyAnimeList API
- **Icons**: Material Design icons
- **Architecture**: Based on Google's recommended app architecture

## 🐛 Troubleshooting

### Common Issues
1. **Rate Limiting**: App handles Jikan API rate limits with user feedback
2. **Network Errors**: Comprehensive error states and retry options
3. **Image Loading**: Fallback mechanisms for missing images

### Debugging
- HTTP logging enabled in debug builds
- Detailed exception handling with user-friendly messages
- Compose preview support for UI development

## 🔄 Future Enhancements
- Offline caching with Room database
- Favorite anime functionality
- Detailed anime information screens
- User authentication
- Push notifications for new episodes