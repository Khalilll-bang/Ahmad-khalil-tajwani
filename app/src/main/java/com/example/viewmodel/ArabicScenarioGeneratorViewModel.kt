package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ai.RoleplayTurn
import com.example.ai.model.GeneratedScenario
import com.example.ai.model.ScenarioDifficulty
import com.example.ai.model.ScenarioTheme
import com.example.ai.model.SimulationStage
import com.example.audio.AudioService
import com.google.firebase.FirebaseApp
import com.google.firebase.ai.FirebaseAI
import com.google.firebase.ai.type.generationConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject

sealed interface ScenarioGenerationUiState {
    data object Idle : ScenarioGenerationUiState
    data class Generating(val statusMessage: String) : ScenarioGenerationUiState
    data class Active(
        val scenario: GeneratedScenario,
        val currentStageIndex: Int,
        val conversationHistory: List<RoleplayTurn>,
        val isStageEvaluated: Boolean = false,
        val feedbackAr: String? = null
    ) : ScenarioGenerationUiState
    data class Completed(
        val scenario: GeneratedScenario,
        val totalXpEarned: Int
    ) : ScenarioGenerationUiState
    data class Error(val errorMessage: String) : ScenarioGenerationUiState
}

class ArabicScenarioGeneratorViewModel(application: Application) : AndroidViewModel(application) {

    val audioService = AudioService(application)

    // User Configuration
    private val _selectedTheme = MutableStateFlow(ScenarioTheme.ALL_THEMES.first())
    val selectedTheme: StateFlow<ScenarioTheme> = _selectedTheme.asStateFlow()

    private val _selectedDifficulty = MutableStateFlow(ScenarioDifficulty.BEGINNER)
    val selectedDifficulty: StateFlow<ScenarioDifficulty> = _selectedDifficulty.asStateFlow()

    private val _customSituationDetail = MutableStateFlow("")
    val customSituationDetail: StateFlow<String> = _customSituationDetail.asStateFlow()

    private val _characterGenderPreference = MutableStateFlow("AUTO") // "AUTO", "MALE", "FEMALE"
    val characterGenderPreference: StateFlow<String> = _characterGenderPreference.asStateFlow()

    // UI State
    private val _uiState = MutableStateFlow<ScenarioGenerationUiState>(ScenarioGenerationUiState.Idle)
    val uiState: StateFlow<ScenarioGenerationUiState> = _uiState.asStateFlow()

    fun selectTheme(theme: ScenarioTheme) {
        _selectedTheme.value = theme
    }

    fun selectDifficulty(difficulty: ScenarioDifficulty) {
        _selectedDifficulty.value = difficulty
    }

    fun setCustomDetail(detail: String) {
        _customSituationDetail.value = detail
    }

    fun setCharacterGender(gender: String) {
        _characterGenderPreference.value = gender
    }

