# LocalStream Pro — Android Personal NAS & Media Server

Production Android application written in Kotlin & Jetpack Compose.

## How to Build in Android Studio:
1. Open Android Studio (Hedgehog, Iguana, or Jellyfish).
2. Choose "Open" and select this `LocalStreamPro` folder.
3. Allow Gradle to sync.
4. Click **Build > Build Bundle(s) / APK(s) > Build APK(s)**.
5. The installable `app-debug.apk` will be in `app/build/outputs/apk/debug/app-debug.apk`!

## Automated Cloud Build:
Push this folder to any GitHub repository. The included `.github/workflows/build-apk.yml` automatically compiles the APK and attaches it to the GitHub Actions artifacts!
