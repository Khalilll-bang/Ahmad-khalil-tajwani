package com.example

import android.app.Application
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.ai.model.ScenarioDifficulty
import com.example.ai.model.ScenarioTheme
import com.example.data.seed.SeedFinalChallenge
import com.example.data.seed.SeedLocations
import com.example.data.seed.SeedMissionsLevel1
import com.example.data.seed.SeedMissionsLevel2
import com.example.data.seed.SeedMissionsLevel3to5
import com.example.speech.SpeechEvaluationService
import com.example.viewmodel.ArabicScenarioGeneratorViewModel
import com.example.viewmodel.ScenarioGenerationUiState
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("عَرَبِيٌّ فِي حَيَاتِي", appName)
    }

    @Test
    fun `verify all 21 locations exist`() {
        assertEquals(21, SeedLocations.locations.size)
        val level1Locations = SeedLocations.locations.filter { it.levelNumber == 1 }
        assertEquals(4, level1Locations.size)
    }

    @Test
    fun `verify missions catalog contains all levels and final challenge`() {
        val allMissions = SeedMissionsLevel1.missions +
                SeedMissionsLevel2.missions +
                SeedMissionsLevel3to5.missions +
                listOf(SeedFinalChallenge.finalChallengeMission)

        assertTrue(allMissions.size >= 25)
        val finalChallenge = allMissions.find { it.id == "m_final_challenge" }
        assertNotNull(finalChallenge)
        assertEquals(5, finalChallenge?.activities?.size)
    }

    @Test
    fun `verify speech evaluator normalizes Arabic and evaluates correctly`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val service = SpeechEvaluationService(context)

        val eval = service.evaluateSpeech("أريد الأرز والدجاج", "أُرِيدُ الأَرُزَّ وَالدَّجَاجَ")
        assertTrue(eval.isAcceptable)
        assertTrue(eval.overallScore >= 80)
    }

    @Test
    fun `verify scenario generator ViewModel initializes and configures themes`() {
        val app = ApplicationProvider.getApplicationContext<Application>()
        val vm = ArabicScenarioGeneratorViewModel(app)

        assertEquals(ScenarioTheme.ALL_THEMES.first(), vm.selectedTheme.value)
        assertEquals(ScenarioDifficulty.BEGINNER, vm.selectedDifficulty.value)
        assertTrue(vm.uiState.value is ScenarioGenerationUiState.Idle)

        // Select food theme
        val foodTheme = ScenarioTheme.ALL_THEMES.find { it.id == "food" }!!
        vm.selectTheme(foodTheme)
        assertEquals(foodTheme, vm.selectedTheme.value)

        // Change difficulty
        vm.selectDifficulty(ScenarioDifficulty.INTERMEDIATE)
        assertEquals(ScenarioDifficulty.INTERMEDIATE, vm.selectedDifficulty.value)
    }
}