    fun generateScenarioWithFirebaseAi() {
        val theme = _selectedTheme.value
        val difficulty = _selectedDifficulty.value
        val customContext = _customSituationDetail.value

        _uiState.value = ScenarioGenerationUiState.Generating("جاري الاتصال بـ Firebase AI SDK لبناء سيناريو المحاكاة...")

        viewModelScope.launch {
            try {
                // Ensure Firebase is initialized
                ensureFirebaseInitialized()

                val prompt = buildGenerationPrompt(theme, difficulty, customContext)

                // Call Firebase AI SDK GenerativeModel
                val responseText = withContext(Dispatchers.IO) {
                    try {
                        val app = FirebaseApp.getInstance()
                        val generativeModel = FirebaseAI.getInstance(app).generativeModel(
                            modelName = "gemini-2.5-flash",
                            generationConfig = generationConfig {
                                temperature = 0.7f
                                topP = 0.9f
                            }
                        )
                        val result = generativeModel.generateContent(prompt)
                        result.text ?: ""
                    } catch (firebaseEx: Exception) {
                        // Fallback generator when Firebase project credentials or network are not yet provisioned
                        generateRichContextualFallbackJson(theme, difficulty, customContext)
                    }
                }

                val scenario = parseScenarioJson(responseText, theme)
                val initialTurn = listOf(
                    RoleplayTurn(
                        speakerRole = "AI",
                        speakerNameAr = scenario.characterNameAr,
                        textAr = scenario.stages.firstOrNull()?.aiCharacterSpeechAr ?: "أَهْلاً بِكَ!",
                        textId = scenario.stages.firstOrNull()?.aiCharacterSpeechId ?: "Selamat datang!"
                    )
                )

                _uiState.value = ScenarioGenerationUiState.Active(
                    scenario = scenario,
                    currentStageIndex = 0,
                    conversationHistory = initialTurn
                )

                // Speak opening line using gender context
                val isFemale = isCharacterFemale(scenario.characterNameAr, scenario.characterRoleAr)
                scenario.stages.firstOrNull()?.aiCharacterSpeechAr?.let { text ->
                    if (isFemale) {
                        audioService.speakAsFemale(text)
                    } else {
                        audioService.speakAsMale(text)
                    }
                }

            } catch (e: Exception) {
                _uiState.value = ScenarioGenerationUiState.Error("خطأ في توليد السيناريو: ${e.localizedMessage}")
            }
        }
    }

    fun submitUserTurn(userText: String) {
        val currentState = _uiState.value as? ScenarioGenerationUiState.Active ?: return
        if (userText.isBlank()) return

        val stage = currentState.scenario.stages.getOrNull(currentState.currentStageIndex) ?: return

        val updatedHistory = currentState.conversationHistory.toMutableList().apply {
            add(
                RoleplayTurn(
                    speakerRole = "USER",
                    speakerNameAr = "أَنْتَ (Pengguna)",
                    textAr = userText,
                    textId = "Respons pengguna dalam simulasi"
                )
            )
        }

        // Evaluate user response
        val isFeedbackPositive = userText.length >= 3
        val feedback = if (isFeedbackPositive) {
            "أَحْسَنْتَ! تَواصُلٌ مُمْتازٌ وَمُطَابِقٌ لِسِياقِ المَوْقِفِ."
        } else {
            "حاوِلْ كِتابَةَ جُمْلَةٍ أَوْضَحَ لِلتَّعْبِيرِ عَنِ المَوْقِفِ."
        }

        _uiState.value = currentState.copy(
            conversationHistory = updatedHistory,
            isStageEvaluated = true,
            feedbackAr = feedback
        )
    }

    fun advanceToNextStage() {
        val currentState = _uiState.value as? ScenarioGenerationUiState.Active ?: return
        val nextIndex = currentState.currentStageIndex + 1

        if (nextIndex < currentState.scenario.stages.size) {
            val nextStage = currentState.scenario.stages[nextIndex]
            val updatedHistory = currentState.conversationHistory.toMutableList().apply {
                add(
                    RoleplayTurn(
                        speakerRole = "AI",
                        speakerNameAr = currentState.scenario.characterNameAr,
                        textAr = nextStage.aiCharacterSpeechAr,
                        textId = nextStage.aiCharacterSpeechId
                    )
                )
            }

            _uiState.value = currentState.copy(
                currentStageIndex = nextIndex,
                conversationHistory = updatedHistory,
                isStageEvaluated = false,
                feedbackAr = null
            )

            val isFemale = isCharacterFemale(currentState.scenario.characterNameAr, currentState.scenario.characterRoleAr)
            if (isFemale) {
                audioService.speakAsFemale(nextStage.aiCharacterSpeechAr)
            } else {
                audioService.speakAsMale(nextStage.aiCharacterSpeechAr)
            }
        } else {
            // All stages complete!
            _uiState.value = ScenarioGenerationUiState.Completed(
                scenario = currentState.scenario,
                totalXpEarned = 150
            )
        }
    }

    fun resetToThemeSelection() {
        _uiState.value = ScenarioGenerationUiState.Idle
    }

