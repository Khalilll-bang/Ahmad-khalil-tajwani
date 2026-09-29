package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ai.AiRoleplayEngine
import com.example.ai.RoleplayScenario
import com.example.ai.RoleplayTurn
import com.example.audio.AudioService
import com.example.data.model.ActivityModel
import com.example.data.model.MissionModel
import com.example.data.model.MissionResultEntity
import com.example.data.model.UserEntity
import com.example.data.model.UserProfileEntity
import com.example.data.model.VocabularyEntity
import com.example.data.repository.AppRepository
import com.example.speech.SpeechEvaluationResult
import com.example.speech.SpeechEvaluationService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class ScreenState {
    AUTH,
    DASHBOARD,
    WORLD_MAP,
    MISSION_ACTIVE,
    MISSION_RESULT,
    VOCABULARY,
    ACHIEVEMENTS,
    PROFILE,
    TEACHER_DASHBOARD,
    AI_ROLEPLAY,
    AI_SCENARIO_GENERATOR,
    FINAL_CHALLENGE
}

data class TeacherAnalytics(
    val totalStudents: Int = 0,
    val averageXp: Int = 0,
    val averageProgressPercent: Int = 0,
    val weakestSkill: String = "الاستماع (Listening)",
    val mostChallengingMission: String = "أين مفتاحي؟ (البيت)",
    val mostMissedVocab: String = "يمين / يسار (الاتجاهات)"
)

class MainViewModel(application: Application) : AndroidViewModel(application) {
    val repository = AppRepository(application)
    val audioService = AudioService(application)
    val speechService = SpeechEvaluationService(application)
    val roleplayEngine = AiRoleplayEngine()

    // Navigation & Screen
    private val _currentScreen = MutableStateFlow(ScreenState.AUTH)
    val currentScreen: StateFlow<ScreenState> = _currentScreen.asStateFlow()

    // Authentication & Active User
    private val _currentUser = MutableStateFlow<UserEntity?>(null)
    val currentUser: StateFlow<UserEntity?> = _currentUser.asStateFlow()

    private val _currentProfile = MutableStateFlow<UserProfileEntity?>(null)
    val currentProfile: StateFlow<UserProfileEntity?> = _currentProfile.asStateFlow()

    // Active Mission State
    private val _activeMission = MutableStateFlow<MissionModel?>(null)
    val activeMission: StateFlow<MissionModel?> = _activeMission.asStateFlow()

    private val _currentActivityIndex = MutableStateFlow(0)
    val currentActivityIndex: StateFlow<Int> = _currentActivityIndex.asStateFlow()

    private val _revealedHintLevel = MutableStateFlow(0) // 0: None, 1: Light, 2: Vocab, 3: Structure
    val revealedHintLevel: StateFlow<Int> = _revealedHintLevel.asStateFlow()

    private val _selectedSentenceChips = MutableStateFlow<List<String>>(emptyList())
    val selectedSentenceChips: StateFlow<List<String>> = _selectedSentenceChips.asStateFlow()

    private val _lastSpeechResult = MutableStateFlow<SpeechEvaluationResult?>(null)
    val lastSpeechResult: StateFlow<SpeechEvaluationResult?> = _lastSpeechResult.asStateFlow()

    private val _lastMissionResult = MutableStateFlow<MissionResultEntity?>(null)
    val lastMissionResult: StateFlow<MissionResultEntity?> = _lastMissionResult.asStateFlow()

    // Context Dictionary Pop-up state
    private val _inspectingWord = MutableStateFlow<VocabularyEntity?>(null)
    val inspectingWord: StateFlow<VocabularyEntity?> = _inspectingWord.asStateFlow()

    // AI Roleplay State
    private val _activeScenario = MutableStateFlow(roleplayEngine.scenarios.first())
    val activeScenario: StateFlow<RoleplayScenario> = _activeScenario.asStateFlow()

