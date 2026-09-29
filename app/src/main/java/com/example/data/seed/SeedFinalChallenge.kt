package com.example.data.seed

import com.example.data.model.ActivityModel
import com.example.data.model.MissionModel

object SeedFinalChallenge {
    val finalChallengeMission = MissionModel(
        id = "m_final_challenge",
        locationId = "loc_downtown",
        titleAr = "التحدي الكبير: يوم كامل باللغة العربية",
        titleId = "Tantangan Utama: Sehari Penuh Berbahasa Arab",
        missionType = "PROBLEM_SOLVING",
        difficulty = "Hard",
        objective = "محاكاة واقعية شاملة ليوم كامل: من الاستيقاظ في البيت، إلى الجامعة، والمطعم، والمواصلات، وحل الأزمات المفاجئة!",
        xp = 250,
        orderNumber = 100,
        characterSpeakerId = "teacher_male_01",
        characterNameAr = "الراوي والموجه",
        activities = listOf(
            // Stage 1: Morning at House
            ActivityModel(
                id = "act_fc_1",
                activityType = "SENTENCE_BUILDER",
                speakerId = "teacher_female_01",
                speakerNameAr = "الأم في البيت",
                promptAr = "المحطة 1 (الصباح في البيت): ودع والدتك قبل الخروج للجامعة: (Selamat pagi ibu, saya pergi ke kampus sekarang)",
                promptId = "Susun ucapan pamit di pagi hari:",
                audioTextAr = "صَبَاحُ الخَيْرِ يَا أُمِّي، أَنَا ذَاهِبٌ إِلَى الجَامِعَةِ الآنَ.",
                wordChips = listOf("صَبَاحُ", "الخَيْرِ", "يَا", "أُمِّي،", "أَنَا", "ذَاهِبٌ", "إِلَى", "الجَامِعَةِ."),
                correctAnswer = "صَبَاحُ الخَيْرِ يَا أُمِّي، أَنَا ذَاهِبٌ إِلَى الجَامِعَةِ.",
                hint1 = "صباح الخير = selamat pagi.",
                hint2 = "أنا ذاهب إلى الجامعة = saya pergi ke kampus.",
                hint3 = "صَبَاحُ الخَيْرِ يَا أُمِّي، أَنَا ذَاهِبٌ إِلَى الجَامِعَةِ.",
                explanationAr = "بداية مباركة لليوم بالبر والتحية الطيبة.",
                explanationId = "Awal hari yang penuh berkah dengan berpamitan kepada ibu."
            ),
            // Stage 2: Transport Crisis (Bus Missed)
            ActivityModel(
                id = "act_fc_2",
                activityType = "DECISION_BRANCH",
                speakerId = "driver_male_01",
                speakerNameAr = "سائق المحطة",
                promptAr = "المحطة 2 (أزمة المواصلات): وصلت المحطة وفاتتك حافلة الصباح! ماذا تفعل لتصل في الموعد؟",
                promptId = "Bus pagi baru saja lewat. Bagaimana keputusanmu agar tidak terlambat kuliah?",
                audioTextAr = "لَقَدْ فَاتَتْكَ الحَافِلَةُ! الحَافِلَةُ القَادِمَةُ بَعْدَ سَاعَةٍ كَامِلَةٍ!",
                options = listOf(
                    "أَرْكَبُ سَيَّارَةَ الأُجْرَةِ (التَّاكْسِي) لِأَصِلَ سَرِيعًا",
                    "أرجع إلى البيت وأنام طوال اليوم",
                    "أقف في الشارع وأبكي"
                ),
                correctAnswer = "أَرْكَبُ سَيَّارَةَ الأُجْرَةِ (التَّاكْسِي) لِأَصِلَ سَرِيعًا",
                hint1 = "اختر البديل السريع لتدارك الوقت.",
                hint2 = "أركب سيارة الأجرة = naik taksi.",
                hint3 = "أركب سيارة الأجرة لأصل سريعاً.",
                explanationAr = "قرار حكيم ومسؤول للمحافظة على وقت المحاضرة الأولى.",
                explanationId = "Keputusan cepat dan tepat naik taksi agar tidak tertinggal materi kuliah."
            ),
            // Stage 3: University Classroom
            ActivityModel(
                id = "act_fc_3",
                activityType = "LISTENING",
                speakerId = "teacher_male_01",
                speakerNameAr = "د. كمال",
                promptAr = "المحطة 3 (قاعة الدرس): استمع لتوجيه الأستاذ حول موعد تسليم الواجب:",
                promptId = "Dengarkan pengumuman tenggat tugas:",
                audioTextAr = "تَسْلِيمُ البَحْثِ يَوْمَ الخَمِيسِ القَادِمِ قَبْلَ الظُّهْرِ.",
                options = listOf(
                    "Penyerahan makalah hari Kamis depan sebelum Zuhur",
                    "الاختبار النهائي غداً في الصباح",
                    "إلغاء المحاضرة القادمة",
                    "شراء كتاب جديد من المكتبة"
                ),
                correctAnswer = "Penyerahan makalah hari Kamis depan sebelum Zuhur",
                hint1 = "يوم الخميس = hari Kamis.",
                hint2 = "قبل الظهر = sebelum Zuhur.",
                hint3 = "تسليم البحث يوم الخميس القادم قبل الظهر.",
                explanationAr = "فهمت التوجيه الأكاديمي بدقة متناهية.",
                explanationId = "Memahami batas akhir tugas akademik dengan cermat."
            ),
            // Stage 4: Sudden problem (Phone Battery Low at Cafe)
            ActivityModel(
                id = "act_fc_4",
                activityType = "SPEAKING",
                speakerId = "waiter_male_01",
                speakerNameAr = "نادل المقهى",
                promptAr = "المحطة 4 (طوارئ المقهى): بطارية هاتفك 2% وتحتاج لشاحن للتواصل مع صديقك! اطلب من النادل بلباقة: (Bolehkah saya pinjam charger?)",
                promptId = "Ucapkan atau ketik: هَلْ عِنْدَكُمْ شَاحِنٌ لِلْهَاتِفِ مِنْ فَضْلِكَ؟",
                audioTextAr = "هَلْ عِنْدَكُمْ شَاحِنٌ لِلْهَاتِفِ مِنْ فَضْلِكَ؟",
                correctAnswer = "هل عندكم شاحن للهاتف من فضلك",
                wordChips = listOf("هَلْ", "عِنْدَكُمْ", "شَاحِنٌ", "لِلْهَاتِفِ", "مِنْ", "فَضْلِكَ؟"),
                hint1 = "شاحن للهاتف = charger ponsel.",
                hint2 = "هل عندكم = apakah kalian punya.",
                hint3 = "هَلْ عِنْدَكُمْ شَاحِنٌ لِلْهَاتِفِ مِنْ فَضْلِكَ؟",
                explanationAr = "ناولك النادل شاحناً سريعاً وشحنت هاتفك والتقيت بصديقك!",
                explanationId = "Pelayan dengan senang hati meminjamkan charger dan kamu berhasil tersambung kembali!"
            ),
            // Stage 5: Night Return & Gratitude
            ActivityModel(
                id = "act_fc_5",
                activityType = "SENTENCE_BUILDER",
                speakerId = "teacher_male_01",
                speakerNameAr = "الراوي",
                promptAr = "المحطة 5 (المساء والرجوع): رجعت إلى بيتك بعد يوم ناجح باللغة العربية! عبر عن امتنانك: (Hari ini adalah hari yang sangat luar biasa dan penuh berkah)",
                promptId = "Susun kalimat penutup hari:",
                audioTextAr = "كَانَ هَذَا اليَوْمُ رَائِعًا جِدًّا وَمُبَارَكًا، الحَمْدُ لِلَّهِ.",
                wordChips = listOf("كَانَ", "هَذَا", "اليَوْمُ", "رَائِعًا", "جِدًّا", "وَمُبَارَكًا،", "الحَمْدُ", "لِلَّهِ."),
                correctAnswer = "كَانَ هَذَا اليَوْمُ رَائِعًا جِدًّا وَمُبَارَكًا، الحَمْدُ لِلَّهِ.",
                hint1 = "كان هذا اليوم = hari ini sungguh.",
                hint2 = "رائعاً جداً ومباركاً = luar biasa dan berkah.",
                hint3 = "كَانَ هَذَا اليَوْمُ رَائِعًا جِدًّا وَمُبَارَكًا، الحَمْدُ لِلَّهِ.",
                explanationAr = "مبارك! أنجزت التحدي الكبير ليوم كامل باللغة العربية وحصلت على وسام بطل اليوم الكامل!",
                explanationId = "Selamat! Kamu berhasil menuntaskan Simulasi Sehari Penuh dan meraih Lencana Master Hari Penuh!"
            )
        )
    )
}
