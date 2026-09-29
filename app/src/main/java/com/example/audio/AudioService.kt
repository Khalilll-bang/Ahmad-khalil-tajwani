package com.example.audio

import android.content.Context
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.speech.tts.Voice
import com.example.data.seed.SeedLocations
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale

enum class VoiceGenderPreference(val labelAr: String, val labelId: String, val icon: String) {
    CONTEXT_AWARE("تلقائي حسب الشخصية", "Sesuai Konteks Karakter", "🎭"),
    FORCE_MALE("صوت رجالي دائماً", "Suara Pria Khas Arab", "🧔"),
    FORCE_FEMALE("صوت نسائي دائماً", "Suara Wanita Khas Arab", "🧕")
}

class AudioService(context: Context) : TextToSpeech.OnInitListener {
    private var tts: TextToSpeech? = TextToSpeech(context.applicationContext, this)
    private var isInitialized = false

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _currentSpeakerId = MutableStateFlow<String?>(null)
    val currentSpeakerId: StateFlow<String?> = _currentSpeakerId.asStateFlow()

    private val _playbackSpeed = MutableStateFlow(1.0f)
    val playbackSpeed: StateFlow<Float> = _playbackSpeed.asStateFlow()

    private val _isFemaleVoiceActive = MutableStateFlow(false)
    val isFemaleVoiceActive: StateFlow<Boolean> = _isFemaleVoiceActive.asStateFlow()

    private val _voicePreference = MutableStateFlow(VoiceGenderPreference.CONTEXT_AWARE)
    val voicePreference: StateFlow<VoiceGenderPreference> = _voicePreference.asStateFlow()

    private var arabicFemaleVoice: Voice? = null
    private var arabicMaleVoice: Voice? = null
    private var defaultArabicVoice: Voice? = null

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val arLocale = Locale.forLanguageTag("ar-SA").takeIf {
                tts?.isLanguageAvailable(it) == TextToSpeech.LANG_AVAILABLE ||
                        tts?.isLanguageAvailable(it) == TextToSpeech.LANG_COUNTRY_AVAILABLE
            } ?: Locale("ar")

            val result = tts?.setLanguage(arLocale)
            isInitialized = (result != TextToSpeech.LANG_MISSING_DATA && result != TextToSpeech.LANG_NOT_SUPPORTED)

            // Discover and catalog available Arabic voices for male vs female distinction
            try {
                val availableVoices = tts?.voices?.filter { v ->
                    val lang = v.locale.language.lowercase()
                    val iso3 = try { v.locale.isO3Language.lowercase() } catch (_: Exception) { "" }
                    lang == "ar" || iso3 == "ara"
                } ?: emptyList()

                // Female Arabic Voice Matchers (Google TTS and system TTS standard conventions)
                arabicFemaleVoice = availableVoices.firstOrNull { v ->
                    val name = v.name.lowercase()
                    name.contains("female") || name.contains("-f-") || name.contains("_f_") ||
                            name.contains("woman") || name.contains("ar-xa-x-arc-local") ||
                            name.contains("ar-xa-x-arc-network") || name.contains("ar-sa-x-arc") ||
                            (v.features != null && v.features.any { it.contains("female", ignoreCase = true) })
                }

                // Male Arabic Voice Matchers
                arabicMaleVoice = availableVoices.firstOrNull { v ->
                    val name = v.name.lowercase()
                    name.contains("male") || name.contains("-m-") || name.contains("_m_") ||
                            name.contains("man") || name.contains("ar-xa-x-ard-local") ||
                            name.contains("ar-xa-x-ard-network") || name.contains("ar-sa-x-ard") ||
                            (v.features != null && v.features.any { it.contains("male", ignoreCase = true) })
                }

                defaultArabicVoice = tts?.voice
            } catch (_: Exception) {}

            tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) {
                    _isPlaying.value = true
                }

                override fun onDone(utteranceId: String?) {
                    _isPlaying.value = false
                    _currentSpeakerId.value = null
                }

                @Deprecated("Deprecated in Java")
                override fun onError(utteranceId: String?) {
                    _isPlaying.value = false
                    _currentSpeakerId.value = null
                }
            })
        }
    }

    fun setVoicePreference(preference: VoiceGenderPreference) {
        _voicePreference.value = preference
    }

    fun setSpeed(speed: Float) {
        _playbackSpeed.value = speed
        tts?.setSpeechRate(speed)
    }

    /**
     * Speaks Arabic text with voice acoustics tailored specifically to gender context
     * (male vs female) and character persona.
     *
     * @param text The Arabic text to speak (full tashkeel preferred for accurate phonetics)
     * @param speakerId The ID of the speaker profile in SeedLocations
     * @param customSpeed Optional speed override (e.g. 0.75f for slow study)
     * @param overrideGender Optional explicit gender override ("MALE" or "FEMALE")
     */
    fun speak(
        text: String,
        speakerId: String = "teacher_male_01",
        customSpeed: Float? = null,
        overrideGender: String? = null
    ) {
        if (!isInitialized || text.isBlank()) return

        val profile = SeedLocations.speakers[speakerId] ?: SeedLocations.speakers["teacher_male_01"]!!

        // Determine effective gender based on user global preference or explicit parameter
        val effectiveIsFemale: Boolean = when {
            overrideGender != null -> overrideGender.equals("FEMALE", ignoreCase = true)
            _voicePreference.value == VoiceGenderPreference.FORCE_FEMALE -> true
            _voicePreference.value == VoiceGenderPreference.FORCE_MALE -> false
            else -> profile.gender.equals("FEMALE", ignoreCase = true)
        }

        _isFemaleVoiceActive.value = effectiveIsFemale
        _currentSpeakerId.value = speakerId

        val effectiveSpeed = customSpeed ?: _playbackSpeed.value

        // Select the appropriate TTS Voice engine if available on the system
        try {
            if (effectiveIsFemale && arabicFemaleVoice != null) {
                tts?.voice = arabicFemaleVoice
            } else if (!effectiveIsFemale && arabicMaleVoice != null) {
                tts?.voice = arabicMaleVoice
            } else if (defaultArabicVoice != null) {
                tts?.voice = defaultArabicVoice
            }
        } catch (_: Exception) {}

        // Apply distinct Arabic acoustic pitch and rate parameters:
        // Authentic Arabic Female articulation:
        // - Higher pitch (1.24f - 1.30f), precise fusha vowel articulation, energetic and warm tone.
        // Authentic Arabic Male articulation:
        // - Deeper pitch (0.84f - 0.90f), chest resonance, authoritative yet gentle pedagogical cadence.
        val targetPitch = if (effectiveIsFemale) {
            profile.pitch.coerceAtLeast(1.24f)
        } else {
            profile.pitch.coerceAtMost(0.88f)
        }

        val targetRate = effectiveSpeed * profile.speechRate

        tts?.setPitch(targetPitch)
        tts?.setSpeechRate(targetRate)

        val enrichedText = prepareArabicPhonetics(text)

        val params = Bundle().apply {
            putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "audio_${System.currentTimeMillis()}")
        }
        tts?.speak(enrichedText, TextToSpeech.QUEUE_FLUSH, params, "audio_session")
    }

    /**
     * Speaks text using explicit Female Arabic voice (الأستاذة فاطمة / الدكتورة مريم)
     */
    fun speakAsFemale(text: String, customSpeed: Float? = null) {
        speak(text, "teacher_female_01", customSpeed, overrideGender = "FEMALE")
    }

    /**
     * Speaks text using explicit Male Arabic voice (الأستاذ أحمد / الشيخ أبو فهد)
     */
    fun speakAsMale(text: String, customSpeed: Float? = null) {
        speak(text, "teacher_male_01", customSpeed, overrideGender = "MALE")
    }

    /**
     * Allows explicit gender override for practicing male vs female Arabic pronunciation
     */
    fun speakWithGenderOverride(text: String, isFemale: Boolean, customSpeed: Float? = null) {
        val speakerId = if (isFemale) "teacher_female_01" else "teacher_male_01"
        speak(text, speakerId, customSpeed, overrideGender = if (isFemale) "FEMALE" else "MALE")
    }

    /**
     * Ensures Arabic text is properly formatted for Arabic TTS makhraj articulation.
     * Normalizes pauses around Arabic punctuation (comma: '،', question: '؟')
     */
    private fun prepareArabicPhonetics(raw: String): String {
        return raw.trim()
            .replace("،", "، ")
            .replace("؟", "؟ ")
            .replace("  ", " ")
    }

    fun stop() {
        tts?.stop()
        _isPlaying.value = false
        _currentSpeakerId.value = null
    }

    fun shutdown() {
        tts?.stop()
        tts?.shutdown()
        tts = null
    }
}