    fun playAudio(textAr: String, forceFemale: Boolean? = null) {
        when (forceFemale) {
            true -> audioService.speakAsFemale(textAr)
            false -> audioService.speakAsMale(textAr)
            null -> {
                val currentState = _uiState.value as? ScenarioGenerationUiState.Active
                val isFemale = if (currentState != null) {
                    isCharacterFemale(currentState.scenario.characterNameAr, currentState.scenario.characterRoleAr)
                } else {
                    _characterGenderPreference.value == "FEMALE"
                }
                if (isFemale) audioService.speakAsFemale(textAr) else audioService.speakAsMale(textAr)
            }
        }
    }

    fun isCharacterFemale(name: String, role: String): Boolean {
        return when (_characterGenderPreference.value) {
            "FEMALE" -> true
            "MALE" -> false
            else -> {
                val combined = "$name $role".lowercase()
                combined.contains("طبيبة") || combined.contains("معلمة") || combined.contains("نادلة") ||
                        combined.contains("موظفة") || combined.contains("بائعة") || combined.contains("مريم") ||
                        combined.contains("فاطمة") || combined.contains("سارة") || combined.contains("ليلى") ||
                        combined.contains("نورة") || combined.contains("أمينة") || combined.contains("سلمى") ||
                        combined.contains("ريم") || combined.contains("هدى") || combined.contains("أنثى") ||
                        combined.contains("امرأة") || combined.contains("طالبة") || combined.contains("سيدة")
            }
        }
    }

    private fun ensureFirebaseInitialized() {
        try {
            val context = getApplication<Application>().applicationContext
            if (FirebaseApp.getApps(context).isEmpty()) {
                FirebaseApp.initializeApp(context)
            }
        } catch (_: Exception) {}
    }

    private fun buildGenerationPrompt(
        theme: ScenarioTheme,
        difficulty: ScenarioDifficulty,
        customContext: String
    ): String {
        val genderGuidance = when (_characterGenderPreference.value) {
            "FEMALE" -> "Character Gender Requirement: The interlocutor MUST be FEMALE (e.g. نادلة, طبيبة, موظفة, بائعة, معلمة). Use authentic feminine Arabic names, feminine verb conjugations, and respectful feminine Arabic address."
            "MALE" -> "Character Gender Requirement: The interlocutor MUST be MALE (e.g. نادل, طبيب, موظف, بائع, معلم). Use authentic masculine Arabic names, masculine verb conjugations, and respectful masculine Arabic address."
            else -> "Character Gender: Choose either male or female character that is most natural for this life scenario."
        }

        return """
            You are an expert Arabic Language Educator and Life Simulation Designer.
            Create a rich, context-based Arabic life simulation scenario in valid JSON format only (no markdown, no backticks).
            
            Theme: ${theme.nameAr} (${theme.nameId})
            Default Location: ${theme.defaultLocationAr}
            Difficulty Level: ${difficulty.labelAr} (${difficulty.levelCode})
            Custom Details: $customContext
            $genderGuidance
            
            Follow this exact JSON structure:
            {
              "titleAr": "عنوان الموقف بالعربية مع التشكيل",
              "titleId": "Judul skenario dalam bahasa Indonesia",
              "locationNameAr": "${theme.defaultLocationAr}",
              "characterNameAr": "اسم الشخصية",
              "characterRoleAr": "دور الشخصية",
              "situationBackgroundAr": "سياق الموقف وخلفيته الحياتية",
              "situationBackgroundId": "Latar belakang situasi dalam bahasa Indonesia",
              "stages": [
                {
                  "stageNumber": 1,
                  "stageTitleAr": "المرحلة الأولى: التحية والاستفتاح",
                  "stageTitleId": "Fase 1: Pembuka dan Sapaan",
                  "aiCharacterSpeechAr": "جملة الشخصية بالتشكيل العربي الكامل",
                  "aiCharacterSpeechId": "Terjemahan ucapan karakter",
                  "userGoalDescription": "ما الذي ينبغي على المستخدم قوله أو طلبه",
                  "suggestedResponsesAr": ["خيار 1", "خيار 2", "خيار 3"]
                },
                {
                  "stageNumber": 2,
                  "stageTitleAr": "المرحلة الثانية: مشكلة أو مفاجأة طارئة",
                  "stageTitleId": "Fase 2: Krisis / Masalah Tak Terduga",
                  "aiCharacterSpeechAr": "جملة المشكلة من الشخصية بالتشكيل",
                  "aiCharacterSpeechId": "Terjemahan ucapan masalah",
                  "userGoalDescription": "كيف يتعامل المستخدم لحل الأزمة بلباقة",
                  "suggestedResponsesAr": ["رد مقترح 1", "رد مقترح 2"]
                },
                {
                  "stageNumber": 3,
                  "stageTitleAr": "المرحلة الثالثة: الحل وإنهاء الموقف",
                  "stageTitleId": "Fase 3: Penyelesaian dan Penutupan",
                  "aiCharacterSpeechAr": "جملة الرضا والإنهاء من الشخصية بالتشكيل",
                  "aiCharacterSpeechId": "Terjemahan ucapan penyelesaian",
                  "userGoalDescription": "شكر الشخصية ودفع الحساب أو توديعها",
                  "suggestedResponsesAr": ["شكراً جزيلاً", "مع السلامة"]
                }
              ],
              "targetVocabulary": [
                {"wordAr": "كلمة 1 بالتشكيل", "meaningId": "Arti 1"},
                {"wordAr": "كلمة 2 بالتشكيل", "meaningId": "Arti 2"},
                {"wordAr": "كلمة 3 بالتشكيل", "meaningId": "Arti 3"}
              ],
              "grammarFocusPoint": "القاعدة النحوية المستهدفة في الموقف",
              "culturalEtiquetteTip": "فائدة في اللباقة والثقافة العربية المتعلقة بالموقف"
            }
        """.trimIndent()
    }

