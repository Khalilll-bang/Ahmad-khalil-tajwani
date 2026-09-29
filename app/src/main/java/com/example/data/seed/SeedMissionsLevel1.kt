package com.example.data.seed

import com.example.data.model.ActivityModel
import com.example.data.model.MissionModel

object SeedMissionsLevel1 {
    val missions = listOf(
        // ==================== 🏠 البيت (House) ====================
        MissionModel(
            id = "m_house_1",
            locationId = "loc_house",
            titleAr = "أشياء في البيت",
            titleId = "Benda-benda di Rumah",
            missionType = "RECOGNIZE",
            difficulty = "Easy",
            objective = "تعرف على أسماء الأثاث والأشياء اليومية داخل المنزل.",
            xp = 20,
            orderNumber = 1,
            characterSpeakerId = "teacher_female_01",
            characterNameAr = "الأستاذة فاطمة",
            activities = listOf(
                ActivityModel(
                    id = "act_h1_1",
                    activityType = "MULTIPLE_CHOICE",
                    speakerId = "teacher_female_01",
                    speakerNameAr = "الأستاذة فاطمة",
                    promptAr = "ما هذا؟ (أجلس عليه لأستريح)",
                    promptId = "Apa ini? (Sesuatu untuk duduk)",
                    audioTextAr = "ما هذا الشيء في الغرفة؟",
                    options = listOf("كُرْسِيّ", "بَاب", "سَرِير", "حَقِيبَة"),
                    correctAnswer = "كُرْسِيّ",
                    hint1 = "شيء له أربع أرجل تجلس عليه.",
                    hint2 = "مفردة تبدأ بحرف الكاف.",
                    hint3 = "كُرْ + سِيّ",
                    explanationAr = "هذا كُرْسِيّ، يُستخدم للجلوس.",
                    explanationId = "Ini adalah kursi (كُرْسِيّ), digunakan untuk duduk."
                ),
                ActivityModel(
                    id = "act_h1_2",
                    activityType = "MULTIPLE_CHOICE",
                    speakerId = "teacher_female_01",
                    speakerNameAr = "الأستاذة فاطمة",
                    promptAr = "أين تضع كتبك وأقلامك للمدرسة؟",
                    promptId = "Di mana kamu meletakkan buku dan pensil untuk ke sekolah?",
                    audioTextAr = "أضع كتبي في الحقيبة.",
                    options = listOf("حَقِيبَة", "نَافِذَة", "بَاب", "مِفْتَاح"),
                    correctAnswer = "حَقِيبَة",
                    hint1 = "تحملها على ظهرك أو في يدك.",
                    hint2 = "تبدأ بحرف الحاء.",
                    hint3 = "حَقِيبَة (Tas)",
                    explanationAr = "الحقيبة تحفظ الكتب والأدوات.",
                    explanationId = "Haqibah (حَقِيبَة) berarti tas."
                )
            )
        ),
        MissionModel(
            id = "m_house_2",
            locationId = "loc_house",
            titleAr = "أين الأشياء؟",
            titleId = "Di Mana Letak Benda?",
            missionType = "USE",
            difficulty = "Medium",
            objective = "استخدم حروف الجر (على، تحت، أمام، خلف، بجانب) لتحديد أماكن الأشياء.",
            xp = 30,
            orderNumber = 2,
            characterSpeakerId = "teacher_female_01",
            characterNameAr = "الأستاذة فاطمة",
            activities = listOf(
                ActivityModel(
                    id = "act_h2_1",
                    activityType = "SENTENCE_BUILDER",
                    speakerId = "teacher_female_01",
                    speakerNameAr = "الأستاذة فاطمة",
                    promptAr = "أين الكتاب؟ (Buku ada di atas meja)",
                    promptId = "Susun kalimat: Buku ada di atas meja",
                    audioTextAr = "الكتاب على الطاولة.",
                    wordChips = listOf("الكِتَابُ", "عَلَى", "الطَّاوِلَةِ", "تَحْتَ", "البَابِ"),
                    correctAnswer = "الكِتَابُ عَلَى الطَّاوِلَةِ",
                    hint1 = "ابدأ بكلمة 'الكتاب'.",
                    hint2 = "استخدم حرف الجر 'على'.",
                    hint3 = "الكتاب + على + الطاولة",
                    explanationAr = "نستخدم 'عَلَى' للدلالة على الاستعلاء فوق السطح.",
                    explanationId = "Gunakan 'عَلَى' untuk menyatakan 'di atas'."
                ),
                ActivityModel(
                    id = "act_h2_2",
                    activityType = "LISTENING",
                    speakerId = "teacher_female_01",
                    speakerNameAr = "الأستاذة فاطمة",
                    promptAr = "استمع وحدد: أين القطة؟",
                    promptId = "Dengarkan audio dan pilih letak kucing:",
                    audioTextAr = "القِطَّةُ تَحْتَ الكُرْسِيِّ.",
                    options = listOf("تحت الكرسي", "على الطاولة", "أمام الباب", "بجانب النافذة"),
                    correctAnswer = "تحت الكرسي",
                    hint1 = "استمع إلى كلمة 'تحت'.",
                    hint2 = "تَحْتَ تعني di bawah.",
                    hint3 = "القطة تحت الكرسي.",
                    explanationAr = "القطة موجودة تحت الكرسي.",
                    explanationId = "Kucing berada di bawah kursi (تحت الكرسي)."
                )
            )
        ),
        MissionModel(
            id = "m_house_3",
            locationId = "loc_house",
            titleAr = "أين مفتاحي؟",
            titleId = "Di Mana Kunciku?",
            missionType = "PROBLEM_SOLVING",
            difficulty = "Hard",
            objective = "لقد أضعت مفتاحك في البيت! تواصل وابحث عنه عبر الأدلة المختلفة.",
            xp = 50,
            orderNumber = 3,
            characterSpeakerId = "teacher_female_01",
            characterNameAr = "الأستاذة فاطمة",
            activities = listOf(
                ActivityModel(
                    id = "act_h3_1",
                    activityType = "SPEAKING",
                    speakerId = "teacher_female_01",
                    speakerNameAr = "الأستاذة فاطمة",
                    promptAr = "ماذا تبحث في الغرفة؟ (Katakan: Saya sedang mencari kunciku)",
                    promptId = "Ucapkan atau ketik: أَبْحَثُ عَنْ مِفْتَاحِي",
                    audioTextAr = "أَبْحَثُ عَنْ مِفْتَاحِي.",
                    correctAnswer = "أبحث عن مفتاحي",
                    wordChips = listOf("أَبْحَثُ", "عَنْ", "مِفْتَاحِي", "فِي", "الغُرْفَةِ"),
                    hint1 = "استخدم الفعل أبحث متبوعاً بحرف الجر عن.",
                    hint2 = "مفتاح + ي المتكلم = مفتاحي.",
                    hint3 = "أَبْحَثُ عَنْ مِفْتَاحِي.",
                    explanationAr = "التعبير الصحيح للبحث: أبحث عن...",
                    explanationId = "Ungkapan mencari barang: أبحث عن مفتاحي (Saya mencari kunciku)."
                ),
                ActivityModel(
                    id = "act_h3_2",
                    activityType = "DECISION_BRANCH",
                    speakerId = "teacher_female_01",
                    speakerNameAr = "الأستاذة فاطمة",
                    promptAr = "قالت لك أختك: 'انْظُرْ عَلَى الطَّاوِلَةِ بِجَانِبِ الكِتَابِ!' ماذا تفعل؟",
                    promptId = "Saudarimu berkata: 'Lihatlah di atas meja di samping buku!' Apa tindakanmu?",
                    audioTextAr = "انْظُرْ عَلَى الطَّاوِلَةِ بِجَانِبِ الكِتَابِ!",
                    options = listOf(
                        "أذهب إلى الطاولة وأنظر بجانب الكتاب وأجده!",
                        "أخرج من البيت فوراً بدون مفتاح",
                        "أبحث في الثلاجة في المطبخ"
                    ),
                    correctAnswer = "أذهب إلى الطاولة وأنظر بجانب الكتاب وأجده!",
                    hint1 = "اتبع توجيهات أختك بدقة.",
                    hint2 = "المكان المحدد هو الطاولة بجانب الكتاب.",
                    hint3 = "انظر على الطاولة بجانب الكتاب.",
                    explanationAr = "أحسنت! عثرت على المفتاح بفضل اتباعك للإرشادات.",
                    explanationId = "Bagus! Kunci ditemukan di samping buku di atas meja."
                )
            )
        ),

        // ==================== 🛒 المتجر (Shop) ====================
        MissionModel(
            id = "m_shop_1",
            locationId = "loc_shop",
            titleAr = "ماذا أشتري؟",
            titleId = "Apa yang Ku Beli?",
            missionType = "RECOGNIZE",
            difficulty = "Easy",
            objective = "تعرف على أسماء المواد الغذائية والمنتجات الأساسية في المتجر.",
            xp = 20,
            orderNumber = 1,
            characterSpeakerId = "teacher_male_01",
            characterNameAr = "الأستاذ أحمد",
            activities = listOf(
                ActivityModel(
                    id = "act_s1_1",
                    activityType = "MULTIPLE_CHOICE",
                    speakerId = "teacher_male_01",
                    speakerNameAr = "الأستاذ أحمد",
                    promptAr = "أي من هذه الخيارات يعني (Susu)?",
                    promptId = "Pilih kata yang berarti Susu:",
                    audioTextAr = "الحَلِيبُ مَفِيدٌ لِلصِّحَّةِ.",
                    options = listOf("حَلِيب", "مَاء", "خُبْز", "سُكَّر"),
                    correctAnswer = "حَلِيب",
                    hint1 = "مشروب أبيض مغذٍ.",
                    hint2 = "ح - ل - ي - ب",
                    hint3 = "حَلِيب",
                    explanationAr = "حَلِيب تعني Susu.",
                    explanationId = "Halib (حليب) artinya susu."
                ),
                ActivityModel(
                    id = "act_s1_2",
                    activityType = "MULTIPLE_CHOICE",
                    speakerId = "teacher_male_01",
                    speakerNameAr = "الأستاذ أحمد",
                    promptAr = "ماذا يسمى (Roti) باللغة العربية؟",
                    promptId = "Apa sebutan Roti dalam bahasa Arab?",
                    audioTextAr = "أَشْتَرِي الخُبْزَ كُلَّ صَبَاحٍ.",
                    options = listOf("خُبْز", "أَرُزّ", "بَيْض", "شَاي"),
                    correctAnswer = "خُبْز",
                    hint1 = "يؤكل مع الجبن أو المربى.",
                    hint2 = "يبدأ بحرف الخاء.",
                    hint3 = "خُبْز (Khubz)",
                    explanationAr = "خُبْز هو الخبز المصنوع من القمح.",
                    explanationId = "Khubz (خُبْز) artinya roti."
                )
            )
        ),
        MissionModel(
            id = "m_shop_2",
            locationId = "loc_shop",
            titleAr = "كم السعر؟",
            titleId = "Berapa Harganya?",
            missionType = "USE",
            difficulty = "Medium",
            objective = "اسأل عن أسعار السلع واستخدم الأرقام من 1 إلى 10 والتعبير بالريال.",
            xp = 30,
            orderNumber = 2,
            characterSpeakerId = "waiter_male_01",
            characterNameAr = "البائع كريم",
            activities = listOf(
                ActivityModel(
                    id = "act_s2_1",
                    activityType = "LISTENING",
                    speakerId = "waiter_male_01",
                    speakerNameAr = "البائع كريم",
                    promptAr = "استمع للبائع: كم سعر علبة الحليب؟",
                    promptId = "Dengarkan penjual: Berapa harga sekotak susu?",
                    audioTextAr = "الحَلِيبُ بِخَمْسَةِ رِيَالَاتٍ.",
                    options = listOf("بخمسة ريالات (5)", "بعشرة ريالات (10)", "بريالين (2)", "بثلاثة ريالات (3)"),
                    correctAnswer = "بخمسة ريالات (5)",
                    hint1 = "استمع للرقم 'خمسة'.",
                    hint2 = "خَمْسَة = 5",
                    hint3 = "بخمسة ريالات",
                    explanationAr = "سعر الحليب خمسة ريالات.",
                    explanationId = "Harga susu adalah 5 riyal (بخمسة ريالات)."
                ),
                ActivityModel(
                    id = "act_s2_2",
                    activityType = "SENTENCE_BUILDER",
                    speakerId = "waiter_male_01",
                    speakerNameAr = "البائع كريم",
                    promptAr = "اسأل البائع عن سعر الخبز: (Berapa harga roti ini?)",
                    promptId = "Susun pertanyaan harga roti:",
                    audioTextAr = "كَمْ سِعْرُ هَذَا الخُبْزِ؟",
                    wordChips = listOf("كَمْ", "سِعْرُ", "هَذَا", "الخُبْزِ؟", "أَيْنَ"),
                    correctAnswer = "كَمْ سِعْرُ هَذَا الخُبْزِ؟",
                    hint1 = "ابدأ بأداة الاستفهام 'كم'.",
                    hint2 = "كم + سعر + هذا + الخبز",
                    hint3 = "كَمْ سِعْرُ هَذَا الخُبْزِ؟",
                    explanationAr = "للسؤال عن السعر نقول: كم سعر...؟",
                    explanationId = "Untuk menanyakan harga: كَمْ سِعْرُ...؟"
                )
            )
        ),
        MissionModel(
            id = "m_shop_3",
            locationId = "loc_shop",
            titleAr = "قائمة المشتريات",
            titleId = "Daftar Belanjaan",
            missionType = "PROBLEM_SOLVING",
            difficulty = "Hard",
            objective = "لديك قائمة مشتريات محددة من والدتك: ماء وخبز وبيض بميزانية محددة.",
            xp = 50,
            orderNumber = 3,
            characterSpeakerId = "waiter_male_01",
            characterNameAr = "البائع كريم",
            activities = listOf(
                ActivityModel(
                    id = "act_s3_1",
                    activityType = "SPEAKING",
                    speakerId = "waiter_male_01",
                    speakerNameAr = "البائع كريم",
                    promptAr = "قال البائع: 'مَرْحَبًا، مَاذَا تُرِيدُ؟' قل له: (Saya ingin air, roti, dan telur)",
                    promptId = "Ucapkan atau ketik: أُرِيدُ المَاءَ وَالخُبْزَ وَالبَيْضَ",
                    audioTextAr = "أُرِيدُ المَاءَ وَالخُبْزَ وَالبَيْضَ.",
                    correctAnswer = "أريد الماء والخبز والبيض",
                    wordChips = listOf("أُرِيدُ", "المَاءَ", "وَالخُبْزَ", "وَالبَيْضَ", "مِنْ", "فَضْلِكَ"),
                    hint1 = "استخدم أداة العطف الواو بين السلع.",
                    hint2 = "أريد + الماء + والخبز + والبيض",
                    hint3 = "أُرِيدُ المَاءَ وَالخُبْزَ وَالبَيْضَ",
                    explanationAr = "طلب سليم ومهذب لجميع عناصر القائمة.",
                    explanationId = "Permintaan lengkap sesuai daftar belanjaan."
                ),
                ActivityModel(
                    id = "act_s3_2",
                    activityType = "DECISION_BRANCH",
                    speakerId = "waiter_male_01",
                    speakerNameAr = "البائع كريم",
                    promptAr = "المجموع 15 ريالاً ولديك 20 ريالاً. ماذا تفعل؟",
                    promptId = "Total 15 riyal, kamu punya 20 riyal. Apa tindakanmu?",
                    audioTextAr = "المَجْمُوعُ خَمْسَةَ عَشَرَ رِيَالاً.",
                    options = listOf(
                        "أعطيه 20 ريالاً وأقول: 'تَفَضَّلْ، وَأُرِيدُ البَاقِيَ مِنْ فَضْلِكَ'",
                        "أغادر المتجر سريعاً دون أن أدفع",
                        "أطلب تخفيض السعر إلى ريال واحد فقط"
                    ),
                    correctAnswer = "أعطيه 20 ريالاً وأقول: 'تَفَضَّلْ، وَأُرِيدُ البَاقِيَ مِنْ فَضْلِكَ'",
                    hint1 = "سلم المبلغ واطلب الباقي بأدب.",
                    hint2 = "تفضل = silakan, الباقي = kembalian.",
                    hint3 = "تفضل وأريد الباقي من فضلك.",
                    explanationAr = "سلوك تسوق صحيح وتواصل راقٍ.",
                    explanationId = "Tindakan sopan: serahkan uang dan minta kembalian."
                )
            )
        ),

        // ==================== 🍽️ المطعم (Restaurant) ====================
        MissionModel(
            id = "m_restaurant_1",
            locationId = "loc_restaurant",
            titleAr = "ماذا تأكل؟",
            titleId = "Apa yang Hendak Kamu Makan?",
            missionType = "RECOGNIZE",
            difficulty = "Easy",
            objective = "تعرف على قائمة الأطعمة والمشروبات الأساسية في المطعم العربي.",
            xp = 20,
            orderNumber = 1,
            characterSpeakerId = "waiter_male_01",
            characterNameAr = "النادل كريم",
            activities = listOf(
                ActivityModel(
                    id = "act_r1_1",
                    activityType = "MULTIPLE_CHOICE",
                    speakerId = "waiter_male_01",
                    speakerNameAr = "النادل كريم",
                    promptAr = "أي من هذه الكلمات تعني (Ayam)?",
                    promptId = "Pilih kata Arab untuk Ayam:",
                    audioTextAr = "الدَّجَاجُ المَشْوِيُّ لَذِيذٌ جِدًّا.",
                    options = listOf("دَجَاج", "لَحْم", "سَمَك", "أَرُزّ"),
                    correctAnswer = "دَجَاج",
                    hint1 = "طائر يُطهى مشوياً أو مقلياً.",
                    hint2 = "دَ - جَ - ا - ج",
                    hint3 = "دَجَاج",
                    explanationAr = "دجاج تعني Ayam.",
                    explanationId = "Dajaj (دجاج) berarti ayam."
                ),
                ActivityModel(
                    id = "act_r1_2",
                    activityType = "MULTIPLE_CHOICE",
                    speakerId = "waiter_male_01",
                    speakerNameAr = "النادل كريم",
                    promptAr = "ماذا يعني مشروب (عَصِير)؟",
                    promptId = "Apa arti minuman 'عَصِير'?",
                    audioTextAr = "أُرِيدُ عَصِيرَ البُرْتُقَالِ.",
                    options = listOf("Jus", "Air putih", "Kopi", "Teh"),
                    correctAnswer = "Jus",
                    hint1 = "مشروب فاكهة معصورة ولذيذة.",
                    hint2 = "مثل عصير البرتقال أو المانجو.",
                    hint3 = "عصير = Jus",
                    explanationAr = "عصير هو المشروب المستخرج من الفواكه.",
                    explanationId = "'Ashir (عصير) berarti jus buah."
                )
            )
        ),
        MissionModel(
            id = "m_restaurant_2",
            locationId = "loc_restaurant",
            titleAr = "أريد الطعام",
            titleId = "Saya Mau Memesan Makanan",
            missionType = "USE",
            difficulty = "Medium",
            objective = "أجرِ حوار الطلب مع النادل: التحية، طلب الوجبة الرئيسية، والمشروب بأدب.",
            xp = 40,
            orderNumber = 2,
            characterSpeakerId = "waiter_male_01",
            characterNameAr = "النادل كريم",
            activities = listOf(
                ActivityModel(
                    id = "act_r2_1",
                    activityType = "SENTENCE_BUILDER",
                    speakerId = "waiter_male_01",
                    speakerNameAr = "النادل كريم",
                    promptAr = "قال النادل: 'مَاذَا تُرِيدُ أَنْ تَأْكُلَ؟' قل له: (Saya mau nasi dan ayam)",
                    promptId = "Susun kalimat: أُرِيدُ الأَرُزَّ وَالدَّجَاجَ",
                    audioTextAr = "أُرِيدُ الأَرُزَّ وَالدَّجَاجَ.",
                    wordChips = listOf("أُرِيدُ", "الأَرُزَّ", "وَالدَّجَاجَ", "مَعَ", "السَّلَطَةِ"),
                    correctAnswer = "أُرِيدُ الأَرُزَّ وَالدَّجَاجَ",
                    hint1 = "ابدأ بكلمة أريد.",
                    hint2 = "أريد + الأرز + والدجاج",
                    hint3 = "أُرِيدُ الأَرُزَّ وَالدَّجَاجَ",
                    explanationAr = "تعبير مباشر ومؤدب لطلب وجبة الغداء.",
                    explanationId = "Ungkapan memesan makanan utama."
                ),
                ActivityModel(
                    id = "act_r2_2",
                    activityType = "SPEAKING",
                    speakerId = "waiter_male_01",
                    speakerNameAr = "النادل كريم",
                    promptAr = "سألك النادل: 'وَمَاذَا تُرِيدُ أَنْ تَشْرَبَ؟' أجب: (Air, tolong)",
                    promptId = "Ucapkan atau ketik: أُرِيدُ المَاءَ مِنْ فَضْلِكَ",
                    audioTextAr = "أُرِيدُ المَاءَ مِنْ فَضْلِكَ.",
                    correctAnswer = "أريد الماء من فضلك",
                    wordChips = listOf("أُرِيدُ", "المَاءَ", "مِنْ", "فَضْلِكَ", "بَارِدًا"),
                    hint1 = "لا تنسَ عبارة التأدب 'من فضلك'.",
                    hint2 = "أريد الماء + من فضلك.",
                    hint3 = "أُرِيدُ المَاءَ مِنْ فَضْلِكَ.",
                    explanationAr = "إضافة 'من فضلك' تضفي احتراماً كبيراً.",
                    explanationId = "Menambahkan 'من فضلك' (tolong/silakan) membuat pesanan sangat santun."
                )
            )
        ),
        MissionModel(
            id = "m_restaurant_3",
            locationId = "loc_restaurant",
            titleAr = "هذا ليس طلبي",
            titleId = "Ini Bukan Pesanan Saya",
            missionType = "PROBLEM_SOLVING",
            difficulty = "Hard",
            objective = "المشكلة: أحضر النادل سمكاً وأنت طلبت الأرز والدجاج! تحدث لحل الخطأ بلباقة.",
            xp = 70,
            orderNumber = 3,
            characterSpeakerId = "waiter_male_01",
            characterNameAr = "النادل كريم",
            activities = listOf(
                ActivityModel(
                    id = "act_r3_1",
                    activityType = "SPEAKING",
                    speakerId = "waiter_male_01",
                    speakerNameAr = "النادل كريم",
                    promptAr = "نبه النادل بأدب: (Maaf, ini bukan pesanan saya)",
                    promptId = "Ucapkan atau ketik: عَفْوًا، هَذَا لَيْسَ طَلَبِي",
                    audioTextAr = "عَفْوًا، هَذَا لَيْسَ طَلَبِي.",
                    correctAnswer = "عفوا هذا ليس طلبي",
                    wordChips = listOf("عَفْوًا،", "هَذَا", "لَيْسَ", "طَلَبِي.", "شُكْرًا"),
                    hint1 = "ابدأ بكلمة اعتذار وتنبيه 'عفواً'.",
                    hint2 = "استخدم أداة النفي 'ليس'.",
                    hint3 = "عَفْوًا، هَذَا لَيْسَ طَلَبِي.",
                    explanationAr = "أسلوب دبلوماسي ومهذب في معالجة الأخطاء في المطاعم.",
                    explanationId = "Cara santun memberitahu kesalahan pesanan tanpa menyinggung pelayan."
                ),
                ActivityModel(
                    id = "act_r3_2",
                    activityType = "SENTENCE_BUILDER",
                    speakerId = "waiter_male_01",
                    speakerNameAr = "النادل كريم",
                    promptAr = "سألك النادل في حرج: 'عَفْوًا! مَاذَا طَلَبْتَ يَا أَخِي؟' قل له: (Saya tadi memesan nasi dan ayam)",
                    promptId = "Susun kalimat: طَلَبْتُ الأَرُزَّ وَالدَّجَاجَ",
                    audioTextAr = "طَلَبْتُ الأَرُزَّ وَالدَّجَاجَ.",
                    wordChips = listOf("طَلَبْتُ", "الأَرُزَّ", "وَالدَّجَاجَ", "لَيْسَ", "السَّمَكَ"),
                    correctAnswer = "طَلَبْتُ الأَرُزَّ وَالدَّجَاجَ",
                    hint1 = "استخدم الفعل الماضي للمتكلم 'طلبتُ'.",
                    hint2 = "طلبت + الأرز + والدجاج.",
                    hint3 = "طَلَبْتُ الأَرُزَّ وَالدَّجَاجَ.",
                    explanationAr = "اعتذر النادل واستبدل الوجبة فوراً بالأرز والدجاج الطازج!",
                    explanationId = "Pelayan meminta maaf dan segera mengganti dengan nasi dan ayam hangat!"
                )
            )
        ),

        // ==================== 🏪 البقالة (Grocery Store) ====================
        MissionModel(
            id = "m_grocery_1",
            locationId = "loc_grocery",
            titleAr = "أين المنتج؟",
            titleId = "Di Mana Barangnya?",
            missionType = "RECOGNIZE",
            difficulty = "Easy",
            objective = "تعرف على أقسام البقالة: الرفوف، الصناديق، والعربات.",
            xp = 20,
            orderNumber = 1,
            characterSpeakerId = "waiter_male_01",
            characterNameAr = "عامل البقالة سمير",
            activities = listOf(
                ActivityModel(
                    id = "act_g1_1",
                    activityType = "MULTIPLE_CHOICE",
                    speakerId = "waiter_male_01",
                    speakerNameAr = "عامل البقالة سمير",
                    promptAr = "ماذا تسمى (Rak tempat barang) في المتجر؟",
                    promptId = "Apa bahasa Arab untuk Rak?",
                    audioTextAr = "الزُّجَاجَاتُ عَلَى الرَّفِّ الأَوَّلِ.",
                    options = listOf("رَفّ", "بَاب", "صُنْدُوق", "عَرَبَة"),
                    correctAnswer = "رَفّ",
                    hint1 = "توضع عليه المنتجات بشكل مرتب.",
                    hint2 = "رَ - فّ",
                    hint3 = "رَفّ",
                    explanationAr = "رَفّ (جمعها رفوف) لوضع البضائع.",
                    explanationId = "Raff (رَفّ) artinya rak pajangan barang."
                ),
                ActivityModel(
                    id = "act_g1_2",
                    activityType = "MULTIPLE_CHOICE",
                    speakerId = "waiter_male_01",
                    speakerNameAr = "عامل البقالة سمير",
                    promptAr = "ماذا تسمى العربة ذات العجلات للتسوق؟",
                    promptId = "Apa sebutan troli belanja beroda?",
                    audioTextAr = "خُذْ عَرَبَةَ التَّسَوُّقِ.",
                    options = listOf("عَرَبَة", "حَقِيبَة", "طَاوِلَة", "كُرْسِيّ"),
                    correctAnswer = "عَرَبَة",
                    hint1 = "تدفعها أمامك لوضع المشتريات.",
                    hint2 = "عَرَبَة التسوق.",
                    hint3 = "عَرَبَة (Troli)",
                    explanationAr = "عَرَبَة هي ترولي التسوق.",
                    explanationId = "'Arabah (عَرَبَة) adalah troli belanja."
                )
            )
        ),
        MissionModel(
            id = "m_grocery_2",
            locationId = "loc_grocery",
            titleAr = "ساعدني من فضلك",
            titleId = "Bantu Saya, Tolong",
            missionType = "USE",
            difficulty = "Medium",
            objective = "استخدم مفردات الاتجاهات (يمين، يسار، أمام، خلف) للاستفسار عن أماكن البضائع.",
            xp = 40,
            orderNumber = 2,
            characterSpeakerId = "waiter_male_01",
            characterNameAr = "عامل البقالة سمير",
            activities = listOf(
                ActivityModel(
                    id = "act_g2_1",
                    activityType = "LISTENING",
                    speakerId = "waiter_male_01",
                    speakerNameAr = "عامل البقالة سمير",
                    promptAr = "سألت العامل عن الحليب، استمع لتوجيهه:",
                    promptId = "Dengarkan arah yang ditunjukkan pelayan minimarket:",
                    audioTextAr = "الحَلِيبُ فِي الجِهَةِ اليُمْنَى، عَلَى الرَّفِّ الثَّانِي.",
                    options = listOf(
                        "Di sisi kanan, rak kedua",
                        "Di sisi kiri, dekat pintu",
                        "Di belakang kasir",
                        "Di lantai atas"
                    ),
                    correctAnswer = "Di sisi kanan, rak kedua",
                    hint1 = "استمع إلى 'الجهة اليمنى' و 'الرف الثاني'.",
                    hint2 = "اليمنى = kanan.",
                    hint3 = "في الجهة اليمنى، على الرف الثاني.",
                    explanationAr = "الحليب في الجهة اليمنى على الرف الثاني.",
                    explanationId = "Susu ada di bagian kanan pada rak nomor dua."
                ),
                ActivityModel(
                    id = "act_g2_2",
                    activityType = "SENTENCE_BUILDER",
                    speakerId = "waiter_male_01",
                    speakerNameAr = "عامل البقالة سمير",
                    promptAr = "اسأل العامل بلباقة: (Permisi, di mana letak susu?)",
                    promptId = "Susun pertanyaan mencari susu:",
                    audioTextAr = "عَفْوًا، أَيْنَ الحَلِيبُ؟",
                    wordChips = listOf("عَفْوًا،", "أَيْنَ", "الحَلِيبُ؟", "كَمْ", "السِّعْرُ"),
                    correctAnswer = "عَفْوًا، أَيْنَ الحَلِيبُ؟",
                    hint1 = "ابدأ بطلب لفت الانتباه: عفواً.",
                    hint2 = "أين = di mana.",
                    hint3 = "عَفْوًا، أَيْنَ الحَلِيبُ؟",
                    explanationAr = "سؤال واضح ومباشر للاستعلام عن موقع سلعة.",
                    explanationId = "Cara santun menanyakan posisi barang belanjaan."
                )
            )
        ),
        MissionModel(
            id = "m_grocery_3",
            locationId = "loc_grocery",
            titleAr = "التسوق السريع",
            titleId = "Belanja Cepat",
            missionType = "PROBLEM_SOLVING",
            difficulty = "Hard",
            objective = "المهمة: شراء أربعة أغراض سريعة والتفاعل مع أمين الصندوق حول الكيس البلاستيكي ودفع الحساب.",
            xp = 100,
            orderNumber = 3,
            characterSpeakerId = "waiter_male_01",
            characterNameAr = "عامل البقالة سمير",
            activities = listOf(
                ActivityModel(
                    id = "act_g3_1",
                    activityType = "SPEAKING",
                    speakerId = "waiter_male_01",
                    speakerNameAr = "المحاسب سمير",
                    promptAr = "سألك المحاسب عند الدفع: 'هَلْ تُرِيدُ كِيسًا؟' أجب: (Ya, tolong)",
                    promptId = "Ucapkan atau ketik: نَعَمْ، مِنْ فَضْلِكَ",
                    audioTextAr = "نَعَمْ، مِنْ فَضْلِكَ.",
                    correctAnswer = "نعم من فضلك",
                    wordChips = listOf("نَعَمْ،", "مِنْ", "فَضْلِكَ.", "لاَ", "شُكْرًا"),
                    hint1 = "نعم = ya, من فضلك = tolong.",
                    hint2 = "نَعَمْ + مِنْ فَضْلِكَ.",
                    hint3 = "نَعَمْ، مِنْ فَضْلِكَ.",
                    explanationAr = "تعبير ممتاز وواضح للتأكيد وطلب كيس للمشتريات.",
                    explanationId = "Jawaban ramah untuk meminta kantong belanja."
                ),
                ActivityModel(
                    id = "act_g3_2",
                    activityType = "DECISION_BRANCH",
                    speakerId = "waiter_male_01",
                    speakerNameAr = "المحاسب سمير",
                    promptAr = "سلمك الأكياس وقال: 'تَفَضَّلْ، يَوْمُكَ سَعِيدٌ!' كيف ترد عليه بأفضل عبارة؟",
                    promptId = "Kasir menyerahkan belanjaan dan mendoakan hari baik. Apa respon terbaikmu?",
                    audioTextAr = "تَفَضَّلْ، يَوْمُكَ سَعِيدٌ!",
                    options = listOf(
                        "شُكْرًا جَزِيلاً، وَيَوْمُكَ سَعِيدٌ أَيْضًا!",
                        "لا أريد هذا الشيء",
                        "أين مفتاح الغرفة؟"
                    ),
                    correctAnswer = "شُكْرًا جَزِيلاً، وَيَوْمُكَ سَعِيدٌ أَيْضًا!",
                    hint1 = "بادله الشكر والتمنيات الطيبة.",
                    hint2 = "شكراً جزيلاً = terima kasih banyak.",
                    hint3 = "شُكْرًا جَزِيلاً، وَيَوْمُكَ سَعِيدٌ أَيْضًا!",
                    explanationAr = "إتمام رائع للتسوق وحصلت على وسام المتسوق الذكي!",
                    explanationId = "Selamat! Kamu menyelesaikan simulasi belanja dengan sangat ramah."
                )
            )
        )
    )
}
