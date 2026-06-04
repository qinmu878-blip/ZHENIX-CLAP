# ZHENIX CLAP - Setup Instructions Lengkap

## 🎯 Tujuan
Aplikasi Android ZHENIX CLAP untuk menemukan HP dengan deteksi tepuk tangan, kontrol senter, pemutaran suara, dan timer otomatis.

## ✅ Fitur yang Sudah Diimplementasikan

### 1. **Clap Detection**
- ✓ Real-time audio monitoring dengan AudioRecord
- ✓ Adaptive threshold algorithm
- ✓ Debounce 2 detik untuk mengurangi false positives
- ✓ Sensitivity adjustable (Low/Medium/High)

### 2. **Sound Management**
- ✓ 5 pilihan suara (Cat, Chicken, Police, Bell, Dog)
- ✓ MediaPlayer dengan loop support
- ✓ Test sound button
- ✓ Auto-stop saat timer habis
- ✓ No audio overlap

### 3. **Flashlight Control**
- ✓ CameraManager untuk torch control
- ✓ Auto-on saat clap terdeteksi
- ✓ Auto-off saat timer habis atau STOP
- ✓ Safe handling untuk device tanpa flash

### 4. **Timer System**
- ✓ 60-detik CountDownTimer
- ✓ Real-time MM:SS countdown display
- ✓ Auto-reset saat clap terdeteksi lagi
- ✓ Automatic cleanup

### 5. **UI/UX**
- ✓ Material 3 design system
- ✓ Status display (Listening/Clap Detected/Playing Sound)
- ✓ Sound selection cards
- ✓ Settings panel dengan sliders & toggles
- ✓ Dark mode support

### 6. **Controls**
- ✓ START button - Mulai detection
- ✓ STOP button - Stop semua
- ✓ TEST SOUND button - Preview suara
- ✓ Sound selection
- ✓ Sensitivity slider
- ✓ Vibration toggle
- ✓ Dark mode toggle
- ✓ Auto start toggle

## 📁 File-File yang Tersedia

```
qinmu878-blip/zhenix-clap/
├── build.gradle.kts                              # Root gradle config
├── settings.gradle.kts                           # Settings gradle
├── app/
│   ├── build.gradle.kts                         # App gradle config
│   ├── proguard-rules.pro                       # ProGuard rules
│   ├── src/main/
│   │   ├── AndroidManifest.xml                  # Manifest lengkap
│   │   ├── java/com/zhenix/clap/
│   │   │   ├── MainActivity.kt                  # Entry point + permissions
│   │   │   ├── detector/
│   │   │   │   └── ClapDetector.kt             # Audio detection
│   │   │   ├── audio/
│   │   │   │   └── SoundManager.kt             # Sound management
│   │   │   ├── flashlight/
│   │   │   │   └── FlashlightManager.kt        # Flashlight control
│   │   │   ├── repository/
│   │   │   │   └── AppRepository.kt            # Repository pattern
│   │   │   ├── viewmodel/
│   │   │   │   └── MainViewModel.kt            # MVVM + StateFlow
│   │   │   ├── ui/
│   │   │   │   ├── theme/
│   │   │   │   │   └── Theme.kt                # Material 3 themes
│   │   │   │   ├── components/
│   │   │   │   │   └── Components.kt           # UI components
│   │   │   │   └── screen/
│   │   │   │       └── MainScreen.kt           # Main Compose UI
│   │   │   └── utils/
│   │   │       └── PermissionHelper.kt         # Permission utilities
│   │   ├── res/
│   │   │   ├── values/
│   │   │   │   ├── strings.xml                 # String resources
│   │   │   │   ├── colors.xml                  # Color definitions
│   │   │   │   └── themes.xml                  # Theme styles
│   │   │   ├── xml/
│   │   │   │   ├── preferences.xml             # Settings
│   │   │   │   ├── backup_schemes.xml          # Backup config
│   │   │   │   └── data_extraction_rules.xml   # Data extraction
│   │   │   ├── mipmap/                         # App icons
│   │   │   └── raw/                            # Audio files (tambahkan)
│   │   │       ├── cat.mp3
│   │   │       ├── chicken.mp3
│   │   │       ├── police.mp3
│   │   │       ├── bell.mp3
│   │   │       └── dog.mp3
├── README.md                                    # Documentation
├── SETUP_INSTRUCTIONS.md                        # File ini
└── .gitignore                                   # Git ignore rules
```

## 🚀 Cara Setup & Build

### Step 1: Clone Repository
```bash
git clone https://github.com/qinmu878-blip/zhenix-clap.git
cd zhenix-clap
```

### Step 2: Tambahkan Audio Files

Buat folder jika belum ada:
```bash
mkdir -p app/src/main/res/raw
```

Letakkan 5 file audio MP3:
- `app/src/main/res/raw/cat.mp3` (Suara kucing "meow")
- `app/src/main/res/raw/chicken.mp3` (Suara ayam "cluck")
- `app/src/main/res/raw/police.mp3` (Sirene polisi)
- `app/src/main/res/raw/bell.mp3` (Suara bel "ring")
- `app/src/main/res/raw/dog.mp3` (Suara anjing "woof")

### Step 3: Open di Android Studio

1. Buka Android Studio
2. File > Open > pilih folder `zhenix-clap`
3. Tunggu Gradle sync selesai
4. Pastikan tidak ada error di tab "Problems"

### Step 4: Setup Device/Emulator

**Untuk Physical Device:**
- Connect device via USB
- Enable USB Debugging di Settings > Developer Options
- Buka terminal dan run: `adb devices`

**Untuk Emulator:**
- Android Studio > Device Manager > Create Device
- Pilih API 26-34
- Start emulator

