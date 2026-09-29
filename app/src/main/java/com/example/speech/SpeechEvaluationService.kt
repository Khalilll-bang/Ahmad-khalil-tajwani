package com.example.speech

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale
import kotlin.math.max

data class SpeechEvaluationResult(
    val transcript: String,
    val targetText: String,
    val pronunciationScore: Int,
    val fluencyScore: Int,
    val grammarScore: Int,
    val vocabularyScore: Int,
    val overallScore: Int,
    val isAcceptable: Boolean,
    val feedbackAr: String,
    val feedbackId: String
)

class SpeechEvaluationService(private val context: Context) {
    private var speechRecognizer: SpeechRecognizer? = null

    private val _isListening = MutableStateFlow(false)
    val isListening: StateFlow<Boolean> = _isListening.asStateFlow()

    private val _recognizedText = MutableStateFlow("")
    val recognizedText: StateFlow<String> = _recognizedText.asStateFlow()

    private val _isSpeechSupported = MutableStateFlow(SpeechRecognizer.isRecognitionAvailable(context))
    val isSpeechSupported: StateFlow<Boolean> = _isSpeechSupported.asStateFlow()

    fun startListening(onResult: (String) -> Unit, onError: (String) -> Unit) {
        if (!SpeechRecognizer.isRecognitionAvailable(context)) {
            onError("التعرف على الصوت غير مدعوم في هذا الجهاز، يرجى استخدام الكتابة المباشرة.")
            return
        }

        try {
            speechRecognizer?.destroy()
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context).apply {
                setRecognitionListener(object : RecognitionListener {
                    override fun onReadyForSpeech(params: Bundle?) {
                        _isListening.value = true
                    }

                    override fun onBeginningOfSpeech() {}
                    override fun onRmsChanged(rmsdB: Float) {}
                    override fun onBufferReceived(buffer: ByteArray?) {}
                    override fun onEndOfSpeech() {
                        _isListening.value = false
                    }

                    override fun onError(error: Int) {
                        _isListening.value = false
                        val errMsg = when (error) {
                            SpeechRecognizer.ERROR_NO_MATCH -> "لم يتم التعرف على الصوت بدقة، حاول مجددًا."
                            SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "انتهى الوقت دون سماع صوت، اضغط وتحدث بوضوح."
                            else -> "تعذر التقاط الصوت، استخدم خيار الكتابة أو بناء الجمل."
                        }
                        onError(errMsg)
                    }

                    override fun onResults(results: Bundle?) {
                        _isListening.value = false
                        val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                        val text = matches?.firstOrNull() ?: ""
                        _recognizedText.value = text
                        onResult(text)
                    }

                    override fun onPartialResults(partialResults: Bundle?) {}
                    override fun onEvent(eventType: Int, params: Bundle?) {}
                })
            }

            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ar")
                putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "ar")
                putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, "ar")
                putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, context.packageName)
            }
            speechRecognizer?.startListening(intent)
        } catch (e: Exception) {
            _isListening.value = false
            onError("خطأ في تشغيل الميكروفون: ${e.localizedMessage}")
        }
    }

    fun stopListening() {
        speechRecognizer?.stopListening()
        _isListening.value = false
    }

    fun evaluateSpeech(spokenText: String, expectedAnswer: String): SpeechEvaluationResult {
        val normSpoken = normalizeArabic(spokenText)
        val normExpected = normalizeArabic(expectedAnswer)

        if (normSpoken.isBlank()) {
            return SpeechEvaluationResult(
                transcript = spokenText,
                targetText = expectedAnswer,
                pronunciationScore = 0,
                fluencyScore = 0,
                grammarScore = 0,
                vocabularyScore = 0,
                overallScore = 0,
                isAcceptable = false,
                feedbackAr = "لم يتم التقاط أي كلام، يُرجى المحاولة بصوت أوضح.",
                feedbackId = "Tidak ada ucapan terdeteksi. Silakan coba bicara lebih jelas."
            )
        }

        // Similarity calculation (Levenshtein based)
        val distance = levenshteinDistance(normSpoken, normExpected)
        val maxLen = max(normSpoken.length, normExpected.length)
        val rawSim = if (maxLen > 0) ((maxLen - distance).toFloat() / maxLen) else 0f
        val similarityPercent = (rawSim * 100).toInt().coerceIn(0, 100)

        // Word overlap
        val spokenWords = normSpoken.split(" ").filter { it.isNotBlank() }.toSet()
        val expectedWords = normExpected.split(" ").filter { it.isNotBlank() }.toSet()
        val commonWords = spokenWords.intersect(expectedWords)
        val wordOverlap = if (expectedWords.isNotEmpty()) (commonWords.size.toFloat() / expectedWords.size) else 0f

        val vocabScore = ((wordOverlap * 70) + (rawSim * 30)).toInt().coerceIn(30, 100)
        val pronunciationScore = (similarityPercent * 0.95f + 5).toInt().coerceIn(25, 100)
        val grammarScore = if (spokenWords.containsAll(expectedWords)) 95 else (similarityPercent * 0.9f).toInt().coerceIn(30, 95)
        val fluencyScore = if (spokenWords.size >= expectedWords.size - 1) 90 else 70

        val overall = ((pronunciationScore * 0.35) + (vocabScore * 0.25) + (grammarScore * 0.20) + (fluencyScore * 0.20)).toInt()
        val isAcceptable = overall >= 60

        val (feedbackAr, feedbackId) = if (overall >= 85) {
            "ممتاز جداً! مخارج الحروف فصيحة والتعبير السياقي دقيق للغاية." to "Luar biasa! Pelafalan fashih dan makna sangat tepat sesuai konteks."
        } else if (overall >= 65) {
            "جيد جداً! الفكرة مفهومة، واصل التدريب لتحسين ضبط الحركات." to "Bagus sekali! Makna tersampaikan dengan baik, terus latih kelancaran."
        } else {
            "محاولة مقبولة، لكن يفضل إعادة التكرار لمطابقة الكلمات المطلوبة بدقة." to "Cukup baik, disarankan mengulang agar lebih sesuai dengan kalimat sasaran."
        }

        return SpeechEvaluationResult(
            transcript = spokenText,
            targetText = expectedAnswer,
            pronunciationScore = pronunciationScore,
            fluencyScore = fluencyScore,
            grammarScore = grammarScore,
            vocabularyScore = vocabScore,
            overallScore = overall,
            isAcceptable = isAcceptable,
            feedbackAr = feedbackAr,
            feedbackId = feedbackId
        )
    }

    private fun normalizeArabic(input: String): String {
        return input
            .replace("[\u064B-\u065F\u0670]".toRegex(), "") // remove Tashkeel
            .replace("[إأآا]".toRegex(), "ا")
            .replace("ى", "ي")
            .replace("ة", "ه")
            .replace("[،.؛!؟,\"?]".toRegex(), " ")
            .replace("\\s+".toRegex(), " ")
            .trim()
            .lowercase()
    }

    private fun levenshteinDistance(s1: String, s2: String): Int {
        val dp = Array(s1.length + 1) { IntArray(s2.length + 1) }
        for (i in 0..s1.length) dp[i][0] = i
        for (j in 0..s2.length) dp[0][j] = j
        for (i in 1..s1.length) {
            for (j in 1..s2.length) {
                val cost = if (s1[i - 1] == s2[j - 1]) 0 else 1
                dp[i][j] = minOf(
                    dp[i - 1][j] + 1,
                    dp[i][j - 1] + 1,
                    dp[i - 1][j - 1] + cost
                )
            }
        }
        return dp[s1.length][s2.length]
    }

    fun release() {
        speechRecognizer?.destroy()
        speechRecognizer = null
    }
}