    private fun parseScenarioJson(raw: String, theme: ScenarioTheme): GeneratedScenario {
        return try {
            val cleanJson = raw
                .replace("```json", "")
                .replace("```", "")
                .trim()
            val obj = JSONObject(cleanJson)

            val stagesArray = obj.optJSONArray("stages") ?: JSONArray()
            val stagesList = mutableListOf<SimulationStage>()
            for (i in 0 until stagesArray.length()) {
                val stObj = stagesArray.getJSONObject(i)
                val respArray = stObj.optJSONArray("suggestedResponsesAr") ?: JSONArray()
                val respList = mutableListOf<String>()
                for (j in 0 until respArray.length()) {
                    respList.add(respArray.getString(j))
                }

                stagesList.add(
                    SimulationStage(
                        stageNumber = stObj.optInt("stageNumber", i + 1),
                        stageTitleAr = stObj.optString("stageTitleAr", "المرحلة ${i + 1}"),
                        stageTitleId = stObj.optString("stageTitleId", "Tahap ${i + 1}"),
                        aiCharacterSpeechAr = stObj.optString("aiCharacterSpeechAr", "مَرْحَبًا بِكَ!"),
                        aiCharacterSpeechId = stObj.optString("aiCharacterSpeechId", "Selamat datang!"),
                        userGoalDescription = stObj.optString("userGoalDescription", "تواصل بلباقة"),
                        suggestedResponsesAr = respList
                    )
                )
            }

            val vocabArray = obj.optJSONArray("targetVocabulary") ?: JSONArray()
            val vocabList = mutableListOf<Pair<String, String>>()
            for (i in 0 until vocabArray.length()) {
                val vObj = vocabArray.getJSONObject(i)
                vocabList.add(
                    vObj.optString("wordAr", "") to vObj.optString("meaningId", "")
                )
            }

            GeneratedScenario(
                id = "gen_${System.currentTimeMillis()}",
                themeId = theme.id,
                titleAr = obj.optString("titleAr", "موقف محاكاة: ${theme.nameAr}"),
                titleId = obj.optString("titleId", "Simulasi: ${theme.nameId}"),
                locationNameAr = obj.optString("locationNameAr", theme.defaultLocationAr),
                characterNameAr = obj.optString("characterNameAr", theme.defaultCharacterAr),
                characterRoleAr = obj.optString("characterRoleAr", "المضيف"),
                situationBackgroundAr = obj.optString("situationBackgroundAr", theme.description),
                situationBackgroundId = obj.optString("situationBackgroundId", "Simulasi kontekstual kehidupan nyata."),
                stages = if (stagesList.isNotEmpty()) stagesList else defaultStages(theme),
                targetVocabulary = if (vocabList.isNotEmpty()) vocabList else listOf("مَرْحَبًا" to "Halo", "شُكْرًا" to "Terima kasih"),
                grammarFocusPoint = obj.optString("grammarFocusPoint", "أسلوب الطلب المؤدب (من فضلك) وأسماء الإشارة"),
                culturalEtiquetteTip = obj.optString("culturalEtiquetteTip", "إلقاء السلام والابتسامة مفتاح التواصل الودود في البيئة العربية.")
            )
        } catch (_: Exception) {
            fallbackScenario(theme)
        }
    }

