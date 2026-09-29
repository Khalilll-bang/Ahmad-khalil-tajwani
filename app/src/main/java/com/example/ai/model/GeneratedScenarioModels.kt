package com.example.ai.model

data class ScenarioTheme(
    val id: String,
    val nameAr: String,
    val nameId: String,
    val icon: String,
    val defaultLocationAr: String,
    val defaultCharacterAr: String,
    val description: String
) {
    companion object {
        val ALL_THEMES = listOf(
            ScenarioTheme(
                id = "travel",
                nameAr = "السفر والمطارات",
                nameId = "Perjalanan & Bandara",
                icon = "✈️",
                defaultLocationAr = "مطار الملك خالد الدولي",
                defaultCharacterAr = "موظف الجوازات والتفتيش",
                description = "مواقف فوات الرحلة، وزن الحقائب الزائد، والبحث عن بوابات الصعود."
            ),
            ScenarioTheme(
                id = "food",
                nameAr = "المطاعم والضيافة",
                nameId = "Kuliner & Restoran",
                icon = "🍽️",
                defaultLocationAr = "مطعم مأكولات تقليدية",
                defaultCharacterAr = "النادل ورئيس الخدمة",
                description = "طلب وجبات شعبية، الاستفسار عن المكونات، ومعالجة خطأ في الفاتورة."
            ),
            ScenarioTheme(
                id = "shopping",
                nameAr = "الأسواق والمتاجر",
                nameId = "Belanja & Pasar Tradisional",
                icon = "🛒",
                defaultLocationAr = "سوق البطحاء القديم",
                defaultCharacterAr = "تاجر العطور والملابس",
                description = "المفاصلة في الأسعار، طلب مقاس مختلف، وتبديل البضائع التالفة."
            ),
            ScenarioTheme(
                id = "health",
                nameAr = "الصحة والعيادات",
                nameId = "Kesehatan & Apotek",
                icon = "🏥",
                defaultLocationAr = "العيادة الطبية الشاملة",
                defaultCharacterAr = "الطبيبة والصيدلي",
                description = "شرح أعراض الحساسية، استشارة الطبيب، والاستفسار عن جرعات الدواء."
            ),
            ScenarioTheme(
                id = "campus",
                nameAr = "الحياة الجامعية",
                nameId = "Kampus & Akademik",
                icon = "🎓",
                defaultLocationAr = "قاعة المحاضرات المركزية",
                defaultCharacterAr = "أستاذ المادة ورئيس القسم",
                description = "مناقشة أوقات الاختبارات، استعارة المراجع، والتسجيل في الأنشطة الطلابية."
            ),
            ScenarioTheme(
                id = "hotel",
                nameAr = "الفنادق والإقامة",
                nameId = "Hotel & Penginapan",
                icon = "🏨",
                defaultLocationAr = "فندق وسط العاصمة",
                defaultCharacterAr = "موظف الاستقبال الليلي",
                description = "تأكيد الحجز المسبق، حل عطل بطاقة الغرفة، وطلب الإفطار المبكر."
            ),
            ScenarioTheme(
                id = "transport",
                nameAr = "المواصلات والتاكسي",
                nameId = "Taksi & Transportasi",
                icon = "🚕",
                defaultLocationAr = "محطة النقل السريع",
                defaultCharacterAr = "سائق سيارة الأجرة",
                description = "الاتفاق على المسار المختصر، التعامل مع الزحام، ودفع الأجرة بالبطاقة."
            ),
            ScenarioTheme(
                id = "community",
                nameAr = "المسجد والمجتمع",
                nameId = "Masjid & Lingkungan Sosial",
                icon = "🕌",
                defaultLocationAr = "المسجد الكبير والساحة",
                defaultCharacterAr = "إمام المسجد وأحد الجيران",
                description = "التعارف والتحية الإسلامية، التوجيه لأماكن الوضوء، وحلقات القرآن."
            )
        )
    }
}

enum class ScenarioDifficulty(val labelAr: String, val labelId: String, val levelCode: String) {
    BEGINNER("مبتدئ", "Pemula (A1-A2)", "A1"),
    INTERMEDIATE("متوسط", "Menengah (B1)", "B1"),
    ADVANCED("متقدم", "Lanjut (B2)", "B2")
}

data class SimulationStage(
    val stageNumber: Int,
    val stageTitleAr: String,
    val stageTitleId: String,
    val aiCharacterSpeechAr: String,
    val aiCharacterSpeechId: String,
    val userGoalDescription: String,
    val suggestedResponsesAr: List<String>,
    val problemOrCrisisDetail: String = ""
)

data class GeneratedScenario(
    val id: String,
    val themeId: String,
    val titleAr: String,
    val titleId: String,
    val locationNameAr: String,
    val characterNameAr: String,
    val characterRoleAr: String,
    val situationBackgroundAr: String,
    val situationBackgroundId: String,
    val stages: List<SimulationStage>,
    val targetVocabulary: List<Pair<String, String>>, // Arabic word to Indonesian meaning
    val grammarFocusPoint: String,
    val culturalEtiquetteTip: String
)
