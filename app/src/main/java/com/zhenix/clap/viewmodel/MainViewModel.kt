package com.zhenix.clap.viewmodel

import android.app.Application
import android.content.Context
import android.os.CountDownTimer
import android.os.Vibrator
import android.os.VibratorManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.zhenix.clap.audio.SoundManager
import com.zhenix.clap.detector.ClapDetector
import com.zhenix.clap.repository.AppRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * MainViewModel - Mengkelola logika aplikasi ZHENIX CLAP
 * 
 * Implementasi MVVM dengan StateFlow untuk reactive UI updates
 */
class MainViewModel(application: Application) : AndroidViewModel(application) {
    
    private val repository = AppRepository(application)
    private val vibrator = getVibratorService(application)
    
    // State untuk UI
    private val _status = MutableStateFlow("Idle")
    val status: StateFlow<String> = _status
    
    private val _timeRemaining = MutableStateFlow("00:00")
    val timeRemaining: StateFlow<String> = _timeRemaining
    
    private val _isListening = MutableStateFlow(false)
    val isListening: StateFlow<Boolean> = _isListening
    
    private val _selectedSound = MutableStateFlow(SoundManager.SoundType.CAT)
    val selectedSound: StateFlow<SoundManager.SoundType> = _selectedSound
    
    private val _sensitivity = MutableStateFlow(0.5f)
    val sensitivity: StateFlow<Float> = _sensitivity
    
    private val _isVibrationEnabled = MutableStateFlow(true)
    val isVibrationEnabled: StateFlow<Boolean> = _isVibrationEnabled
    
    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode
    
    private val _autoStart = MutableStateFlow(false)
    val autoStart: StateFlow<Boolean> = _autoStart
    
    private var countDownTimer: CountDownTimer? = null
    private val timerDuration = 60000L // 60 detik
    
    private var clapDetector: ClapDetector? = null
    
    init {
        initializeClapDetector()
    }
    
    /**
     * Dapatkan vibrator service
     */
    private fun getVibratorService(context: Context): Vibrator? {
        return try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
                val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                vibratorManager.defaultVibrator
            } else {
                @Suppress("DEPRECATION")
                context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            }
        } catch (e: Exception) {
            null
        }
    }
    
    /**
     * Inisialisasi ClapDetector
     */
    private fun initializeClapDetector() {
        clapDetector = ClapDetector(
            onClapDetected = { onClapDetected() }
        )
    }
    
    /**
     * Handler ketika clap terdeteksi
     */
    private fun onClapDetected() {
        viewModelScope.launch {
            _status.value = "Clap Detected!"
            
            // Vibrate jika enabled
            if (_isVibrationEnabled.value) {
                vibrate(500)
            }
            
            // Mulai sound
            repository.getSoundManager().playSound(_selectedSound.value, isLooping = true)
            _status.value = "Playing Sound..."
            
            // Nyalakan flashlight
            repository.getFlashlightManager().turnOn()
            
            // Reset dan mulai timer
            startTimer()
        }
    }
    
    /**
     * Mulai mendengarkan clap
     */
    fun startListening() {
        try {
            repository.startClapDetection()
            _isListening.value = true
            _status.value = "Listening..."
        } catch (e: Exception) {
            e.printStackTrace()
            _status.value = "Error: ${e.message}"
        }
    }
    
    /**
     * Berhenti mendengarkan
     */
    fun stopListening() {
        try {
            repository.stopClapDetection()
            _isListening.value = false
            stopSound()
            turnOffFlashlight()
            cancelTimer()
            _status.value = "Idle"
            _timeRemaining.value = "00:00"
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    /**
     * Mulai timer 60 detik
     */
    private fun startTimer() {
        cancelTimer()
        
        countDownTimer = object : CountDownTimer(timerDuration, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished / 1000
                val displayText = String.format("%02d:%02d", seconds / 60, seconds % 60)
                _timeRemaining.value = displayText
            }
            
            override fun onFinish() {
                _timeRemaining.value = "00:00"
                stopSound()
                turnOffFlashlight()
                _status.value = "Listening..."
            }
        }
        
        countDownTimer?.start()
    }
    
    /**
     * Hentikan timer
     */
    private fun cancelTimer() {
        countDownTimer?.cancel()
        countDownTimer = null
    }
    
    /**
     * Hentikan suara
     */
    fun stopSound() {
        try {
            repository.getSoundManager().stopSound()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    /**
     * Matikan flashlight
     */
    fun turnOffFlashlight() {
        try {
            repository.getFlashlightManager().turnOff()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    /**
     * Test suara yang dipilih
     */
    fun testSound() {
        viewModelScope.launch {
            try {
                repository.getSoundManager().stopSound()
                repository.getSoundManager().playSound(_selectedSound.value, isLooping = false)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    
    /**
     * Set sound yang dipilih
     */
    fun setSelectedSound(sound: SoundManager.SoundType) {
        _selectedSound.value = sound
    }
    
    /**
     * Set sensitivity
     */
    fun setSensitivity(level: Float) {
        _sensitivity.value = level
        repository.setClapSensitivity(level)
        clapDetector?.setSensitivity(level)
    }
    
    /**
     * Toggle vibration
     */
    fun setVibrationEnabled(enabled: Boolean) {
        _isVibrationEnabled.value = enabled
    }
    
    /**
     * Toggle dark mode
     */
    fun setDarkMode(enabled: Boolean) {
        _isDarkMode.value = enabled
    }
    
    /**
     * Set auto start
     */
    fun setAutoStart(enabled: Boolean) {
        _autoStart.value = enabled
    }
    
    /**
     * Vibrate
     */
    private fun vibrate(duration: Long) {
        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                vibrator?.vibrate(android.os.VibrationEffect.createOneShot(
                    duration,
                    android.os.VibrationEffect.DEFAULT_AMPLITUDE
                ))
            } else {
                @Suppress("DEPRECATION")
                vibrator?.vibrate(duration)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    override fun onCleared() {
        super.onCleared()
        repository.cleanup()
        cancelTimer()
    }
}
