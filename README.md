📅 Event Companion
Event Companion is an open-source Android app designed to help users scan, save, and browse event schedules. With QR code scanning, offline storage, and a clean Jetpack Compose UI, it makes event participation smarter and easier. The backend for this app can be found here https://github.com/muhammadmohsinnisar/ktor-event-companion

✨ Features
📷 QR Code Scanner
Scan QR codes containing event JSON URLs to load event details.

💾 Offline Event Storage
Events are saved locally using Room Database for offline access.

💬 Event and Session Details
View all sessions, speakers, and metadata associated with each scanned event.

❤️ Favorites
Mark sessions as favorite for quick reference.

🗑️ Delete Events
Remove unwanted events from your list with a confirmation dialog.

📱 Jetpack Compose UI
Modern and responsive UI built with Material3 and Jetpack Compose.

📸 Screenshots
- To be uploaded -
  
📦 Architecture
* MVVM with ViewModel and LiveData/StateFlow.
* Jetpack Compose for declarative UI.
* Room for local persistence.
* ZXing via ImageAnalysis.Analyzer for QR scanning.
* Kotlinx Serialization for parsing event JSON from URL.

🧪 Tech Stack
* Layer	Technology
* UI	Jetpack Compose, Material3
* ViewModel	LiveData, StateFlow
* Data Layer	Room, OkHttp, kotlinx.serialization
* QR Scanning	CameraX, ZXing
* Language	: Kotlin

🔗 QR Code Format
The app expects a QR code that contains a URL pointing to an event JSON in the following format:

```
{
  "id": 1,
  "name": "Dev Conference",
  "description": "An amazing developer conference.",
  "date": "2025-08-20",
  "location": "Berlin",
  "sessions": [
    {
      "id": 101,
      "title": "Intro to Jetpack Compose",
      "description": "Basics and beyond.",
      "startTime": "10:00",
      "endTime": "10:50",
      "eventId": 1,
      "speaker": {
        "id": 201,
        "name": "Jane Doe",
        "bio": "Android Engineer at X",
        "eventId": 1
      }
    }
  ]
}
```

🚀 Getting Started
Prerequisites
* Android Studio Flamingo or later
* Kotlin 1.9+
* Gradle 8.0+
* Android SDK 33+

Clone the repository
```
git clone https://github.com/your-username/event-companion.git
cd event-companion
```

Run the app
Open in Android Studio.

Connect a physical device or use an emulator with a working camera.

Run the MainActivity.

📂 Project Structure
```
com.mohsin.eventcompanion
├── data           // Local DB, DTOs, Repositories
├── domain         // Models
├── ui             // Screens & ViewModels
├── utils          // QR Analyzer
├── MainActivity   // Navigation & Drawer
🛡️ Permissions
```
The app requests:
```
<uses-permission android:name="android.permission.CAMERA"/>
```

🧩 Contribution
PRs and Issues are welcome! 🚀
If you have a new feature idea or bug fix, feel free to open a pull request.

📝 License
This project is licensed under the MIT License - see the LICENSE file for details.

🙌 Acknowledgements
* Jetpack Compose
* ZXing
* kotlinx.serialization
* CameraX