### Step 5: Build & Run

```bash
# Cara 1: Via Android Studio
# Tekan Shift + F10 atau klik Run button

# Cara 2: Via terminal
./gradlew assembleDebug        # Build APK
./gradlew installDebug         # Install ke device
```

## 🎮 Cara Menggunakan Aplikasi

### Startup
1. Buka aplikasi ZHENIX CLAP
2. Grant permissions saat diminta:
   - RECORD_AUDIO (Mikrofon)
   - CAMERA (Flashlight)
   - VIBRATE (Haptic feedback)

### Basic Usage

**1. Pilih Suara**
- Tap salah satu dari 5 kartu suara
- Selected sound ditampilkan dengan highlight

**2. Test Suara (Optional)**
- Tap "TEST SOUND" button
- Dengarkan preview suara pilihan
- Tanpa perlu menepuk tangan

**3. Mulai Detection**
- Tap "START" button
- Status akan berubah menjadi "Listening..."
- Flashlight akan tetap mati di awal

**4. Tepuk Tangan**
- Tepuk tangan di dekat mikrofon
- Status berubah "Clap Detected!"
- Suara diputar otomatis
- Flashlight ON
- Timer mulai menghitung mundur 60 detik

**5. Timer Countdown**
- Lihat MM:SS countdown di layar
- Tepuk lagi untuk RESET timer (kembali ke 60 detik)
- Suara dan flashlight tetap ON

**6. Auto-Stop**
- Saat timer mencapai 00:00
- Suara BERHENTI otomatis
- Flashlight MATI otomatis
- Status kembali ke "Listening..."
- Timer RESET ke "00:00"

**7. Stop Manual**
- Tap "STOP" button kapan saja
- Semua activity langsung berhenti
- Suara stop
- Flashlight off
- Timer reset
- Status kembali "Idle"

### Settings Panel

**Sensitivity Slider**
- Low (0.3) = Kurang sensitif, perlu tepuk keras
- Medium (0.5) = Default, balanced
- High (0.7) = Sangat sensitif, tepuk ringan sudah terdeteksi

**Vibration Toggle**
- ON = Getaran 500ms saat clap terdeteksi
- OFF = No vibration

**Dark Mode Toggle**
- ON = Dark theme
- OFF = Light theme

**Auto Start Toggle**
- ON = Detection mulai otomatis saat app dibuka
- OFF = Perlu tap START button

## 🔧 Troubleshooting

### Error: "Failed to resolve dependency"
**Solusi:**
```bash
./gradlew clean
./gradlew sync
```

### Permission Denied at Runtime
**Solusi:**
- Uninstall app: `adb uninstall com.zhenix.clap`
- Rebuild & reinstall
- Manual grant di Settings > Apps > ZHENIX CLAP > Permissions

### Clap Not Detected
**Solusi:**
1. Cek volume (tidak dalam vibrate mode)
2. Increase sensitivity ke "High"
3. Tepuk lebih dekat ke mikrofon
4. Test dengan TEST SOUND button
5. Check microphone di device settings

### Flashlight Not Working
**Solusi:**
1. Pastikan device punya LED flash
2. Grant CAMERA permission
3. Restart aplikasi
4. Cek Settings > Camera > Flashlight permissions

### Audio Not Playing
**Solusi:**
1. Cek audio files di `app/src/main/res/raw/`
2. Cek volume device
3. Test dengan TEST SOUND button
4. Rebuild: `./gradlew clean assembleDebug`

### App Crashes
**Solusi:**
1. Check Logcat: View > Tool Windows > Logcat
2. Look for "Exception" atau "Error"
3. Cek permissions di AndroidManifest.xml
4. Run: `./gradlew assembleDebug --info`

## 📊 Architecture Overview

```
MVVM Pattern
├── View (Jetpack Compose)
│   └── MainScreen.kt
├── ViewModel (StateFlow)
│   └── MainViewModel.kt
├── Repository Pattern
│   └── AppRepository.kt
└── Data Layer
    ├── ClapDetector.kt (AudioRecord)
    ├── SoundManager.kt (MediaPlayer)
    └── FlashlightManager.kt (CameraManager)
```

## 📱 Device Requirements

✅ **Minimum:**
- Android 8.0 (API 26)
- 50MB RAM
- Microphone

✅ **Recommended:**
- Android 10+ (API 29+)
- 100MB+ RAM
- Microphone + Flash LED

✅ **Optional:**
- Dark mode support (all Android 10+)
- Vibration motor

## 🚢 Build APK untuk Release

```bash
# Generate signed APK
./gradlew assembleRelease

# Output: app/build/outputs/apk/release/app-release.apk
```

## 📝 Code Quality

- ✓ KDoc documentation di semua files
- ✓ Error handling comprehensive
- ✓ Resource cleanup proper
- ✓ Null safety dengan Kotlin
- ✓ No deprecated APIs
- ✓ Coroutine-safe operations
- ✓ Memory leak prevention

## 🎓 Learning Resources

- [Jetpack Compose Docs](https://developer.android.com/jetpack/compose)
- [Material Design 3](https://m3.material.io/)
- [AudioRecord API](https://developer.android.com/reference/android/media/AudioRecord)
- [CameraManager API](https://developer.android.com/reference/android/hardware/camera2/CameraManager)
- [ViewModel & StateFlow](https://developer.android.com/topic/architecture/ui-layer)

## 📞 Support

Jika ada pertanyaan atau issue:
1. Check Logcat untuk error details
2. Baca README.md untuk documentation
3. Create issue di GitHub repository
4. Contact: qinmu878@gmail.com

---

**ZHENIX CLAP siap digunakan!** 🎉

Happy coding! 🚀