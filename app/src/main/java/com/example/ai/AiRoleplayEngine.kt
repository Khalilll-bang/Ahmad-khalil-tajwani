package com.example.ai

import com.example.data.seed.SeedLocations

data class RoleplayTurn(
    val speakerRole: String, // "AI" or "USER"
    val speakerNameAr: String,
    val textAr: String,
    val textId: String,
    val timestamp: Long = System.currentTimeMillis()
)

data class RoleplayScenario(
    val scenarioId: String,
    val locationId: String,
    val locationNameAr: String,
    val characterId: String,
    val characterNameAr: String,
    val characterRoleAr: String,
    val initialAiGreetingAr: String,
    val initialAiGreetingId: String,
    val targetGoals: List<String>,
    val promptContext: String
)

class AiRoleplayEngine {

    val scenarios = listOf(
        RoleplayScenario(
            scenarioId = "rp_restaurant",
            locationId = "loc_restaurant",
            locationNameAr = "المطعم",
            characterId = "waiter_male_01",
            characterNameAr = "النادل كريم",
            characterRoleAr = "نادل المطعم",
            initialAiGreetingAr = "أَهْلاً وَسَهْلاً بِكَ فِي مَطْعَمِنَا! تَفَضَّلْ، مَاذَا تُحِبُّ أَنْ تَأْكُلَ اليَوْمَ؟",
            initialAiGreetingId = "Selamat datang di restoran kami! Silakan, apa yang ingin Anda santap hari ini?",
            targetGoals = listOf("اطلب وجبة الغداء", "حدد المشروب", "اطلب الحساب بأدب"),
            promptContext = "الحوار في مطعم عربي تقليدي مع النادل."
        ),
        RoleplayScenario(
            scenarioId = "rp_taxi",
            locationId = "loc_taxi",
            locationNameAr = "سيارة الأجرة",
            characterId = "driver_male_01",
            characterNameAr = "السائق أبو فهد",
            characterRoleAr = "سائق التاكسي",
            initialAiGreetingAr = "مَرْحَبًا يَا أَخِي! إِلَى أَيْنَ الوُجْهَةُ إِنْ شَاءَ اللَّهُ؟",
            initialAiGreetingId = "Halo saudaraku! Ke mana tujuan kita insya Allah?",
            targetGoals = listOf("حدد الوجهة إلى الجامعة أو الفندق", "اسأل عن الأجرة", "وجه السائق في الطريق"),
            promptContext = "التنقل في تاكسي المدينة والحديث مع السائق."
        ),
        RoleplayScenario(
            scenarioId = "rp_doctor",
            locationId = "loc_hospital",
            locationNameAr = "المستشفى",
            characterId = "doctor_female_01",
            characterNameAr = "الدكتورة مريم",
            characterRoleAr = "طبيبة العيادة",
            initialAiGreetingAr = "أَهْلاً بِكَ، سَلاَمَتُكَ يَا أَخِي. مِمَّ تَشْتَكِي وَمَاذَا تَشْعُرُ؟",
            initialAiGreetingId = "Halo, semoga lekas sehat saudaraku. Apa keluhanmu dan apa yang dirasakan?",
            targetGoals = listOf("صف مكان الألم (الرأس أو البطن)", "حدد مدة المرض", "اشكر الطبيبة واطلب النصيحة"),
            promptContext = "الفحص في العيادة الطبية مع الطبيبة المعالجة."
        ),
        RoleplayScenario(
            scenarioId = "rp_friend",
            locationId = "loc_cafe",
            locationNameAr = "المقهى",
            characterId = "friend_male_01",
            characterNameAr = "الصديق بلال",
            characterRoleAr = "زميل الدراسة",
            initialAiGreetingAr = "السَّلاَمُ عَلَيْكُمْ يَا صَدِيقِي! كَيْفَ حَالُكَ اليَوْمَ؟ مَاذَا تَفْعَلُ هُنَا؟",
            initialAiGreetingId = "Assalamu'alaikum sahabatku! Bagaimana kabarmu hari ini? Sedang apa di sini?",
            targetGoals = listOf("رد السلام واسأل عن حاله", "اقترح مشروباً ساخناً", "تحدث عن موعد الدراسة"),
            promptContext = "لقاء غير متوقع مع صديقك في المقهى بعد الظهر."
        )
    )

