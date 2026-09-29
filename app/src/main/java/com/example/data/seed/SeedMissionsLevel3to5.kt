package com.example.data.seed

import com.example.data.model.ActivityModel
import com.example.data.model.MissionModel

object SeedMissionsLevel3to5 {
    val missions = listOf(
        // ==================== 🏥 المستشفى (Hospital - Level 3) ====================
        MissionModel(
            id = "m_hosp_1",
            locationId = "loc_hospital",
            titleAr = "أين تشعر بالألم؟",
            titleId = "Di Bagian Mana Sakitnya?",
            missionType = "RECOGNIZE",
            difficulty = "Easy",
            objective = "تعلم أسماء أعضاء الجسم (الرأس، البطن، اليد، الرجل، الظهر) للتواصل الطبي البسيط.",
            xp = 25,
            orderNumber = 1,
            characterSpeakerId = "doctor_female_01",
            characterNameAr = "الدكتورة مريم",
            activities = listOf(
                ActivityModel(
                    id = "act_hp1_1",
                    activityType = "MULTIPLE_CHOICE",
                    speakerId = "doctor_female_01",
                    speakerNameAr = "الدكتورة مريم",
                    promptAr = "ماذا يعني عضو (الرَّأْس)؟",
                    promptId = "Apa arti anggota tubuh 'الرَّأْس'?",
                    audioTextAr = "أَشْعُرُ بِأَلَمٍ فِي رَأْسِي.",
                    options = listOf("Kepala", "Perut", "Tangan", "Kaki"),
                    correctAnswer = "Kepala",
                    hint1 = "العضو العلوي وفيه العقل والعينان.",
                    hint2 = "الرأس = Kepala.",
                    hint3 = "الرَّأْس",
                    explanationAr = "الرَّأْس هو الجزء العلوي من الجسد.",
                    explanationId = "Al-Ra's (الرأس) berarti kepala."
                )
            )
        ),
        MissionModel(
            id = "m_hosp_2",
            locationId = "loc_hospital",
            titleAr = "ماذا تشعر؟",
            titleId = "Apa yang Kamu Rasakan?",
            missionType = "USE",
            difficulty = "Medium",
            objective = "وصف الأعراض الشائعة مثل الصداع ومدة الشعور بالألم منذ يومين.",
            xp = 40,
            orderNumber = 2,
            characterSpeakerId = "doctor_female_01",
            characterNameAr = "الدكتورة مريم",
            activities = listOf(
                ActivityModel(
                    id = "act_hp2_1",
                    activityType = "SPEAKING",
                    speakerId = "doctor_female_01",
                    speakerNameAr = "الدكتورة مريم",
                    promptAr = "سألتك الطبيبة: 'مِمَّ تَشْتَكِي؟' قل لها: (Saya merasa sakit kepala)",
                    promptId = "Ucapkan atau ketik: أَشْعُرُ بِالصُّدَاعِ",
                    audioTextAr = "أَشْعُرُ بِالصُّدَاعِ.",
                    correctAnswer = "أشعر بالصداع",
                    wordChips = listOf("أَشْعُرُ", "بِالصُّدَاعِ.", "مُنْذُ", "يَوْمَيْنِ"),
                    hint1 = "أشعر بـ = saya merasakan.",
                    hint2 = "الصداع = sakit kepala.",
                    hint3 = "أَشْعُرُ بِالصُّدَاعِ.",
                    explanationAr = "وصف واضح ودقيق للأعراض للطبيبة.",
                    explanationId = "Ungkapan menyampaikan rasa pusing/sakit kepala."
                )
            )
        ),
        MissionModel(
            id = "m_hosp_3",
            locationId = "loc_hospital",
            titleAr = "أين قسم الطوارئ؟",
            titleId = "Di Mana Ruang IGD/Darurat?",
            missionType = "PROBLEM_SOLVING",
            difficulty = "Hard",
            objective = "البحث عن قسم الطوارئ بسرعة واتباع تعليمات موظف الاستعلامات.",
            xp = 70,
            orderNumber = 3,
            characterSpeakerId = "doctor_female_01",
            characterNameAr = "موظف الطوارئ",
            activities = listOf(
                ActivityModel(
                    id = "act_hp3_1",
                    activityType = "LISTENING",
                    speakerId = "doctor_female_01",
                    speakerNameAr = "موظف الطوارئ",
                    promptAr = "استمع لتعليمات الوصول لقسم الطوارئ:",
                    promptId = "Dengarkan arah darurat:",
                    audioTextAr = "قِسْمُ الطَّوَارِئِ فِي نِهَايَةِ المَمَرِّ عَلَى اليَمِينِ.",
                    options = listOf(
                        "Di ujung lorong sebelah kanan",
                        "Di lantai tiga gedung sebelah",
                        "خارج بوابة المستشفى",
                        "في الصيدلية المجاورة"
                    ),
                    correctAnswer = "Di ujung lorong sebelah kanan",
                    hint1 = "نهاية الممر = ujung lorong.",
                    hint2 = "على اليمين = sebelah kanan.",
                    hint3 = "قسم الطوارئ في نهاية الممر على اليمين.",
                    explanationAr = "وصلت إلى قسم الطوارئ بسرعة وأمان.",
                    explanationId = "Arah menuju IGD di ujung lorong sebelah kanan."
                )
            )
        ),

        // ==================== 💊 الصيدلية (Pharmacy - Level 3) ====================
        MissionModel(
            id = "m_pharm_1",
            locationId = "loc_pharmacy",
            titleAr = "أدوية ومستلزمات",
            titleId = "Obat dan Perlengkapan",
            missionType = "RECOGNIZE",
            difficulty = "Easy",
            objective = "التعرف على أسماء المستلزمات الطبية: دواء، فيتامين، كمامة، منديل.",
            xp = 25,
            orderNumber = 1,
            characterSpeakerId = "pharmacist_male_01",
            characterNameAr = "الصيدلي عمر",
            activities = listOf(
                ActivityModel(
                    id = "act_ph1_1",
                    activityType = "MULTIPLE_CHOICE",
                    speakerId = "pharmacist_male_01",
                    speakerNameAr = "الصيدلي عمر",
                    promptAr = "ماذا تسمى (Masker penutup wajah)?",
                    promptId = "Apa bahasa Arab untuk Masker?",
                    audioTextAr = "ارْتَدِ الكَمَّامَةَ لِلْوِقَايَةِ.",
                    options = listOf("كَمَّامَة", "دَوَاء", "فِيتَامِين", "مِنْدِيل"),
                    correctAnswer = "كَمَّامَة",
                    hint1 = "توضع على الفم والأنف.",
                    hint2 = "كَمَّامَة (Kammāmah).",
                    hint3 = "كَمَّامَة",
                    explanationAr = "الكمامة تحمي من العدوى والغبار.",
                    explanationId = "Kammāmah (كمامة) artinya masker penutup hidung dan mulut."
                )
            )
        ),
        MissionModel(
            id = "m_pharm_2",
            locationId = "loc_pharmacy",
            titleAr = "كم سعر هذا؟",
            titleId = "Berapa Harga Obat Ini?",
            missionType = "USE",
            difficulty = "Medium",
            objective = "السؤال عن سعر الدواء وطلب علبة واحدة بلباقة.",
            xp = 40,
            orderNumber = 2,
            characterSpeakerId = "pharmacist_male_01",
            characterNameAr = "الصيدلي عمر",
            activities = listOf(
                ActivityModel(
                    id = "act_ph2_1",
                    activityType = "SENTENCE_BUILDER",
                    speakerId = "pharmacist_male_01",
                    speakerNameAr = "الصيدلي عمر",
                    promptAr = "اطلب من الصيدلي: (Saya mau satu, tolong)",
                    promptId = "Susun: أُرِيدُ وَاحِدًا مِنْ فَضْلِكَ",
                    audioTextAr = "أُرِيدُ وَاحِدًا مِنْ فَضْلِكَ.",
                    wordChips = listOf("أُرِيدُ", "وَاحِدًا،", "مِنْ", "فَضْلِكَ.", "عَشَرَة"),
                    correctAnswer = "أُرِيدُ وَاحِدًا، مِنْ فَضْلِكَ.",
                    hint1 = "واحداً = satu buah.",
                    hint2 = "من فضلك = tolong.",
                    hint3 = "أُرِيدُ وَاحِدًا، مِنْ فَضْلِكَ.",
                    explanationAr = "طلب سليم ومؤدب للشراء من الصيدلية.",
                    explanationId = "Permintaan sopan untuk membeli satu unit obat."
                )
            )
        ),
        MissionModel(
            id = "m_pharm_3",
            locationId = "loc_pharmacy",
            titleAr = "هذا ليس ما أريد",
            titleId = "Ini Bukan yang Saya Inginkan",
            missionType = "PROBLEM_SOLVING",
            difficulty = "Hard",
            objective = "الصيدلي ناولك عبوة فيتامينات وأنت طلبت كمامات! وضح له قصدك بهدوء.",
            xp = 70,
            orderNumber = 3,
            characterSpeakerId = "pharmacist_male_01",
            characterNameAr = "الصيدلي عمر",
            activities = listOf(
                ActivityModel(
                    id = "act_ph3_1",
                    activityType = "SPEAKING",
                    speakerId = "pharmacist_male_01",
                    speakerNameAr = "الصيدلي عمر",
                    promptAr = "قل للصيدلي بأدب: (Maaf, saya ingin masker bukan vitamin)",
                    promptId = "Ucapkan atau ketik: عَفْوًا، أُرِيدُ الكَمَّامَةَ",
                    audioTextAr = "عَفْوًا، أُرِيدُ الكَمَّامَةَ.",
                    correctAnswer = "عفوا أريد الكمامة",
                    wordChips = listOf("عَفْوًا،", "أُرِيدُ", "الكَمَّامَةَ.", "شُكْرًا"),
                    hint1 = "عفواً = maaf/permisi.",
                    hint2 = "أريد الكمامة = saya mau masker.",
                    hint3 = "عَفْوًا، أُرِيدُ الكَمَّامَةَ.",
                    explanationAr = "اعتذر الصيدلي وأعطاك علبة الكمامات فوراً!",
                    explanationId = "Apoteker mengerti dan segera memberikan masker yang tepat."
                )
            )
        ),

        // ==================== 🏦 البنك (Bank - Level 3) ====================
        MissionModel(
            id = "m_bank_1",
            locationId = "loc_bank",
            titleAr = "أريد فتح حساب",
            titleId = "Saya Ingin Buka Rekening",
            missionType = "RECOGNIZE",
            difficulty = "Easy",
            objective = "التعرف على مصطلحات البنك: حساب، بطاقة، نقود، توقيع.",
            xp = 25,
            orderNumber = 1,
            characterSpeakerId = "receptionist_01",
            characterNameAr = "المصرفي طارق",
            activities = listOf(
                ActivityModel(
                    id = "act_bk1_1",
                    activityType = "MULTIPLE_CHOICE",
                    speakerId = "receptionist_01",
                    speakerNameAr = "المصرفي طارق",
                    promptAr = "ماذا تسمى (Kartu ATM/Bank) باللغة العربية؟",
                    promptId = "Apa sebutan Kartu Bank dalam bahasa Arab?",
                    audioTextAr = "هَذِهِ بِطَاقَةُ الحِسَابِ المَصْرِفِيِّ.",
                    options = listOf("بِطَاقَة", "نُقُود", "تَوْقِيع", "اسْتِمَارَة"),
                    correctAnswer = "بِطَاقَة",
                    hint1 = "بطاقة بلاستيكية للسحب والدفع.",
                    hint2 = "بِطَاقَة (Bithāqah).",
                    hint3 = "بِطَاقَة",
                    explanationAr = "بطاقة تعني Kartu.",
                    explanationId = "Bithāqah (بطاقة) berarti kartu perbankan."
                )
            )
        ),
        MissionModel(
            id = "m_bank_2",
            locationId = "loc_bank",
            titleAr = "املأ هذه الاستمارة",
            titleId = "Isi Formulir Ini",
            missionType = "USE",
            difficulty = "Medium",
            objective = "فهم بيانات الاستمارة البنكية: الاسم، العمر، الجنسية، ورقم الهاتف.",
            xp = 40,
            orderNumber = 2,
            characterSpeakerId = "receptionist_01",
            characterNameAr = "المصرفي طارق",
            activities = listOf(
                ActivityModel(
                    id = "act_bk2_1",
                    activityType = "SENTENCE_BUILDER",
                    speakerId = "receptionist_01",
                    speakerNameAr = "المصرفي طارق",
                    promptAr = "قال الموظف: 'اكْتُبْ بَيَانَاتِكَ'. رتب: (Ini tanda tangan dan nomor telepon saya)",
                    promptId = "Susun: هَذَا تَوْقِيعِي وَرَقْمُ هَاتِفِي",
                    audioTextAr = "هَذَا تَوْقِيعِي وَرَقْمُ هَاتِفِي.",
                    wordChips = listOf("هَذَا", "تَوْقِيعِي", "وَرَقْمُ", "هَاتِفِي.", "الحِسَابِ"),
                    correctAnswer = "هَذَا تَوْقِيعِي وَرَقْمُ هَاتِفِي.",
                    hint1 = "توقيعي = tanda tangan saya.",
                    hint2 = "رقم هاتفي = nomor telepon saya.",
                    hint3 = "هَذَا تَوْقِيعِي وَرَقْمُ هَاتِفِي.",
                    explanationAr = "تم تسليم الاستمارة المكتملة بنجاح للمصرفي.",
                    explanationId = "Formulir dan data diserahkan dengan benar."
                )
            )
        ),
        MissionModel(
            id = "m_bank_3",
            locationId = "loc_bank",
            titleAr = "بطاقتي لا تعمل",
            titleId = "Kartu Saya Tidak Berfungsi",
            missionType = "PROBLEM_SOLVING",
            difficulty = "Hard",
            objective = "المشكلة: الصراف الآلي رفض بطاقتك! اطلب المساعدة لحل العطل التقني.",
            xp = 70,
            orderNumber = 3,
            characterSpeakerId = "receptionist_01",
            characterNameAr = "المصرفي طارق",
            activities = listOf(
                ActivityModel(
                    id = "act_bk3_1",
                    activityType = "SPEAKING",
                    speakerId = "receptionist_01",
                    speakerNameAr = "المصرفي طارق",
                    promptAr = "اشرح المشكلة للموظف: (Kartu saya tidak berfungsi, tolong bantu)",
                    promptId = "Ucapkan atau ketik: بِطَاقَتِي لاَ تَعْمَلُ، أُرِيدُ المُسَاعَدَةَ",
                    audioTextAr = "بِطَاقَتِي لاَ تَعْمَلُ، أُرِيدُ المُسَاعَدَةَ مِنْ فَضْلِكَ.",
                    correctAnswer = "بطاقتي لا تعمل أريد المساعدة",
                    wordChips = listOf("بِطَاقَتِي", "لاَ", "تَعْمَلُ،", "أُرِيدُ", "المُسَاعَدَةَ.", "شُكْرًا"),
                    hint1 = "لا تعمل = tidak berfungsi.",
                    hint2 = "أريد المساعدة = saya butuh bantuan.",
                    hint3 = "بِطَاقَتِي لاَ تَعْمَلُ، أُرِيدُ المُسَاعَدَةَ.",
                    explanationAr = "قام الموظف بإعادة تفعيل بطاقتك على الفور!",
                    explanationId = "Petugas bank langsung mengaktifkan kembali kartumu."
                )
            )
        ),

        // ==================== 🏤 مكتب البريد (Post Office - Level 3) ====================
        MissionModel(
            id = "m_post_1",
            locationId = "loc_post",
            titleAr = "أريد إرسال طرد",
            titleId = "Saya Mau Kirim Paket",
            missionType = "RECOGNIZE",
            difficulty = "Easy",
            objective = "التعرف على مصطلحات الشحن: طرد، عنوان، اسم، بريد.",
            xp = 25,
            orderNumber = 1,
            characterSpeakerId = "receptionist_01",
            characterNameAr = "موظف البريد",
            activities = listOf(
                ActivityModel(
                    id = "act_po1_1",
                    activityType = "MULTIPLE_CHOICE",
                    speakerId = "receptionist_01",
                    speakerNameAr = "موظف البريد",
                    promptAr = "ماذا تعني كلمة (طَرْد) في البريد؟",
                    promptId = "Apa arti kata 'طَرْد'?",
                    audioTextAr = "أُرِيدُ أَنْ أُرْسِلَ هَذَا الطَّرْدَ.",
                    options = listOf("Paket / Kargo", "Surat kabar", "Kunci rumah", "Buku catatan"),
                    correctAnswer = "Paket / Kargo",
                    hint1 = "صندوق مغلف يُرسل عبر البريد.",
                    hint2 = "طرد بريدي = Paket.",
                    hint3 = "طَرْد",
                    explanationAr = "طَرْد يعني الصندوق المرسل (Paket).",
                    explanationId = "Thard (طَرْد) berarti paket kargo."
                )
            )
        ),
        MissionModel(
            id = "m_post_2",
            locationId = "loc_post",
            titleAr = "عنوان المستلم",
            titleId = "Alamat Penerima",
            missionType = "USE",
            difficulty = "Medium",
            objective = "كتابة وتأكيد وجهة الطرد وعنوان المستلم في إندونيسيا.",
            xp = 40,
            orderNumber = 2,
            characterSpeakerId = "receptionist_01",
            characterNameAr = "موظف البريد",
            activities = listOf(
                ActivityModel(
                    id = "act_po2_1",
                    activityType = "SENTENCE_BUILDER",
                    speakerId = "receptionist_01",
                    speakerNameAr = "موظف البريد",
                    promptAr = "سألك الموظف: 'إِلَى أَيْنَ الإِرْسَالُ؟' أجب: (Saya ingin mengirim paket ini ke Jakarta)",
                    promptId = "Susun: أُرِيدُ إِرْسَالَ الطَّرْدِ إِلَى جَاكَرْتَا",
                    audioTextAr = "أُرِيدُ إِرْسَالَ الطَّرْدِ إِلَى جَاكَرْتَا.",
                    wordChips = listOf("أُرِيدُ", "إِرْسَالَ", "الطَّرْدِ", "إِلَى", "جَاكَرْتَا.", "اليَوْمَ"),
                    correctAnswer = "أُرِيدُ إِرْسَالَ الطَّرْدِ إِلَى جَاكَرْتَا.",
                    hint1 = "إرسال = pengiriman.",
                    hint2 = "إلى جاكرتا = ke Jakarta.",
                    hint3 = "أُرِيدُ إِرْسَالَ الطَّرْدِ إِلَى جَاكَرْتَا.",
                    explanationAr = "تم تسجيل وجهة الشحنة الدولية بنجاح.",
                    explanationId = "Tujuan pengiriman internasional tercatat."
                )
            )
        ),
        MissionModel(
            id = "m_post_3",
            locationId = "loc_post",
            titleAr = "الطرد المستعجل",
            titleId = "Paket Ekspres",
            missionType = "PROBLEM_SOLVING",
            difficulty = "Hard",
            objective = "الموقف: تحتاج لوصول الطرد خلال يومين! اختر بين البريد العادي والبريد المستعجل.",
            xp = 70,
            orderNumber = 3,
            characterSpeakerId = "receptionist_01",
            characterNameAr = "موظف البريد",
            activities = listOf(
                ActivityModel(
                    id = "act_po3_1",
                    activityType = "DECISION_BRANCH",
                    speakerId = "receptionist_01",
                    speakerNameAr = "موظف البريد",
                    promptAr = "خيرك الموظف: 'عَادِيّ أَمْ سَرِيعٌ مُسْتَعْجَلٌ؟' ماذا تختار لتصل سريعاً؟",
                    promptId = "Pilihan pengiriman reguler atau ekspres cepat?",
                    audioTextAr = "هَلْ تُرِيدُ بَرِيدًا عَادِيًّا أَمْ بَرِيدًا سَرِيعًا؟",
                    options = listOf(
                        "أُرِيدُ البَرِيدَ السَّرِيعَ المُسْتَعْجَلَ لِأَنَّهُ مَهِمٌّ جِدًّا",
                        "أريد البريد العادي البطيء جداً",
                        "لا أريد إرسال أي شيء الآن"
                    ),
                    correctAnswer = "أُرِيدُ البَرِيدَ السَّرِيعَ المُسْتَعْجَلَ لِأَنَّهُ مَهِمٌّ جِدًّا",
                    hint1 = "البريد السريع = kilat khusus / ekspres.",
                    hint2 = "مستعجل = mendesak.",
                    hint3 = "أريد البريد السريع المستعجل.",
                    explanationAr = "تمت معالجة الشحنة بنظام البريد السريع الممتاز.",
                    explanationId = "Paket dikirim dengan layanan kilat ekspres."
                )
            )
        ),

        // ==================== 🚔 مركز الشرطة (Police Station - Level 3) ====================
        MissionModel(
            id = "m_police_1",
            locationId = "loc_police",
            titleAr = "فقدت محفظتي",
            titleId = "Dompet Saya Hilang",
            missionType = "RECOGNIZE",
            difficulty = "Easy",
            objective = "تعلم كلمات المقتنيات الشخصية: محفظة، هاتف، مفتاح، حقيبة، بطاقة.",
            xp = 25,
            orderNumber = 1,
            characterSpeakerId = "police_male_01",
            characterNameAr = "الضابط خالد",
            activities = listOf(
                ActivityModel(
                    id = "act_pl1_1",
                    activityType = "MULTIPLE_CHOICE",
                    speakerId = "police_male_01",
                    speakerNameAr = "الضابط خالد",
                    promptAr = "ماذا تسمى (Dompet tempat uang dan kartu)?",
                    promptId = "Apa bahasa Arab untuk Dompet?",
                    audioTextAr = "لَقَدْ فَقَدْتُ مِحْفَظَتِي فِي السُّوقِ.",
                    options = listOf("مِحْفَظَة", "هَاتِف", "حَقِيبَة", "سَيَّارَة"),
                    correctAnswer = "مِحْفَظَة",
                    hint1 = "تحفظ فيها النقود والبطاقات الشخصية.",
                    hint2 = "مِحْفَظَة (Mihfadzah).",
                    hint3 = "مِحْفَظَة",
                    explanationAr = "المحفظة تحفظ الأموال والهوية.",
                    explanationId = "Mihfadzah (محفظة) berarti dompet."
                )
            )
        ),
        MissionModel(
            id = "m_police_2",
            locationId = "loc_police",
            titleAr = "أين فقدتها؟",
            titleId = "Di Mana Hilangnya?",
            missionType = "USE",
            difficulty = "Medium",
            objective = "تحديد موقع وتوقيت الفقدان: في الحافلة، المطعم، أو محطة القطار.",
            xp = 40,
            orderNumber = 2,
            characterSpeakerId = "police_male_01",
            characterNameAr = "الضابط خالد",
            activities = listOf(
                ActivityModel(
                    id = "act_pl2_1",
                    activityType = "SENTENCE_BUILDER",
                    speakerId = "police_male_01",
                    speakerNameAr = "الضابط خالد",
                    promptAr = "سألك الضابط: 'أَيْنَ فَقَدْتَهَا؟' أجب: (Saya kehilangan dompet di dalam bus)",
                    promptId = "Susun kalimat: فَقَدْتُهَا فِي الحَافِلَةِ صَبَاحًا",
                    audioTextAr = "فَقَدْتُهَا فِي الحَافِلَةِ صَبَاحًا.",
                    wordChips = listOf("فَقَدْتُهَا", "فِي", "الحَافِلَةِ", "صَبَاحًا.", "المَسْجِدِ"),
                    correctAnswer = "فَقَدْتُهَا فِي الحَافِلَةِ صَبَاحًا.",
                    hint1 = "فقدتها = saya menghilangkannya.",
                    hint2 = "في الحافلة = di dalam bus.",
                    hint3 = "فَقَدْتُهَا فِي الحَافِلَةِ صَبَاحًا.",
                    explanationAr = "تحديد دقيق للمكان والزمان في محضر الشرطة.",
                    explanationId = "Lokasi dan waktu kehilangan tercatat jelas."
                )
            )
        ),
        MissionModel(
            id = "m_police_3",
            locationId = "loc_police",
            titleAr = "كيف شكل المحفظة؟",
            titleId = "Bagaimana Ciri Dompetnya?",
            missionType = "PROBLEM_SOLVING",
            difficulty = "Hard",
            objective = "وصف دقيق للمحفظة: سوداء وصغيرة، وبداخلها البطاقة الجامعية.",
            xp = 70,
            orderNumber = 3,
            characterSpeakerId = "police_male_01",
            characterNameAr = "الضابط خالد",
            activities = listOf(
                ActivityModel(
                    id = "act_pl3_1",
                    activityType = "SPEAKING",
                    speakerId = "police_male_01",
                    speakerNameAr = "الضابط خالد",
                    promptAr = "صف المحفظة للشرطي: (Dompet saya hitam kecil dan ada kartu)",
                    promptId = "Ucapkan atau ketik: مِحْفَظَتِي سَوْدَاءُ وَصَغِيرَةٌ",
                    audioTextAr = "مِحْفَظَتِي سَوْدَاءُ وَصَغِيرَةٌ، فِيهَا بِطَاقَتِي.",
                    correctAnswer = "محفظتي سوداء وصغيرة فيها بطاقتي",
                    wordChips = listOf("مِحْفَظَتِي", "سَوْدَاءُ", "وَصَغِيرَةٌ،", "فِيهَا", "بِطَاقَتِي.", "كَبِيرَةٌ"),
                    hint1 = "سوداء = hitam.",
                    hint2 = "صغيرة = kecil.",
                    hint3 = "مِحْفَظَتِي سَوْدَاءُ وَصَغِيرَةٌ، فِيهَا بِطَاقَتِي.",
                    explanationAr = "ابتسم الضابط وفتح الدرج وأخرج محفظتك بعد أن وجدها سائق الحافلة الأمين!",
                    explanationId = "Polisi tersenyum dan menyerahkan dompetmu yang dititipkan sopir bus yang jujur!"
                )
            )
        ),

        // ==================== 🚕 سيارة الأجرة (Taxi - Level 4) ====================
        MissionModel(
            id = "m_taxi_1",
            locationId = "loc_taxi",
            titleAr = "إلى أين تريد الذهاب؟",
            titleId = "Mau Pergi ke Mana?",
            missionType = "RECOGNIZE",
            difficulty = "Easy",
            objective = "التحدث مع سائق التاكسي وتحديد الوجهة الجامعية.",
            xp = 25,
            orderNumber = 1,
            characterSpeakerId = "driver_male_01",
            characterNameAr = "السائق أبو فهد",
            activities = listOf(
                ActivityModel(
                    id = "act_tx1_1",
                    activityType = "SENTENCE_BUILDER",
                    speakerId = "driver_male_01",
                    speakerNameAr = "السائق أبو فهد",
                    promptAr = "سألك السائق: 'إِلَى أَيْنَ؟' قل له: (Saya mau pergi ke kampus)",
                    promptId = "Susun: أُرِيدُ الذَّهَابَ إِلَى الجَامِعَةِ",
                    audioTextAr = "أُرِيدُ الذَّهَابَ إِلَى الجَامِعَةِ مِنْ فَضْلِكَ.",
                    wordChips = listOf("أُرِيدُ", "الذَّهَابَ", "إِلَى", "الجَامِعَةِ", "مِنْ", "فَضْلِكَ."),
                    correctAnswer = "أُرِيدُ الذَّهَابَ إِلَى الجَامِعَةِ مِنْ فَضْلِكَ.",
                    hint1 = "الذهاب = pergi.",
                    hint2 = "إلى الجامعة = ke kampus.",
                    hint3 = "أُرِيدُ الذَّهَابَ إِلَى الجَامِعَةِ مِنْ فَضْلِكَ.",
                    explanationAr = "تحديد واضح ومباشر للوجهة لسائق الأجرة.",
                    explanationId = "Menyebutkan tujuan perjalanan kepada pengemudi taksi."
                )
            )
        ),
        MissionModel(
            id = "m_taxi_2",
            locationId = "loc_taxi",
            titleAr = "كم الأجرة؟",
            titleId = "Berapa Ongkosnya?",
            missionType = "USE",
            difficulty = "Medium",
            objective = "الاتفاق على الأجرة بالريال لعدد شخصين.",
            xp = 40,
            orderNumber = 2,
            characterSpeakerId = "driver_male_01",
            characterNameAr = "السائق أبو فهد",
            activities = listOf(
                ActivityModel(
                    id = "act_tx2_1",
                    activityType = "LISTENING",
                    speakerId = "driver_male_01",
                    speakerNameAr = "السائق أبو فهد",
                    promptAr = "استمع لقيمة الأجرة التي حددها السائق:",
                    promptId = "Dengarkan besaran tarif taksi:",
                    audioTextAr = "الأُجْرَةُ عِشْرُونَ رِيَالاً فَقَطْ.",
                    options = listOf("Dua puluh riyal (20)", "Sepuluh riyal (10)", "Lima puluh riyal (50)", "Lima riyal (5)"),
                    correctAnswer = "Dua puluh riyal (20)",
                    hint1 = "استمع إلى 'عشرون ريالاً'.",
                    hint2 = "عشرون = 20.",
                    hint3 = "الأجرة عشرون ريالاً فقط.",
                    explanationAr = "الأجرة المتفق عليها 20 ريالاً.",
                    explanationId = "Tarif yang disepakati adalah 20 riyal."
                )
            )
        ),
        MissionModel(
            id = "m_taxi_3",
            locationId = "loc_taxi",
            titleAr = "هذا ليس الطريق",
            titleId = "Ini Bukan Jalannya",
            missionType = "PROBLEM_SOLVING",
            difficulty = "Hard",
            objective = "انعطف السائق نحو طريق فرعي! نبهه بأدب وصحح له مسار الطريق إلى بوابة الجامعة.",
            xp = 70,
            orderNumber = 3,
            characterSpeakerId = "driver_male_01",
            characterNameAr = "السائق أبو فهد",
            activities = listOf(
                ActivityModel(
                    id = "act_tx3_1",
                    activityType = "SPEAKING",
                    speakerId = "driver_male_01",
                    speakerNameAr = "السائق أبو فهد",
                    promptAr = "نبه السائق بلباقة: (Permisi paman, ini bukan jalan ke kampus)",
                    promptId = "Ucapkan atau ketik: عَفْوًا، هَذَا لَيْسَ الطَّرِيقَ إِلَى الجَامِعَةِ",
                    audioTextAr = "عَفْوًا، هَذَا لَيْسَ الطَّرِيقَ إِلَى الجَامِعَةِ.",
                    correctAnswer = "عفوا هذا ليس الطريق إلى الجامعة",
                    wordChips = listOf("عَفْوًا،", "هَذَا", "لَيْسَ", "الطَّرِيقَ", "إِلَى", "الجَامِعَةِ.", "نَعَمْ"),
                    hint1 = "ليس الطريق = bukan jalannya.",
                    hint2 = "إلى الجامعة = menuju kampus.",
                    hint3 = "عَفْوًا، هَذَا لَيْسَ الطَّرِيقَ إِلَى الجَامِعَةِ.",
                    explanationAr = "انتبه السائق فوراً وشكرك قائلاً: 'معذرة! سأرجع للطريق الرئيسي!'. ونلت وسام المسافر الذكي!",
                    explanationId = "Sopir menyadari kekeliruan dan kembali ke jalan raya menuju kampus. Lencana Pelancong Cerdas terbuka!"
                )
            )
        ),

        // ==================== ✈️ المطار (Airport - Level 4) ====================
        MissionModel(
            id = "m_air_1",
            locationId = "loc_airport",
            titleAr = "لوحة المغادرة",
            titleId = "Papan Keberangkatan",
            missionType = "RECOGNIZE",
            difficulty = "Easy",
            objective = "قراءة لوحة الرحلات: رقم الرحلة 205 إلى جاكرتا، البوابة 7، الساعة 14:30.",
            xp = 25,
            orderNumber = 1,
            characterSpeakerId = "receptionist_01",
            characterNameAr = "موظف المطار",
            activities = listOf(
                ActivityModel(
                    id = "act_ap1_1",
                    activityType = "MULTIPLE_CHOICE",
                    speakerId = "receptionist_01",
                    speakerNameAr = "موظف المطار",
                    promptAr = "ماذا تسمى (Gerbang Keberangkatan Pesawat)?",
                    promptId = "Apa bahasa Arab untuk Pintu Gerbang Bandara?",
                    audioTextAr = "التَّوَجُّهُ إِلَى البَوَّابَةِ رَقْمِ سَبْعَةٍ.",
                    options = listOf("بَوَّابَة", "طَائِرَة", "تَذْكِرَة", "جَوَاز"),
                    correctAnswer = "بَوَّابَة",
                    hint1 = "الممر النهائي للصعود للطائرة.",
                    hint2 = "بَوَّابَة (Bawwābah).",
                    hint3 = "بَوَّابَة",
                    explanationAr = "البوابة (Gate) في المطار.",
                    explanationId = "Bawwābah (بوابة) adalah pintu gerbang boarding."
                )
            )
        ),
        MissionModel(
            id = "m_air_2",
            locationId = "loc_airport",
            titleAr = "إعلان المطار الصوتي",
            titleId = "Pengumuman Bandara",
            missionType = "USE",
            difficulty = "Medium",
            objective = "الاستماع لإعلان الصعود للرحلة وفهم رقم البوابة.",
            xp = 40,
            orderNumber = 2,
            characterSpeakerId = "receptionist_01",
            characterNameAr = "مذيع المطار",
            activities = listOf(
                ActivityModel(
                    id = "act_ap2_1",
                    activityType = "LISTENING",
                    speakerId = "receptionist_01",
                    speakerNameAr = "مذيع المطار",
                    promptAr = "استمع للنداء الأخير لرحلة جاكرتا:",
                    promptId = "Dengarkan pengumuman boarding:",
                    audioTextAr = "الرِّحْلَةُ رَقْمُ 205 إِلَى جَاكَرْتَا، الصُّعُودُ الآنَ مِنَ البَوَّابَةِ 7.",
                    options = listOf(
                        "Penerbangan 205 ke Jakarta boarding di Gate 7",
                        "Penerbangan dibatalkan karena cuaca buruk",
                        "التوجه إلى قسم استلام الحقائب",
                        "رحلة القاهرة من البوابة 2"
                    ),
                    correctAnswer = "Penerbangan 205 ke Jakarta boarding di Gate 7",
                    hint1 = "رحلة 205 إلى جاكرتا.",
                    hint2 = "البوابة 7.",
                    hint3 = "الرحلة رقم 205 إلى جاكرتا من البوابة 7.",
                    explanationAr = "فهم ممتاز للإعلان الصوتي الرسمي في المطار.",
                    explanationId = "Pemahaman sempurna terhadap siaran panggilan naik pesawat."
                )
            )
        ),
        MissionModel(
            id = "m_air_3",
            locationId = "loc_airport",
            titleAr = "جواز سفري!",
            titleId = "Paspor Saya!",
            missionType = "PROBLEM_SOLVING",
            difficulty = "Hard",
            objective = "نسيت جواز سفرك عند كاونتر وزن الحقائب! تواصل بسرعة لاستعادته قبل إغلاق البوابة.",
            xp = 70,
            orderNumber = 3,
            characterSpeakerId = "receptionist_01",
            characterNameAr = "موظف الجوازات",
            activities = listOf(
                ActivityModel(
                    id = "act_ap3_1",
                    activityType = "SPEAKING",
                    speakerId = "receptionist_01",
                    speakerNameAr = "موظف الجوازات",
                    promptAr = "قل للموظف بسرعة: (Paspor saya tertinggal di konter)",
                    promptId = "Ucapkan atau ketik: نَسِيتُ جَوَازَ سَفَرِي عِنْدَ المَكْتَبِ",
                    audioTextAr = "نَسِيتُ جَوَازَ سَفَرِي عِنْدَ المَكْتَبِ، سَاعِدْنِي!",
                    correctAnswer = "نسيت جواز سفري عند المكتب ساعدني",
                    wordChips = listOf("نَسِيتُ", "جَوَازَ", "سَفَرِي", "عِنْدَ", "المَكْتَبِ،", "سَاعِدْنِي!", "أَيْنَ"),
                    hint1 = "جواز سفري = paspor saya.",
                    hint2 = "ساعدني = tolong bantu saya.",
                    hint3 = "نَسِيتُ جَوَازَ سَفَرِي عِنْدَ المَكْتَبِ، سَاعِدْنِي!",
                    explanationAr = "اتصل الموظف بالزملاء وأحضر الجواز في دقيقتين وصعدت الطائرة بنجاح!",
                    explanationId = "Petugas segera berkoordinasi dan paspor diserahkan tepat waktu. Lencana Musafir Siap terbuka!"
                )
            )
        ),

        // ==================== 🕌 المسجد (Mosque - Level 5) ====================
        MissionModel(
            id = "m_mosq_1",
            locationId = "loc_mosque",
            titleAr = "مكان الوضوء",
            titleId = "Tempat Wudhu",
            missionType = "RECOGNIZE",
            difficulty = "Easy",
            objective = "التحية الإسلامية والسؤال عن مكان الوضوء والمصلى.",
            xp = 25,
            orderNumber = 1,
            characterSpeakerId = "teacher_male_01",
            characterNameAr = "المصلّي عمر",
            activities = listOf(
                ActivityModel(
                    id = "act_mq1_1",
                    activityType = "SENTENCE_BUILDER",
                    speakerId = "teacher_male_01",
                    speakerNameAr = "المصلّي عمر",
                    promptAr = "اسأل أحد المصلين: (Permisi, di mana tempat wudhu?)",
                    promptId = "Susun: عَفْوًا، أَيْنَ مَكَانُ الوُضُوءِ؟",
                    audioTextAr = "عَفْوًا، أَيْنَ مَكَانُ الوُضُوءِ؟",
                    wordChips = listOf("عَفْوًا،", "أَيْنَ", "مَكَانُ", "الوُضُوءِ؟", "المَسْجِدِ"),
                    correctAnswer = "عَفْوًا، أَيْنَ مَكَانُ الوُضُوءِ؟",
                    hint1 = "مكان الوضوء = tempat wudhu.",
                    hint2 = "أين = di mana.",
                    hint3 = "عَفْوًا، أَيْنَ مَكَانُ الوُضُوءِ؟",
                    explanationAr = "أشار إليك قائلاً: 'مَكَانُ الوُضُوءِ خَلْفَ المَسْجِدِ'.",
                    explanationId = "Tempat wudhu berada di belakang masjid."
                )
            )
        ),
        MissionModel(
            id = "m_mosq_2",
            locationId = "loc_mosque",
            titleAr = "آداب المسجد",
            titleId = "Adab di Masjid",
            missionType = "USE",
            difficulty = "Medium",
            objective = "الاستماع لنداء الأذان وتطبيق آداب الهدوء والسكينة داخل بيت الله.",
            xp = 40,
            orderNumber = 2,
            characterSpeakerId = "teacher_male_01",
            characterNameAr = "المؤذن",
            activities = listOf(
                ActivityModel(
                    id = "act_mq2_1",
                    activityType = "LISTENING",
                    speakerId = "teacher_male_01",
                    speakerNameAr = "المؤذن",
                    promptAr = "استمع لتوجيه الإمام قبل إقامة الصلاة:",
                    promptId = "Dengarkan arahan imam sebelum shalat:",
                    audioTextAr = "اسْتَوُوا وَاعْتَدِلُوا وَأَغْلِقُوا الهَوَاتِفَ مِنْ فَضْلِكُمْ.",
                    options = listOf(
                        "Luruskan shaf dan matikan ponsel tolong",
                        "الخروج إلى الفناء الخارجي",
                        "توزيع الكتب والمصاحف",
                        "بدء المحاضرة الفقهية"
                    ),
                    correctAnswer = "Luruskan shaf dan matikan ponsel tolong",
                    hint1 = "استووا واعتدلوا = luruskan dan rapatkan.",
                    hint2 = "أغلقوا الهواتف = matikan telepon.",
                    hint3 = "استووا واعتدلوا وأغلقوا الهواتف.",
                    explanationAr = "توجيه نبوي شريف لتسوية الصفوف والخشوع.",
                    explanationId = "Perintah merapatkan barisan dan menjaga kekhusyukan."
                )
            )
        ),
        MissionModel(
            id = "m_mosq_3",
            locationId = "loc_mosque",
            titleAr = "هل يمكنك مساعدتي؟",
            titleId = "Bisakah Membantu Saya?",
            missionType = "PROBLEM_SOLVING",
            difficulty = "Hard",
            objective = "رجل مسن طلب منك المساعدة في حمل صندوق المصاحف الثقيل! استجب بلباقة.",
            xp = 70,
            orderNumber = 3,
            characterSpeakerId = "teacher_male_01",
            characterNameAr = "الشيخ عبد الله",
            activities = listOf(
                ActivityModel(
                    id = "act_mq3_1",
                    activityType = "SPEAKING",
                    speakerId = "teacher_male_01",
                    speakerNameAr = "الشيخ عبد الله",
                    promptAr = "قال لك: 'هَلْ تُسَاعِدُنِي يَا بُنَيَّ؟' قل له: (Tentu dengan senang hati, mari saya bantu)",
                    promptId = "Ucapkan atau ketik: نَعَمْ بِكُلِّ سُرُورٍ، أُسَاعِدُكَ",
                    audioTextAr = "نَعَمْ بِكُلِّ سُرُورٍ، سَأُسَاعِدُكَ يَا عَمِّي!",
                    correctAnswer = "نعم بكل سرور سأساعدك",
                    wordChips = listOf("نَعَمْ", "بِكُلِّ", "سُرُورٍ،", "سَأُسَاعِدُكَ", "يَا", "عَمِّي!", "لاَ"),
                    hint1 = "بكل سرور = dengan senang hati.",
                    hint2 = "سأساعدك = saya akan membantumu.",
                    hint3 = "نَعَمْ بِكُلِّ سُرُورٍ، سَأُسَاعِدُكَ يَا عَمِّي!",
                    explanationAr = "دعا لك الشيخ بالبركة والتوفيق ونلت وسام المتواصل الجيد!",
                    explanationId = "Orang tua tersebut mendoakan kebaikan untukmu. Lencana Komunikator Baik terbuka!"
                )
            )
        ),

        // ==================== ☕ المقهى (Cafe - Level 5) ====================
        MissionModel(
            id = "m_cafe_1",
            locationId = "loc_cafe",
            titleAr = "ماذا تريد أن تشرب؟",
            titleId = "Mau Minum Apa?",
            missionType = "RECOGNIZE",
            difficulty = "Easy",
            objective = "تعرف على مشروبات المقهى: قهوة، شاي، حليب، عصير، ماء.",
            xp = 25,
            orderNumber = 1,
            characterSpeakerId = "waiter_male_01",
            characterNameAr = "باريستا المقهى",
            activities = listOf(
                ActivityModel(
                    id = "act_cf1_1",
                    activityType = "MULTIPLE_CHOICE",
                    speakerId = "waiter_male_01",
                    speakerNameAr = "باريستا المقهى",
                    promptAr = "ماذا تعني كلمة (قَهْوَة)؟",
                    promptId = "Apa arti 'قَهْوَة'?",
                    audioTextAr = "أُرِيدُ قَهْوَةً عَرَبِيَّةً سَاخِنَةً.",
                    options = listOf("Kopi", "Teh", "Susu", "Jus"),
                    correctAnswer = "Kopi",
                    hint1 = "مشروب ساخن من البن المحمص.",
                    hint2 = "قَهْوَة (Qahwah).",
                    hint3 = "قَهْوَة",
                    explanationAr = "القهوة العربية الأصيلة بالهيل.",
                    explanationId = "Qahwah (قهوة) berarti kopi."
                )
            )
        ),
        MissionModel(
            id = "m_cafe_2",
            locationId = "loc_cafe",
            titleAr = "لقاء صديق",
            titleId = "Bertemu Teman",
            missionType = "USE",
            difficulty = "Medium",
            objective = "التحاور الودي مع زميل قديم: كيف حالك؟ أنا بخير، وماذا تفعل هنا؟",
            xp = 40,
            orderNumber = 2,
            characterSpeakerId = "friend_male_01",
            characterNameAr = "الصديق بلال",
            activities = listOf(
                ActivityModel(
                    id = "act_cf2_1",
                    activityType = "SENTENCE_BUILDER",
                    speakerId = "friend_male_01",
                    speakerNameAr = "الصديق بلال",
                    promptAr = "حياك صديقك: 'كَيْفَ حَالُكَ يَا صَدِيقِي؟' رتب: (Alhamdulillah saya baik-baik saja)",
                    promptId = "Susun: أَنَا بِخَيْرٍ، الحَمْدُ لِلَّهِ",
                    audioTextAr = "أَنَا بِخَيْرٍ، الحَمْدُ لِلَّهِ. وَكَيْفَ حَالُكَ أَنْتَ؟",
                    wordChips = listOf("أَنَا", "بِخَيْرٍ،", "الحَمْدُ", "لِلَّهِ.", "وَأَنْتَ؟", "تَعْبَانٌ"),
                    correctAnswer = "أَنَا بِخَيْرٍ، الحَمْدُ لِلَّهِ.",
                    hint1 = "أنا بخير = saya baik.",
                    hint2 = "الحمد لله = puji syukur kepada Allah.",
                    hint3 = "أَنَا بِخَيْرٍ، الحَمْدُ لِلَّهِ.",
                    explanationAr = "رد ودود واجتماعي طبيعي بين الأصدقاء.",
                    explanationId = "Jawaban hangat atas sapaan sahabat karib."
                )
            )
        ),
        MissionModel(
            id = "m_cafe_3",
            locationId = "loc_cafe",
            titleAr = "الحساب من فضلك",
            titleId = "Minta Bon dan Bayar",
            missionType = "PROBLEM_SOLVING",
            difficulty = "Hard",
            objective = "أردت أن تدفع الحساب كضيافة لصديقك! اطلب الفاتورة وادفع 20 ريالاً.",
            xp = 70,
            orderNumber = 3,
            characterSpeakerId = "waiter_male_01",
            characterNameAr = "باريستا المقهى",
            activities = listOf(
                ActivityModel(
                    id = "act_cf3_1",
                    activityType = "SPEAKING",
                    speakerId = "waiter_male_01",
                    speakerNameAr = "باريستا المقهى",
                    promptAr = "نادي النادل واطلب الفاتورة: (Bon/tagihannya tolong)",
                    promptId = "Ucapkan atau ketik: الحِسَابُ مِنْ فَضْلِكَ",
                    audioTextAr = "الحِسَابُ مِنْ فَضْلِكَ.",
                    correctAnswer = "الحساب من فضلك",
                    wordChips = listOf("الحِسَابُ", "مِنْ", "فَضْلِكَ.", "كَمْ", "السِّعْرُ"),
                    hint1 = "الحساب = bon/tagihan.",
                    hint2 = "من فضلك = tolong.",
                    hint3 = "الحِسَابُ مِنْ فَضْلِكَ.",
                    explanationAr = "سلمك النادل الفاتورة بقيمة 20 ريالاً ونلت وسام الزبون الذكي!",
                    explanationId = "Pelayan memberikan struk dan kamu membayar dengan ramah. Lencana Pelanggan Cerdas terbuka!"
                )
            )
        ),

        // ==================== 🏙️ وسط المدينة (Downtown - Level 5) ====================
        MissionModel(
            id = "m_dt_1",
            locationId = "loc_downtown",
            titleAr = "خريطة المدينة",
            titleId = "Peta Pusat Kota",
            missionType = "RECOGNIZE",
            difficulty = "Easy",
            objective = "تتبع مسار وسط المدينة: المكتبة -> المقهى -> البنك.",
            xp = 30,
            orderNumber = 1,
            characterSpeakerId = "friend_male_01",
            characterNameAr = "المرشد فهد",
            activities = listOf(
                ActivityModel(
                    id = "act_dt1_1",
                    activityType = "MULTIPLE_CHOICE",
                    speakerId = "friend_male_01",
                    speakerNameAr = "المرشد فهد",
                    promptAr = "ما هو الترتيب الصحيح لخط سير الجولة؟",
                    promptId = "Urutan rute perjalanan:",
                    audioTextAr = "نَبْدَأُ مِنَ المَكْتَبَةِ، ثُمَّ المَقْهَى، ثُمَّ البَنْكِ.",
                    options = listOf(
                        "Perpustakaan -> Kafe -> Bank",
                        "Bank -> Rumah sakit -> Sekolah",
                        "Bandara -> Hotel -> Taksi",
                        "Pasar -> Masjid -> Rumah"
                    ),
                    correctAnswer = "Perpustakaan -> Kafe -> Bank",
                    hint1 = "نبدأ من المكتبة.",
                    hint2 = "ثم المقهى ثم البنك.",
                    hint3 = "المكتبة -> المقهى -> البنك.",
                    explanationAr = "مسار ممتاز لاستكشاف معالم وسط المدينة.",
                    explanationId = "Rute menjelajahi landmark pusat kota."
                )
            )
        ),
        MissionModel(
            id = "m_dt_2",
            locationId = "loc_downtown",
            titleAr = "تغيير الخطة",
            titleId = "Perubahan Rencana",
            missionType = "USE",
            difficulty = "Medium",
            objective = "اتصل صديقك ليغير موعد اللقاء إلى المقهى مباشرة! اختر الرد البديل المناسب.",
            xp = 50,
            orderNumber = 2,
            characterSpeakerId = "friend_male_01",
            characterNameAr = "الصديق بلال",
            activities = listOf(
                ActivityModel(
                    id = "act_dt2_1",
                    activityType = "DECISION_BRANCH",
                    speakerId = "friend_male_01",
                    speakerNameAr = "الصديق بلال",
                    promptAr = "قال بلال: 'هَلْ نَذْهَبُ إِلَى المَقْهَى مُبَاشَرَةً؟' أي رد سياقي صحيح تختاره؟",
                    promptId = "Bilal mengusulkan langsung ke kafe. Pilih respon yang santun dan tepat:",
                    audioTextAr = "هَلْ نَذْهَبُ إِلَى المَقْهَى مُبَاشَرَةً؟",
                    options = listOf(
                        "نَعَمْ، فِكْرَةٌ مُمْتَازَةٌ! هَيَّا بِنَا!",
                        "عَفْوًا، أُرِيدُ الذَّهَابَ إِلَى المَكْتَبَةِ أَوَّلاً",
                        "أَنَا لاَ أُحِبُّكَ وَلَنْ أَلْتَقِيَ بِكَ"
                    ),
                    correctAnswer = "نَعَمْ، فِكْرَةٌ مُمْتَازَةٌ! هَيَّا بِنَا!",
                    hint1 = "فكرة ممتازة = ide bagus!",
                    hint2 = "هيا بنا = ayo kita berangkat.",
                    hint3 = "نَعَمْ، فِكْرَةٌ مُمْتَازَةٌ! هَيَّا بِنَا!",
                    explanationAr = "تواصل مرن وتفاعل سريع ومرح مع اقتراح الصديق.",
                    explanationId = "Respon fleksibel menyambut usul sahabat."
                )
            )
        ),
        MissionModel(
            id = "m_dt_3",
            locationId = "loc_downtown",
            titleAr = "يوم مزدحم",
            titleId = "Hari yang Sibuk",
            missionType = "PROBLEM_SOLVING",
            difficulty = "Hard",
            objective = "إنجاز 4 مهام: البنك -> المتجر -> المقهى -> صلاة المغرب في المسجد قبل غروب الشمس!",
            xp = 120,
            orderNumber = 3,
            characterSpeakerId = "friend_male_01",
            characterNameAr = "المرشد فهد",
            activities = listOf(
                ActivityModel(
                    id = "act_dt3_1",
                    activityType = "SPEAKING",
                    speakerId = "friend_male_01",
                    speakerNameAr = "المرشد فهد",
                    promptAr = "حان وقت المغرب! قل لزميلك: (Ayo kita ke masjid untuk sholat)",
                    promptId = "Ucapkan atau ketik: هَيَّا بِنَا إِلَى المَسْجِدِ لِلصَّلاَةِ",
                    audioTextAr = "هَيَّا بِنَا إِلَى المَسْجِدِ لِلصَّلاَةِ!",
                    correctAnswer = "هيا بنا إلى المسجد للصلاة",
                    wordChips = listOf("هَيَّا", "بِنَا", "إِلَى", "المَسْجِدِ", "لِلصَّلاَةِ!", "السُّوقِ"),
                    hint1 = "هيا بنا = ayo kita.",
                    hint2 = "إلى المسجد = ke masjid.",
                    hint3 = "هَيَّا بِنَا إِلَى المَسْجِدِ لِلصَّلاَةِ!",
                    explanationAr = "أتممت يوماً حافلاً باللغة العربية ونلت وسام مستكشف المدينة!",
                    explanationId = "Menuntaskan rute kota dengan sukses dan meraih Lencana Penjelajah Kota!"
                )
            )
        )
    )
}