    private val _roleplayChatHistory = MutableStateFlow<List<RoleplayTurn>>(emptyList())
    val roleplayChatHistory: StateFlow<List<RoleplayTurn>> = _roleplayChatHistory.asStateFlow()

    // Completed mission IDs for active user
    val completedMissionIds: StateFlow<List<String>> = _currentUser.combine(repository.getAllResults()) { user, _ ->
        if (user != null) {
            repository.getCompletedMissionIds(user.id).firstOrNull() ?: emptyList()
        } else {
            emptyList()
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // All Vocabulary for active user
    val vocabularyList: StateFlow<List<VocabularyEntity>> = repository.getAllVocabulary()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Achievements earned
    val earnedAchievementIds: StateFlow<List<String>> = _currentUser.combine(repository.getAllResults()) { user, _ ->
        if (user != null) {
            repository.getUserEarnedAchievements(user.id).firstOrNull() ?: emptyList()
        } else {
            emptyList()
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Teacher View Data
    val studentList: StateFlow<List<UserEntity>> = repository.getAllStudents()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allProfiles: StateFlow<List<UserProfileEntity>> = repository.getAllProfiles()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        viewModelScope.launch {
            repository.initializeDatabaseIfEmpty()
            // Auto login default student for seamless preview
            val defaultStudent = repository.loginUser("ahmad@student.com", "123456").getOrNull()
            if (defaultStudent != null) {
                _currentUser.value = defaultStudent
                observeProfile(defaultStudent.id)
                _currentScreen.value = ScreenState.DASHBOARD
            }
        }
    }

    private fun observeProfile(userId: Long) {
        viewModelScope.launch {
            repository.getUserProfile(userId).collect { profile ->
                _currentProfile.value = profile
            }
        }
    }

    fun navigateTo(screen: ScreenState) {
        _currentScreen.value = screen
    }

    fun login(email: String, pass: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            val res = repository.loginUser(email, pass)
            if (res.isSuccess) {
                val user = res.getOrThrow()
                _currentUser.value = user
                observeProfile(user.id)
                _currentScreen.value = if (user.role == "TEACHER") ScreenState.TEACHER_DASHBOARD else ScreenState.DASHBOARD
                onSuccess()
            } else {
                onError(res.exceptionOrNull()?.message ?: "Gagal login")
            }
        }
    }

    fun register(name: String, email: String, pass: String, level: String, role: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            val res = repository.registerUser(name, email, pass, level, role)
            if (res.isSuccess) {
                val user = res.getOrThrow()
                _currentUser.value = user
                observeProfile(user.id)
                _currentScreen.value = if (user.role == "TEACHER") ScreenState.TEACHER_DASHBOARD else ScreenState.DASHBOARD
                onSuccess()
            } else {
                onError(res.exceptionOrNull()?.message ?: "Gagal registrasi")
            }
        }
    }

    fun logout() {
        _currentUser.value = null
        _currentProfile.value = null
        _currentScreen.value = ScreenState.AUTH
    }

    // ==================== MISSION EXECUTION ====================
    fun startMission(mission: MissionModel) {
        _activeMission.value = mission
        _currentActivityIndex.value = 0
        _revealedHintLevel.value = 0
        _selectedSentenceChips.value = emptyList()
        _lastSpeechResult.value = null
        _currentScreen.value = ScreenState.MISSION_ACTIVE

        // Automatically speak opening instruction or prompt
        val firstAct = mission.activities.firstOrNull()
        if (firstAct != null && firstAct.audioTextAr.isNotBlank()) {
            audioService.speak(firstAct.audioTextAr, firstAct.speakerId)
        }
    }

    fun addSentenceChip(chip: String) {
        _selectedSentenceChips.value = _selectedSentenceChips.value + chip
    }

    fun removeSentenceChip(index: Int) {
        val list = _selectedSentenceChips.value.toMutableList()
        if (index in list.indices) {
            list.removeAt(index)
            _selectedSentenceChips.value = list
        }
    }

    fun clearSentenceChips() {
        _selectedSentenceChips.value = emptyList()
    }

    fun requestNextHint() {
        if (_revealedHintLevel.value < 3) {
            _revealedHintLevel.value += 1
        }
    }

    fun submitAnswer(userAnswer: String, onFeedback: (isCorrect: Boolean, feedback: String) -> Unit) {
        val mission = _activeMission.value ?: return
        val currentAct = mission.activities.getOrNull(_currentActivityIndex.value) ?: return

        val normUser = normalize(userAnswer)
        val normTarget = normalize(currentAct.correctAnswer)

        val isMatch = normUser.contains(normTarget) || normTarget.contains(normUser) || (levenshteinRatio(normUser, normTarget) > 0.70f)

        if (isMatch) {
            onFeedback(true, "أَحْسَنْتَ! إِجَابَةٌ صَحِيحَةٌ وَمُتْقَنَةٌ. (Jawabanmu benar dan tepat!)")
        } else {
            requestNextHint()
            onFeedback(false, "حَاوِلْ مَرَّةً أُخْرَى! تَفَقَّدِ التَّلْمِيحَاتِ. (Coba lagi, periksa petunjuk hint di bawah!)")
        }
    }

    fun submitSpeech(spokenText: String, expected: String, onFeedback: (isAcceptable: Boolean, result: SpeechEvaluationResult) -> Unit) {
        val result = speechService.evaluateSpeech(spokenText, expected)
        _lastSpeechResult.value = result
        onFeedback(result.isAcceptable, result)
    }

    fun advanceToNextActivity() {
        val mission = _activeMission.value ?: return
        val nextIdx = _currentActivityIndex.value + 1

        if (nextIdx < mission.activities.size) {
            _currentActivityIndex.value = nextIdx
            _revealedHintLevel.value = 0
            _selectedSentenceChips.value = emptyList()
            _lastSpeechResult.value = null

            val act = mission.activities[nextIdx]
            if (act.audioTextAr.isNotBlank()) {
                audioService.speak(act.audioTextAr, act.speakerId)
            }
        } else {
            // Mission Complete!
            finishActiveMission()
        }
    }

    private fun finishActiveMission() {
        val user = _currentUser.value ?: return
        val mission = _activeMission.value ?: return

        val score = 95
        val speechScore = _lastSpeechResult.value?.overallScore ?: 85
        val listeningScore = 90
        val readingScore = 95
        val writingScore = 88
        val vocabScore = 92
        val grammarScore = 89

        viewModelScope.launch {
            repository.completeMission(
                userId = user.id,
                missionId = mission.id,
                score = score,
                xpEarned = mission.xp,
                speakingScore = speechScore,
                listeningScore = listeningScore,
                readingScore = readingScore,
                writingScore = writingScore,
                vocabularyScore = vocabScore,
                grammarScore = grammarScore
            )

            _lastMissionResult.value = MissionResultEntity(
                userId = user.id,
                missionId = mission.id,
                score = score,
                xpEarned = mission.xp,
                completedAt = System.currentTimeMillis(),
                speakingScore = speechScore,
                listeningScore = listeningScore,
                readingScore = readingScore,
                writingScore = writingScore,
                vocabularyScore = vocabScore,
                grammarScore = grammarScore
            )
            _currentScreen.value = ScreenState.MISSION_RESULT
        }
    }

    // ==================== CONTEXT DICTIONARY ====================
    fun inspectWord(word: VocabularyEntity) {
        _inspectingWord.value = word
        audioService.speak(word.tashkeel, "teacher_female_01")
    }

    fun closeWordInspector() {
        _inspectingWord.value = null
    }

    fun toggleSaveVocab(vocabId: String) {
        viewModelScope.launch {
            repository.toggleVocabSaved(vocabId)
            val current = _inspectingWord.value
            if (current != null && current.id == vocabId) {
                _inspectingWord.value = current.copy(isSaved = !current.isSaved)
            }
        }
    }

    // ==================== AI ROLEPLAY ====================
    fun startRoleplay(scenario: RoleplayScenario) {
        _activeScenario.value = scenario
        _roleplayChatHistory.value = listOf(
            RoleplayTurn(
                speakerRole = "AI",
                speakerNameAr = scenario.characterNameAr,
                textAr = scenario.initialAiGreetingAr,
                textId = scenario.initialAiGreetingId
            )
        )
        _currentScreen.value = ScreenState.AI_ROLEPLAY
        audioService.speak(scenario.initialAiGreetingAr, scenario.characterId)
    }

    fun sendRoleplayMessage(userText: String) {
        if (userText.isBlank()) return
        val currentHist = _roleplayChatHistory.value.toMutableList()
        currentHist.add(
            RoleplayTurn(
                speakerRole = "USER",
                speakerNameAr = _currentUser.value?.name ?: "أنت",
                textAr = userText,
                textId = "Pesan pengguna"
            )
        )
        _roleplayChatHistory.value = currentHist

        // Generate AI character response
        viewModelScope.launch {
            val scenario = _activeScenario.value
            val level = _currentUser.value?.arabicLevel ?: "BEGINNER"
            val aiResponse = roleplayEngine.generateCharacterResponse(scenario.scenarioId, userText, level)

            val updatedHist = _roleplayChatHistory.value.toMutableList()
            updatedHist.add(aiResponse)
            _roleplayChatHistory.value = updatedHist

            audioService.speak(aiResponse.textAr, scenario.characterId)
        }
    }

    // ==================== TEACHER ANALYTICS ====================
    fun getTeacherAnalytics(): TeacherAnalytics {
        val students = studentList.value
        val profiles = allProfiles.value
        val total = students.size
        val avgXp = if (profiles.isNotEmpty()) profiles.map { it.xp }.average().toInt() else 0
        val avgProgress = if (profiles.isNotEmpty()) ((avgXp / 1000f) * 100).toInt().coerceIn(15, 95) else 0

        return TeacherAnalytics(
            totalStudents = total,
            averageXp = avgXp,
            averageProgressPercent = avgProgress,
            weakestSkill = "الاستماع (Listening)",
            mostChallengingMission = "أين مفتاحي؟ (البيت)",
            mostMissedVocab = "يمين / يسار (الاتجاهات)"
        )
    }

    private fun normalize(text: String): String {
        return text
            .replace("[\u064B-\u065F\u0670]".toRegex(), "")
            .replace("[إأآا]".toRegex(), "ا")
            .replace("ة", "ه")
            .replace("ى", "ي")
            .replace("[،.؛!؟,\"?]".toRegex(), "")
            .replace("\\s+".toRegex(), " ")
            .trim()
            .lowercase()
    }

    private fun levenshteinRatio(s1: String, s2: String): Float {
        val maxLen = maxOf(s1.length, s2.length)
        if (maxLen == 0) return 1.0f
        var distance = 0
        val dp = Array(s1.length + 1) { IntArray(s2.length + 1) }
        for (i in 0..s1.length) dp[i][0] = i
        for (j in 0..s2.length) dp[0][j] = j
        for (i in 1..s1.length) {
            for (j in 1..s2.length) {
                val cost = if (s1[i - 1] == s2[j - 1]) 0 else 1
                dp[i][j] = minOf(dp[i - 1][j] + 1, dp[i][j - 1] + 1, dp[i - 1][j - 1] + cost)
            }
        }
        distance = dp[s1.length][s2.length]
        return (maxLen - distance).toFloat() / maxLen
    }

    override fun onCleared() {
        super.onCleared()
        audioService.shutdown()
        speechService.release()
    }
}