    fun generateCharacterResponse(
        scenarioId: String,
        userMessage: String,
        userLevel: String
    ): RoleplayTurn {
        val norm = normalize(userMessage)
        val scenario = scenarios.find { it.scenarioId == scenarioId } ?: scenarios.first()
        val speaker = SeedLocations.speakers[scenario.characterId] ?: SeedLocations.speakers["waiter_male_01"]!!

        val (replyAr, replyId) = when (scenarioId) {
            "rp_restaurant" -> {
                when {
                    norm.contains("ارز") || norm.contains("دجاج") || norm.contains("لحم") || norm.contains("سمك") -> {
                        "حَسَنًا، طَلَبٌ رَائِعٌ جِدًّا! وَمَاذَا تُرِيدُ أَنْ تَشْرَبَ مَعَهُ؟ (عَصِيرٌ أَمْ مَاءٌ؟)" to
                                "Baik, pesanan yang sangat lezat! Mau minum apa bersamanya? (Jus atau air?)"
                    }
                    norm.contains("ماء") || norm.contains("عصير") || norm.contains("شاي") -> {
                        "حَاضِرٌ يَا سَيِّدِي، سَأُحْضِرُهُ لَكَ حَالاً! هَلْ تُرِيدُ أَيَّ شَيْءٍ آخَرَ؟" to
                                "Siap tuan, saya bawakan segera! Apakah ada tambahan lain?"
                    }
                    norm.contains("حساب") || norm.contains("كم") -> {
                        "الحِسَابُ عِشْرُونَ رِيَالاً فَقَطْ، تَفَضَّلْ هَذِهِ الفَاتُورَةُ وَشُكْرًا لِزِيَارَتِكَ!" to
                                "Tagihannya 20 riyal saja, ini fakturnya dan terima kasih atas kunjungannya!"
                    }
                    norm.contains("شكرا") -> {
                        "عَفْوًا، أَهْلاً وَسَهْلاً بِكَ دَائِمًا! فِي أَمَانِ اللَّهِ." to
                                "Sama-sama, selamat datang kembali kapan saja! Sampai jumpa."
                    }
                    else -> {
                        "مَفْهُومٌ يَا طَيِّبُ! هَلْ تُرِيدُ مَعَهُ خُبْزًا أَوْ سَلَطَةً؟" to
                                "Dimengerti saudaraku yang baik! Apakah mau ditambah roti atau salad?"
                    }
                }
            }
            "rp_taxi" -> {
                when {
                    norm.contains("جامعه") || norm.contains("فندق") || norm.contains("مطار") || norm.contains("سوق") -> {
                        "تَمَامًا، سَآخُذُكَ إِلَى هُنَاكَ مِنْ أَسْرَعِ طَرِيقٍ! هَلْ أَنْتَ فِي عَجَلَةٍ مِنْ أَمْرِكَ؟" to
                                "Baik sekali, saya antar lewat rute tercepat! Apakah Anda sedang terburu-buru?"
                    }
                    norm.contains("اجره") || norm.contains("سعر") || norm.contains("كم") -> {
                        "الأُجْرَةُ حَسَبَ العَدَّادِ، حَوَالَيْ 25 رِيَالاً إِنْ شَاءَ اللَّهُ." to
                                "Tarif sesuai argo, sekitar 25 riyal insya Allah."
                    }
                    norm.contains("يسار") || norm.contains("يمين") || norm.contains("امام") || norm.contains("طريق") -> {
                        "شُكْرًا لِتَوْجِيهِكَ، سَأَنْعَطِفُ الآنَ يَمِينًا كَمَا قُلْتَ!" to
                                "Terima kasih arahannya, saya belok kanan sekarang sesuai katamu!"
                    }
                    else -> {
                        "حَسَنًا يَا أَخِي، سَنَصِلُ خِلاَلَ عَشْرِ دَقَائِقَ بِإِذْنِ اللَّهِ." to
                                "Baik saudaraku, kita sampai dalam 10 menit dengan izin Allah."
                    }
                }
            }
            "rp_doctor" -> {
                when {
                    norm.contains("راس") || norm.contains("صداع") -> {
                        "شَفَاكَ اللَّهُ. مُنْذُ مَتَى وَأَنْتَ تَشْعُرُ بِهَذَا الصُّدَاعِ؟ وَهَلْ نِمْتَ جَيِّدًا البَارِحَةَ؟" to
                                "Semoga Allah menyembuhkanmu. Sejak kapan merasa pusing? Apakah tidur nyenyak semalam?"
                    }
                    norm.contains("بطن") || norm.contains("الم") -> {
                        "لاَ بَأْسَ طَهُورٌ إِنْ شَاءَ اللَّهُ. هَلْ أَكَلْتَ شَيْئًا حَارًّا أَوْ مَكْشُوفًا؟" to
                                "Tidak apa-apa, semoga lekas pulih. Apakah tadi makan makanan pedas atau terbuka?"
                    }
                    norm.contains("يومين") || norm.contains("امس") || norm.contains("صباح") -> {
                        "سَأَكْتُبُ لَكَ هَذَا الدَّوَاءَ لِتَأْخُذَهُ بَعْدَ الأَكْلِ مَعَ كَثِيرٍ مِنَ المَاءِ، سَلاَمَتُكَ!" to
                                "Saya resepkan obat ini diminum sesudah makan dengan banyak air. Lekas sehat ya!"
                    }
                    else -> {
                        "أَنْصَحُكَ بِالرَّاحَةِ التَّامَّةِ وَتَنَاوُلِ العَصَائِرِ الطَّازَجَةِ." to
                                "Saya sarankan istirahat total dan perbanyak jus buah segar."
                    }
                }
            }
            else -> {
                when {
                    norm.contains("خير") || norm.contains("الحمد") -> {
                        "الحَمْدُ لِلَّهِ دَائِمًا! مَا رَأْيُكَ أَنْ نَشْرَبَ قَهْوَةً عَرَبِيَّةً مَعًا؟" to
                                "Alhamdulillah selalu! Bagaimana kalau kita minum kopi Arab bersama?"
                    }
                    norm.contains("نعم") || norm.contains("هيا") -> {
                        "يَا سَلاَم! هَيَّا بِنَا، سَأَطْلُبُ القَهْوَةَ الآنَ!" to
                                "Mantap! Ayo, saya pesan kopinya sekarang!"
                    }
                    else -> {
                        "سَعِدْتُ جِدًّا بِلِقَائِكَ يَا أَخِي، دَعْنَا نَبْقَى عَلَى تَوَاصُلٍ!" to
                                "Senang sekali berjumpa denganmu sahabatku, mari tetap saling kontak!"
                    }
                }
            }
        }

        return RoleplayTurn(
            speakerRole = "AI",
            speakerNameAr = speaker.nameAr,
            textAr = replyAr,
            textId = replyId
        )
    }

    private fun normalize(text: String): String {
        return text
            .replace("[\u064B-\u065F\u0670]".toRegex(), "")
            .replace("[إأآا]".toRegex(), "ا")
            .replace("ة", "ه")
            .replace("ى", "ي")
            .trim()
            .lowercase()
    }
}