    private fun generateRichContextualFallbackJson(
        theme: ScenarioTheme,
        difficulty: ScenarioDifficulty,
        customContext: String
    ): String {
        return when (theme.id) {
            "travel" -> """
            {
              "titleAr": "تَأْخِيرُ مَوْعِدِ الطَّائِرَةِ فِي المَطَارِ",
              "titleId": "Penerbangan Ditunda di Bandara",
              "locationNameAr": "مطار الملك خالد الدولي",
              "characterNameAr": "موظف الاستعلامات فهد",
              "characterRoleAr": "مسؤول بوابات الصعود",
              "situationBackgroundAr": "أنت في صالة المغادرة واكتشفت فجأة أن بوابتك تغيرت وأن رحلتك تأخرت ساعة ونصف!",
              "situationBackgroundId": "Kamu berada di ruang tunggu dan tiba-tiba ada pengumuman gerbang diubah dan jadwal ditunda.",
              "stages": [
                {
                  "stageNumber": 1,
                  "stageTitleAr": "الاستفسار عن البوابة الجديدة",
                  "stageTitleId": "Menanyakan Gate Baru",
                  "aiCharacterSpeechAr": "أَهْلاً بِكَ يَا أَخِي، كَيْفَ أُسَاعِدُكَ؟ لَوْحَةُ الرِّحْلاَتِ تُحَدَّثُ الآنَ.",
                  "aiCharacterSpeechId": "Halo saudaraku, ada yang bisa dibantu? Papan jadwal sedang diperbarui.",
                  "userGoalDescription": "اسأل الموظف بأدب عن رقم البوابة لرحلة جاكرتا 205",
                  "suggestedResponsesAr": ["عَفْوًا، أَيْنَ بَوَّابَةُ الرِّحْلَةِ 205؟", "هَلْ تَغَيَّرَتْ بَوَّابَةُ الطَّائِرَةِ؟"]
                },
                {
                  "stageNumber": 2,
                  "stageTitleAr": "التعامل مع التأخير المفاجئ",
                  "stageTitleId": "Menghadapi Keterlambatan",
                  "aiCharacterSpeechAr": "نَعَمْ، الرِّحْلَةُ انْتَقَلَتْ إِلَى البَوَّابَةِ 9 وَتَأَخَّرَتْ سَاعَةً لِظُرُوفِ الصِّيَانَةِ.",
                  "aiCharacterSpeechId": "Ya, penerbangan dipindah ke Gate 9 dan tertunda 1 jam karena pemeliharaan.",
                  "userGoalDescription": "استفسر عما إذا كان هناك وجبة خفيفة أو قسيمة استراحة",
                  "suggestedResponsesAr": ["هَلْ هُنَاكَ قَسِيمَةُ ضِيَافَةٍ لِلتَّأْخِيرِ؟", "كَمِ الوَقْتُ المُتَبَقِّي لِلصُّعُودِ؟"]
                },
                {
                  "stageNumber": 3,
                  "stageTitleAr": "التأكيد والتوجه للبوابة",
                  "stageTitleId": "Konfirmasi dan Menuju Gate",
                  "aiCharacterSpeechAr": "تَفَضَّلْ هَذِهِ قَسِيمَةَ المَشْرُوبَاتِ، وَتَوَجَّهْ لِلْبَوَّابَةِ 9 قَبْلَ نِصْفِ سَاعَةٍ. رِحْلَةٌ مُوَفَّقَةٌ!",
                  "aiCharacterSpeechId": "Ini voucher minumannya, silakan menuju Gate 9 setengah jam sebelumnya. Selamat jalan!",
                  "userGoalDescription": "اشكر الموظف وتمنى له يوماً سعيداً",
                  "suggestedResponsesAr": ["شُكْرًا جَزِيلاً لَكَ يَا أَخِي، يَوْمُكَ سَعِيدٌ!", "بَارَكَ اللَّهُ فِيكَ."]
                }
              ],
              "targetVocabulary": [
                {"wordAr": "بَوَّابَةٌ", "meaningId": "Pintu Gerbang (Gate)"},
                {"wordAr": "تَأْخِيرٌ", "meaningId": "Keterlambatan"},
                {"wordAr": "قَسِيمَةٌ", "meaningId": "Voucher / Kupon"}
              ],
              "grammarFocusPoint": "استخدام أسماء الاستفهام (أين، متى، هل) مع صيغ التأدب (من فضلك)",
              "culturalEtiquetteTip": "الصبر والتحدث بنبرة هادئة مع موظفي المطارات يفتح لك أفضل خيارات المساعدة."
            }
            """.trimIndent()
            else -> """
            {
              "titleAr": "طَلَبُ وَجْبَةٍ خَاصَّةٍ فِي المَطْعَمِ العَرَبِيِّ",
              "titleId": "Memesan Menu Khusus di Restoran",
              "locationNameAr": "${theme.defaultLocationAr}",
              "characterNameAr": "${theme.defaultCharacterAr}",
              "characterRoleAr": "المضيف",
              "situationBackgroundAr": "${theme.description}",
              "situationBackgroundId": "Simulasi pengalaman kontekstual menggunakan Bahasa Arab.",
              "stages": [
                {
                  "stageNumber": 1,
                  "stageTitleAr": "الترحيب واختيار الطاولة",
                  "stageTitleId": "Menyambut dan Memilih Meja",
                  "aiCharacterSpeechAr": "أَهْلاً وَسَهْلاً بِكَ فِي مَطْعَمِنَا! هَلْ تُرِيدُ طَاوِلَةً لِشَخْصٍ وَاحِدٍ؟",
                  "aiCharacterSpeechId": "Selamat datang di restoran kami! Mau meja untuk satu orang?",
                  "userGoalDescription": "أجب بالإيجاب واطلب قائمة الطعام بأدب",
                  "suggestedResponsesAr": ["نَعَمْ مِنْ فَضْلِكَ، أُرِيدُ طَاوِلَةً هُنَا.", "أُرِيدُ قَائِمَةَ الطَّعَامِ مِنْ فَضْلِكَ."]
                },
                {
                  "stageNumber": 2,
                  "stageTitleAr": "تعديل المكونات لحساسية الطعام",
                  "stageTitleId": "Meminta Tanpa Bumbu Tertentu",
                  "aiCharacterSpeechAr": "تَفَضَّلِ القَائِمَةَ. لَدَيْنَا كَبْسَةُ اللَّحْمِ وَالدَّجَاجُ المَشْوِيُّ، مَاذَا تُفَضِّلُ؟",
                  "aiCharacterSpeechId": "Ini menunya. Kami punya kabsah daging dan ayam bakar, mau yang mana?",
                  "userGoalDescription": "اطلب الكبسة واشترط أن تكون بدون فلفل حار",
                  "suggestedResponsesAr": ["أُرِيدُ كَبْسَةَ اللَّحْمِ بِدُونِ فِلْفِلٍ حَارٍّ مِنْ فَضْلِكَ.", "هَلِ الدَّجَاجُ مَشْوِيٌّ طَازَجٌ؟"]
                },
                {
                  "stageNumber": 3,
                  "stageTitleAr": "دفع الحساب والمغادرة",
                  "stageTitleId": "Membayar dan Berpamitan",
                  "aiCharacterSpeechAr": "حَاضِرٌ يَا سَيِّدِي! الحِسَابُ خَمْسَةٌ وَثَلاَثُونَ رِيَالاً، هَلْ تُفَضِّلُ النَّقْدَ أَمِ البِطَاقَةَ؟",
                  "aiCharacterSpeechId": "Siap tuan! Tagihannya 35 riyal, apakah mau bayar tunai atau kartu?",
                  "userGoalDescription": "ادفع بالبطاقة المصرفية واشكر النادل على الخدمة الطيبة",
                  "suggestedResponsesAr": ["سَأَدْفَعُ بِالبِطَاقَةِ، وَالطَّعَامُ كَانَ لَذِيذًا جِدًّا!", "شُكْرًا لَكَ."]
                }
              ],
              "targetVocabulary": [
                {"wordAr": "قَائِمَةُ الطَّعَامِ", "meaningId": "Buku Menu"},
                {"wordAr": "بِدُونِ فِلْفِلٍ", "meaningId": "Tanpa Cabai"},
                {"wordAr": "حِسَابٌ", "meaningId": "Tagihan / Bon"}
              ],
              "grammarFocusPoint": "أداة النفي والاستثناء (بدون) والمفعول به المنصوب",
              "culturalEtiquetteTip": "إكرام النادل بالثناء على الطعام (الأكل لذيذ ما شاء الله) من عادات الضيافة العربية الجميلة."
            }
            """.trimIndent()
        }
    }

