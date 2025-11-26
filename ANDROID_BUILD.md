# Building Android APK

This document explains how to build the Android APK for Kotlin Code Assistant.

## Important Note

The Android app module is commented out by default in `settings.gradle.kts` to allow the CLI tool to build without requiring Android SDK. Before building the Android app, you need to enable the Android module.

### Quick Enable (Linux/Mac)

```bash
./scripts/enable-android.sh
```

### Manual Enable

Edit `settings.gradle.kts` and uncomment the Android app module:
```kotlin
// Change this line:
// include(":app")

// To this:
include(":app")
```

### Disable Android Module (Optional)

To go back to CLI-only builds without Android SDK:
```bash
./scripts/disable-android.sh
```

## Prerequisites

1. **Android Studio** (Arctic Fox or later) or Android SDK command-line tools
2. **Java JDK 11** or higher
3. **Android SDK** with the following components:
   - Android SDK Platform 34
   - Android SDK Build-Tools 34.0.0 or higher
   - Android SDK Command-line Tools

## Option 1: Building with Android Studio (Recommended)

1. Install Android Studio from https://developer.android.com/studio

2. Open Android Studio and select "Open an Existing Project"

3. Navigate to the `kotlin-code-assistant` directory and click "OK"

4. Wait for Gradle to sync (this may take a few minutes on first run as it downloads dependencies)

5. Once synced, select `Build > Build Bundle(s) / APK(s) > Build APK(s)` from the menu

6. The APK will be generated at:
   ```
   app/build/outputs/apk/debug/app-debug.apk
   ```

7. Transfer the APK to your Android device and install it

## Option 2: Building from Command Line

1. Make sure you have Android SDK installed and `ANDROID_HOME` environment variable set:
   ```bash
   export ANDROID_HOME=/path/to/android/sdk
   export PATH=$PATH:$ANDROID_HOME/tools:$ANDROID_HOME/platform-tools
   ```

2. Navigate to the project directory:
   ```bash
   cd kotlin-code-assistant
   ```

3. Build the debug APK:
   ```bash
   ./gradlew :app:assembleDebug
   ```

4. The APK will be generated at:
   ```
   app/build/outputs/apk/debug/app-debug.apk
   ```

## Option 3: Building Release APK (Signed)

For a release build that can be uploaded to Google Play Store:

1. Create a keystore file:
   ```bash
   keytool -genkey -v -keystore my-release-key.keystore -alias my-key-alias -keyalg RSA -keysize 2048 -validity 10000
   ```

2. Create a `keystore.properties` file in the project root:
   ```properties
   storePassword=your-keystore-password
   keyPassword=your-key-password
   keyAlias=my-key-alias
   storeFile=/path/to/my-release-key.keystore
   ```

3. Build the release APK:
   ```bash
   ./gradlew :app:assembleRelease
   ```

4. The signed APK will be at:
   ```
   app/build/outputs/apk/release/app-release.apk
   ```

## Installing the APK

### On Physical Device

1. Enable "Unknown Sources" in device settings:
   - Settings > Security > Unknown Sources (Android 7.x and below)
   - Settings > Apps > Special Access > Install Unknown Apps (Android 8.0+)

2. Transfer the APK to your device via:
   - USB cable (copy to device storage)
   - Email attachment
   - Cloud storage (Google Drive, Dropbox, etc.)
   - ADB: `adb install app/build/outputs/apk/debug/app-debug.apk`

3. Tap the APK file on your device to install

### Using ADB

If you have ADB installed:
```bash
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

The `-r` flag replaces an existing installation if present.

## Troubleshooting

### Gradle Sync Failed
- Ensure you have a stable internet connection for downloading dependencies
- Check that your Android SDK is properly installed
- Try: `./gradlew clean` then rebuild
- If you encounter Kotlin/AGP version compatibility warnings, this is normal - the versions used are compatible

### Build Failed - SDK Not Found
- Set `ANDROID_HOME` environment variable
- Or create `local.properties` file in project root:
  ```properties
  sdk.dir=/path/to/android/sdk
  ```

### APK Won't Install
- Check that your device allows installation from unknown sources
- Ensure the APK architecture matches your device (the build creates a universal APK)
- Uninstall any previous version if upgrading

## Project Structure

```
kotlin-code-assistant/
├── app/                          # Android application module
│   ├── src/
│   │   └── main/
│   │       ├── java/com/kotlinassistant/android/
│   │       │   ├── MainActivity.kt          # Main UI activity
│   │       │   ├── models/
│   │       │   │   └── Models.kt            # Data models
│   │       │   └── analyzer/
│   │       │       └── CodeAnalyzer.kt      # Code analysis logic
│   │       ├── res/                          # Android resources
│   │       │   ├── layout/
│   │       │   │   └── activity_main.xml    # Main UI layout
│   │       │   ├── values/
│   │       │   │   ├── strings.xml
│   │       │   │   ├── colors.xml
│   │       │   │   └── themes.xml
│   │       │   └── mipmap-*/                # App icons
│   │       └── AndroidManifest.xml          # App manifest
│   └── build.gradle.kts                     # App build configuration
└── build.gradle.kts                         # Root build configuration

```

## Features

The Android app includes:
- ✅ Code input area for pasting Kotlin code
- ✅ One-tap analysis button
- ✅ Display of code suggestions with severity icons
- ✅ Code metrics (lines, functions, classes)
- ✅ Offline static analysis (no internet required)

## Future Enhancements

Potential improvements for the Android app:
- [ ] File picker to analyze files from device storage
- [ ] Syntax highlighting in code input
- [ ] Save and load code snippets
- [ ] Share analysis results
- [ ] Dark mode support
- [ ] AI-powered suggestions (would require adding OkHttp and Gson dependencies + API key configuration)
- [ ] Multiple language support beyond Kotlin
- [ ] Export analysis reports as PDF or text files
- [ ] Code snippet templates library

**Note**: The current implementation focuses on offline static analysis. AI features can be added by:
1. Adding OkHttp and Gson dependencies to `app/build.gradle.kts`
2. Implementing API key configuration UI
3. Porting the AI service integration from the CLI module
4. Adding appropriate ProGuard rules for network libraries

## Support

For issues or questions about building the Android app, please visit:
https://github.com/valleedgar085-rgb/kotlin-code-assistant/issues
