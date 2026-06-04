# ZHENIX CLAP - Build & Setup Guide

## Prasyarat
- Android Studio 2023.1 atau lebih baru
- SDK Android 26 (Oreo) hingga 34 (Android 14)
- Gradle 8.0 atau lebih baru
- Kotlin 1.9.20

## Setup Langkah-Langkah

### 1. Clone Repository
```bash
git clone https://github.com/qinmu878-blip/zhenix-clap.git
cd zhenix-clap
```

### 2. Audio Files Setup
Letakkan file audio di folder `app/src/main/res/raw/`:
- `cat.mp3` - Suara kucing
- `chicken.mp3` - Suara ayam
- `police.mp3` - Sirene polisi
- `bell.mp3` - Suara bel
- `dog.mp3` - Suara anjing

### 3. Build & Run
```bash
# Build APK
./gradlew assembleDebug

# Install ke device
./gradlew installDebug

# Jalankan di emulator
./gradlew connectedAndroidTest
```

## Struktur Proyek

```
com.zhenix.clap/
├── MainActivity.kt              # Entry point dengan permission handling
├── viewmodel/
│   └── MainViewModel.kt         # MVVM logic dengan StateFlow
├── detector/
│   └── ClapDetector.kt          # Audio detection algorithm
├── audio/
│   └── SoundManager.kt          # MediaPlayer management
├── flashlight/
│   └── FlashlightManager.kt     # CameraManager integration
├── repository/
│   └── AppRepository.kt         # Repository pattern
├── ui/
│   ├── theme/Theme.kt           # Material 3 design system
│   ├── components/Components.kt # Reusable UI components
│   └── screen/MainScreen.kt     # Main Compose screen
└── utils/
    └── PermissionHelper.kt      # Permission utilities
```

## Fitur Utama

### 🎵 Clap Detection
- Real-time audio monitoring dengan AudioRecord
- Adaptive threshold algorithm
- Debounce 2 detik untuk false positive reduction
- Adjustable sensitivity (Low/Medium/High)

### 🔊 Sound System
- 5 pilihan suara (Cat, Chicken, Police, Bell, Dog)
- MediaPlayer dengan loop support
- Test sound tanpa clap trigger
- Automatic stop saat timer selesai

### 💡 Flashlight Control
- CameraManager-based torch control
- Auto-on saat clap terdeteksi
- Auto-off saat timer habis
- Safe handling untuk device tanpa flash

### ⏱️ Timer System
- 60-detik countdown timer
- Real-time MM:SS display
- Auto-reset saat clap terdeteksi lagi
- Automatic cleanup saat selesai

### 🎨 UI/UX
- Material 3 design system
- Dark mode support
- Responsive Jetpack Compose layout
- Real-time status updates

### ⚙️ Settings
- Sensitivity slider (Rendah/Sedang/Tinggi)
- Vibration toggle dengan haptic feedback
- Dark mode toggle
- Auto-start option

## Permissions

Aplikasi meminta permissions:
- `RECORD_AUDIO` - Akses mikrofon
- `CAMERA` - Kontrol flashlight
- `FLASHLIGHT` - Senter
- `VIBRATE` - Getaran haptic

## Architecture & Best Practices

- **MVVM Pattern** dengan ViewModel & StateFlow
- **Repository Pattern** untuk separation of concerns
- **Clean Architecture** dengan proper dependency injection
- **Coroutines** untuk async operations
- **No Memory Leaks** - Proper lifecycle management
- **No ANR** - Background operations di thread terpisah
- **Error Handling** di setiap module

## Build Variants

```gradle
// Debug variant untuk development
./gradlew assembleDebug

// Release variant untuk production
./gradlew assembleRelease

// Run unit tests
./gradlew test

// Run instrumented tests
./gradlew connectedAndroidTest
```

## Troubleshooting

### Permission Denied
- Pastikan device menjalankan Android 6.0 (API 23) atau lebih baru
- Grant permissions melalui Settings > Apps > ZHENIX CLAP

### Clap Not Detected
- Periksa microphone sensitivity di Settings
- Pastikan speaker volume device tidak dalam mode silent
- Test dengan TEST SOUND button terlebih dahulu

### Flashlight Not Working
- Pastikan device memiliki LED flash
- Periksa CAMERA permission di Settings
- Beberapa device memiliki batasan penggunaan flash

### Audio Not Playing
- Pastikan audio files berada di `app/src/main/res/raw/`
- Cek volume device (bukan vibrate mode)
- Restart aplikasi jika suara tidak terdengar

## Performance Optimization

- Memory footprint minimal (< 50MB)
- Battery efficient dengan sleep mode detection
- Optimized audio processing loop
- Garbage collection friendly coroutines

## Support & Compatibility

- Minimum SDK: Android 8.0 (API 26)
- Target SDK: Android 14 (API 34)
- Tested on Android 8 - 15
- Support untuk landscape & portrait orientations

## License

© 2026 ZHENIX CLAP - All rights reserved

## Contact

Developer: qinmu878-blip
Email: qinmu878@gmail.com

---

Untuk bantuan lebih lanjut atau pelaporan bug, silakan buat issue di repository ini.