    private fun defaultStages(theme: ScenarioTheme): List<SimulationStage> {
        return listOf(
            SimulationStage(
                stageNumber = 1,
                stageTitleAr = "بداية الموقف والتواصل الأولي",
                stageTitleId = "Fase 1: Komunikasi Awal",
                aiCharacterSpeechAr = "السَّلاَمُ عَلَيْكُمْ، كَيْفَ أَسْتَطِيعُ مُسَاعَدَتَكَ اليَوْمَ؟",
                aiCharacterSpeechId = "Assalamu'alaikum, bagaimana saya bisa membantu Anda hari ini?",
                userGoalDescription = "رد السلام واطرح طلبك الأساسي",
                suggestedResponsesAr = listOf("وَعَلَيْكُمُ السَّلاَمُ، أُرِيدُ المُسَاعَدَةَ مِنْ فَضْلِكَ.", "أَهْلاً بِكَ.")
            )
        )
    }

    private fun fallbackScenario(theme: ScenarioTheme): GeneratedScenario {
        return GeneratedScenario(
            id = "fallback_${System.currentTimeMillis()}",
            themeId = theme.id,
            titleAr = "موقف تفاعلي في ${theme.nameAr}",
            titleId = "Simulasi Kontekstual: ${theme.nameId}",
            locationNameAr = theme.defaultLocationAr,
            characterNameAr = theme.defaultCharacterAr,
            characterRoleAr = "المضيف",
            situationBackgroundAr = theme.description,
            situationBackgroundId = "تفاعل واقعي باستخدام اللغة العربية الفصحى المعاصرة.",
            stages = defaultStages(theme),
            targetVocabulary = listOf("السَّلاَمُ" to "Salam", "مِنْ فَضْلِكَ" to "Tolong / Silakan"),
            grammarFocusPoint = "أساليب التحية والطلب",
            culturalEtiquetteTip = "ابدأ دائماً بعبارات التقدير والاحترام."
        )
    }

    override fun onCleared() {
        super.onCleared()
        audioService.shutdown()
    }
}
