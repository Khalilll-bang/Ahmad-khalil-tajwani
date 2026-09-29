package com.example.data.seed

import com.example.data.model.ActivityModel
import com.example.data.model.MissionModel

object SeedMissionsLevel2 {
    val missions = listOf(
        // ==================== 🏫 المدرسة (School) ====================
        MissionModel(
            id = "m_school_1",
            locationId = "loc_school",
            titleAr = "من أنا؟",
            titleId = "Siapa Saya?",
            missionType = "RECOGNIZE",
            difficulty = "Easy",
            objective = "التعارف والتعريف بالنفس والبلد الأصلي في المدرسة.",
            xp = 20,
            orderNumber = 1,
            characterSpeakerId = "teacher_male_01",
            characterNameAr = "الأستاذ أحمد",
            activities = listOf(
                ActivityModel(
                    id = "act_sc1_1",
                    activityType = "SENTENCE_BUILDER",
                    speakerId = "teacher_male_01",
                    speakerNameAr = "الأستاذ أحمد",
                    promptAr = "سألك الأستاذ: 'مَا اسْمُكَ؟' أجب: (Nama saya Ahmad)",
                    promptId = "Susun jawaban: اسْمِي أَحْمَدُ",
                    audioTextAr = "اسْمِي أَحْمَدُ.",
                    wordChips = listOf("اسْمِي", "أَحْمَدُ", "مِنْ", "إِنْدُونِيسِيَا"),
                    correctAnswer = "اسْمِي أَحْمَدُ",
                    hint1 = "اسم + ي المتكلم = اسمي.",
                    hint2 = "اسمي + أحمد.",
                    hint3 = "اسْمِي أَحْمَدُ.",
                    explanationAr = "للتعريف بالاسم نقول: اسمي...",
                    explanationId = "Untuk memperkenalkan nama: اسمي..."
                ),
                ActivityModel(
                    id = "act_sc1_2",
                    activityType = "SPEAKING",
                    speakerId = "teacher_male_01",
                    speakerNameAr = "الأستاذ أحمد",
                    promptAr = "سألك: 'مِنْ أَيْنَ أَنْتَ؟' قل له: (Saya dari Indonesia)",
                    promptId = "Ucapkan atau ketik: أَنَا مِنْ إِنْدُونِيسِيَا",
                    audioTextAr = "أَنَا مِنْ إِنْدُونِيسِيَا.",
                    correctAnswer = "أنا من إندونيسيا",
                    wordChips = listOf("أَنَا", "مِنْ", "إِنْدُونِيسِيَا.", "طَالِبٌ", "جَدِيدٌ"),
                    hint1 = "أنا = saya, من = dari.",
                    hint2 = "أنا من + الدولة.",
                    hint3 = "أَنَا مِنْ إِنْدُونِيسِيَا.",
                    explanationAr = "للإخبار عن بلد المنشأ: أنا من...",
                    explanationId = "Menyatakan asal negara: أنا من إندونيسيا."
                )
            )
        ),
        MissionModel(
            id = "m_school_2",
            locationId = "loc_school",
            titleAr = "في أي فصل؟",
            titleId = "Di Kelas Mana?",
            missionType = "USE",
            difficulty = "Medium",
            objective = "السؤال عن أرقام الفصول والطوابق واتباع إرشادات الطريق داخل مبنى المدرسة.",
            xp = 30,
            orderNumber = 2,
            characterSpeakerId = "teacher_male_01",
            characterNameAr = "الأستاذ أحمد",
            activities = listOf(
                ActivityModel(
                    id = "act_sc2_1",
                    activityType = "LISTENING",
                    speakerId = "teacher_male_01",
                    speakerNameAr = "الأستاذ أحمد",
                    promptAr = "استمع لتوجيهات الأستاذ عن موقع الفصل:",
                    promptId = "Dengarkan audio penunjuk arah kelas:",
                    audioTextAr = "اذْهَبْ إِلَى اليَمِينِ، ثُمَّ ادْخُلِ الفَصْلَ الثَّالِثَ فِي الطَّابِقِ الأَوَّلِ.",
                    options = listOf(
                        "Ke kanan, lalu masuk kelas ketiga di lantai satu",
                        "Ke kiri, naik ke lantai dua",
                        "مباشرة إلى ساحة المدرسة",
                        "ارجع إلى مكتب المدير"
                    ),
                    correctAnswer = "Ke kanan, lalu masuk kelas ketiga di lantai satu",
                    hint1 = "اذهب إلى اليمين = pergi ke kanan.",
                    hint2 = "الفصل الثالث = kelas ketiga.",
                    hint3 = "الطابق الأول = lantai satu.",
                    explanationAr = "الفصل الثالث يقع يميناً في الطابق الأول.",
                    explanationId = "Arah jelas menuju kelas di lantai satu."
                )
            )
        ),
        MissionModel(
            id = "m_school_3",
            locationId = "loc_school",
            titleAr = "تأخرت عن الدرس",
            titleId = "Terlambat Masuk Pelajaran",
            missionType = "PROBLEM_SOLVING",
            difficulty = "Hard",
            objective = "وصلت متأخراً بسبب زحام الحافلة! اعتذر للأستاذ باللغة العربية واطلب الإذن بالدخول.",
            xp = 60,
            orderNumber = 3,
            characterSpeakerId = "teacher_male_01",
            characterNameAr = "الأستاذ أحمد",
            activities = listOf(
                ActivityModel(
                    id = "act_sc3_1",
                    activityType = "SPEAKING",
                    speakerId = "teacher_male_01",
                    speakerNameAr = "الأستاذ أحمد",
                    promptAr = "اطرق الباب وقل بلباقة: (Permisi Ustadz, bolehkah saya masuk?)",
                    promptId = "Ucapkan atau ketik: عَفْوًا يَا أُسْتَاذُ، هَلْ أَسْتَطِيعُ أَنْ أَدْخُلَ؟",
                    audioTextAr = "عَفْوًا يَا أُسْتَاذُ، هَلْ أَسْتَطِيعُ أَنْ أَدْخُلَ؟",
                    correctAnswer = "عفوا يا أستاذ هل أستطيع أن أدخل",
                    wordChips = listOf("عَفْوًا", "يَا", "أُسْتَاذُ،", "هَلْ", "أَسْتَطِيعُ", "أَنْ", "أَدْخُلَ؟"),
                    hint1 = "هل أستطيع = bisakah saya.",
                    hint2 = "أن أدخل = untuk masuk.",
                    hint3 = "عَفْوًا يَا أُسْتَاذُ، هَلْ أَسْتَطِيعُ أَنْ أَدْخُلَ؟",
                    explanationAr = "أسلوب قمة في الأدب لطلب الإذن من المعلم.",
                    explanationId = "Ungkapan izin masuk kelas yang sangat sopan."
                ),
                ActivityModel(
                    id = "act_sc3_2",
                    activityType = "SENTENCE_BUILDER",
                    speakerId = "teacher_male_01",
                    speakerNameAr = "الأستاذ أحمد",
                    promptAr = "سأل الأستاذ: 'لِمَاذَا تَأَخَّرْتَ؟' اشرح له: (Saya terlambat karena bus terlambat)",
                    promptId = "Susun kalimat: تَأَخَّرْتُ لِأَنَّ الحَافِلَةَ تَأَخَّرَتْ",
                    audioTextAr = "تَأَخَّرْتُ لِأَنَّ الحَافِلَةَ تَأَخَّرَتْ.",
                    wordChips = listOf("تَأَخَّرْتُ", "لِأَنَّ", "الحَافِلَةَ", "تَأَخَّرَتْ.", "آسِفٌ"),
                    correctAnswer = "تَأَخَّرْتُ لِأَنَّ الحَافِلَةَ تَأَخَّرَتْ",
                    hint1 = "استخدم أداة التعليل 'لأن'.",
                    hint2 = "الحافلة = bus.",
                    hint3 = "تَأَخَّرْتُ لِأَنَّ الحَافِلَةَ تَأَخَّرَتْ.",
                    explanationAr = "ابتسم الأستاذ وقال: 'تفضل بالدخول واجلس مكانك!'.",
                    explanationId = "Guru tersenyum memaklumi dan mengizinkanmu duduk!"
                )
            )
        ),

        // ==================== 🎓 الجامعة (University) ====================
        MissionModel(
            id = "m_univ_1",
            locationId = "loc_university",
            titleAr = "أول يوم في الجامعة",
            titleId = "Hari Pertama di Kampus",
            missionType = "RECOGNIZE",
            difficulty = "Easy",
            objective = "تعرف على مرافق الجامعة: الكلية، القاعة، المكتبة، والمقصف.",
            xp = 20,
            orderNumber = 1,
            characterSpeakerId = "teacher_male_01",
            characterNameAr = "د. كمال",
            activities = listOf(
                ActivityModel(
                    id = "act_u1_1",
                    activityType = "MULTIPLE_CHOICE",
                    speakerId = "teacher_male_01",
                    speakerNameAr = "د. كمال",
                    promptAr = "ماذا تسمى (Fakultas) في الجامعة؟",
                    promptId = "Apa bahasa Arab untuk Fakultas?",
                    audioTextAr = "أَدْرُسُ فِي كُلِّيَّةِ التَّرْبِيَةِ.",
                    options = listOf("كُلِّيَّة", "جَامِعَة", "مَقْصَف", "مَكْتَب"),
                    correctAnswer = "كُلِّيَّة",
                    hint1 = "مثل كلية الشريعة أو كلية الطب.",
                    hint2 = "كُلِّيَّة (Kulliyyah).",
                    hint3 = "كُلِّيَّة",
                    explanationAr = "كُلِّيَّة هي الوحدة الأكاديمية في الجامعة.",
                    explanationId = "Kulliyyah (كُلِّيَّة) berarti fakultas."
                )
            )
        ),
        MissionModel(
            id = "m_univ_2",
            locationId = "loc_university",
            titleAr = "أين قاعة المحاضرة؟",
            titleId = "Di Mana Ruang Kuliah?",
            missionType = "USE",
            difficulty = "Medium",
            objective = "استعلم عن موقع القاعة 203 في الطابق الثاني بجانب مكتب الأستاذ.",
            xp = 40,
            orderNumber = 2,
            characterSpeakerId = "teacher_male_01",
            characterNameAr = "طالب جامعي",
            activities = listOf(
                ActivityModel(
                    id = "act_u2_1",
                    activityType = "SENTENCE_BUILDER",
                    speakerId = "teacher_male_01",
                    speakerNameAr = "طالب جامعي",
                    promptAr = "اسأل زميلك في الحرم الجامعي: (Permisi, di mana ruang 203?)",
                    promptId = "Susun: عَفْوًا، أَيْنَ قَاعَةُ مِائَتَيْنِ وَثَلاَثَةٍ؟",
                    audioTextAr = "عَفْوًا، أَيْنَ قَاعَةُ 203؟",
                    wordChips = listOf("عَفْوًا،", "أَيْنَ", "قَاعَةُ", "203؟", "مَتَى"),
                    correctAnswer = "عَفْوًا، أَيْنَ قَاعَةُ 203؟",
                    hint1 = "عفواً = permisi.",
                    hint2 = "قاعة = aula / ruang kuliah.",
                    hint3 = "عَفْوًا، أَيْنَ قَاعَةُ 203؟",
                    explanationAr = "قاعة 203 في الطابق الثاني بجانب مكتب الأستاذ.",
                    explanationId = "Ruang 203 ada di lantai 2 sebelah kantor dosen."
                )
            )
        ),
        MissionModel(
            id = "m_univ_3",
            locationId = "loc_university",
            titleAr = "مشكلة في المحاضرة",
            titleId = "Kendala di Tengah Kuliah",
            missionType = "PROBLEM_SOLVING",
            difficulty = "Hard",
            objective = "طلب المحاضر فتح الصفحة، لكن الصوت لم يكن واضحاً! استفسر عن رقم الصفحة بدقة.",
            xp = 70,
            orderNumber = 3,
            characterSpeakerId = "teacher_male_01",
            characterNameAr = "د. كمال",
            activities = listOf(
                ActivityModel(
                    id = "act_u3_1",
                    activityType = "SPEAKING",
                    speakerId = "teacher_male_01",
                    speakerNameAr = "د. كمال",
                    promptAr = "استأذن واسأل الدكتور بلطف: (Maaf dokter, halaman berapa?)",
                    promptId = "Ucapkan atau ketik: عَفْوًا يَا دُكْتُورُ، أَيُّ صَفْحَةٍ؟",
                    audioTextAr = "عَفْوًا يَا دُكْتُورُ، أَيُّ صَفْحَةٍ؟",
                    correctAnswer = "عفوا يا دكتور أي صفحة",
                    wordChips = listOf("عَفْوًا", "يَا", "دُكْتُورُ،", "أَيُّ", "صَفْحَةٍ؟", "كَمْ"),
                    hint1 = "أي = yang mana / berapa.",
                    hint2 = "صفحة = halaman.",
                    hint3 = "عَفْوًا يَا دُكْتُورُ، أَيُّ صَفْحَةٍ؟",
                    explanationAr = "أجاب الدكتور بصوت جهوري: 'الصَّفْحَةُ العِشْرُونَ!'.",
                    explanationId = "Dosen mengulangi: Halaman 20 (الصفحة العشرون)!"
                )
            )
        ),

        // ==================== 📚 المكتبة (Library) ====================
        MissionModel(
            id = "m_lib_1",
            locationId = "loc_library",
            titleAr = "أريد كتابًا",
            titleId = "Saya Mau Meminjam Buku",
            missionType = "RECOGNIZE",
            difficulty = "Easy",
            objective = "تعرف على مجالات الكتب: اللغة العربية، التاريخ، الرياضيات، والعلوم.",
            xp = 20,
            orderNumber = 1,
            characterSpeakerId = "teacher_female_01",
            characterNameAr = "أمينة المكتبة ليلى",
            activities = listOf(
                ActivityModel(
                    id = "act_l1_1",
                    activityType = "MULTIPLE_CHOICE",
                    speakerId = "teacher_female_01",
                    speakerNameAr = "أمينة المكتبة ليلى",
                    promptAr = "سألتك: 'كَيْفَ أُسَاعِدُكَ؟' أي خيار يعني (Buku Bahasa Arab)?",
                    promptId = "Pilih kata untuk Buku Bahasa Arab:",
                    audioTextAr = "أُرِيدُ كِتَابًا فِي اللُّغَةِ العَرَبِيَّةِ.",
                    options = listOf("كِتَابٌ فِي اللُّغَةِ العَرَبِيَّةِ", "كِتَابُ التَّارِيخِ", "كِتَابُ الرِّيَاضِيَّاتِ", "كِتَابُ العُلُومِ"),
                    correctAnswer = "كِتَابٌ فِي اللُّغَةِ العَرَبِيَّةِ",
                    hint1 = "اللغة العربية = Bahasa Arab.",
                    hint2 = "كتاب في اللغة العربية.",
                    hint3 = "كِتَابٌ فِي اللُّغَةِ العَرَبِيَّةِ",
                    explanationAr = "طلب سليم ومحدد لكتب قسم تعليم العربية.",
                    explanationId = "Buku pembelajaran bahasa Arab di perpustakaan."
                )
            )
        ),
        MissionModel(
            id = "m_lib_2",
            locationId = "loc_library",
            titleAr = "ابحث عن الكتاب",
            titleId = "Cari Buku di Rak",
            missionType = "USE",
            difficulty = "Medium",
            objective = "اتباع نظام أرقام الرفوف وقسم المعاجم والقواميس.",
            xp = 40,
            orderNumber = 2,
            characterSpeakerId = "teacher_female_01",
            characterNameAr = "أمينة المكتبة ليلى",
            activities = listOf(
                ActivityModel(
                    id = "act_l2_1",
                    activityType = "LISTENING",
                    speakerId = "teacher_female_01",
                    speakerNameAr = "أمينة المكتبة ليلى",
                    promptAr = "استمع لموقع معجم اللغة العربية في المكتبة:",
                    promptId = "Dengarkan petunjuk lokasi kamus:",
                    audioTextAr = "الكِتَابُ فِي الرَّفِّ الثَّالِثِ، بِجَانِبِ القَامُوسِ الكَبِيرِ.",
                    options = listOf(
                        "Di rak ketiga, di samping kamus besar",
                        "Di dekat pintu masuk utama",
                        "على طاولة الاستعارة",
                        "في الطابق السفلي"
                    ),
                    correctAnswer = "Di rak ketiga, di samping kamus besar",
                    hint1 = "الرف الثالث = rak ketiga.",
                    hint2 = "بجانب القاموس = di samping kamus.",
                    hint3 = "الكتاب في الرف الثالث بجانب القاموس.",
                    explanationAr = "موقع الكتاب دقيق في الرف الثالث بجانب القاموس.",
                    explanationId = "Letak buku di rak nomor tiga di samping kamus besar."
                )
            )
        ),
        MissionModel(
            id = "m_lib_3",
            locationId = "loc_library",
            titleAr = "الكتاب المفقود",
            titleId = "Buku yang Tertinggal",
            missionType = "PROBLEM_SOLVING",
            difficulty = "Hard",
            objective = "سألتك أمينة المكتبة عن كتاب استعرته أمس، واكتشفت أنك نسيته في قاعة المحاضرات!",
            xp = 80,
            orderNumber = 3,
            characterSpeakerId = "teacher_female_01",
            characterNameAr = "أمينة المكتبة ليلى",
            activities = listOf(
                ActivityModel(
                    id = "act_l3_1",
                    activityType = "DECISION_BRANCH",
                    speakerId = "teacher_female_01",
                    speakerNameAr = "أمينة المكتبة ليلى",
                    promptAr = "قالت: 'أَيْنَ الكِتَابُ المُسْتَعَارُ يَا أَحْمَدُ؟' كيف تجيب بأمانة ووضوح؟",
                    promptId = "Pustakawan menanyakan buku pinjaman. Bagaimana kamu menjawab jujur?",
                    audioTextAr = "أَيْنَ الكِتَابُ المُسْتَعَارُ يَا أَحْمَدُ؟",
                    options = listOf(
                        "عَفْوًا، نَسِيتُهُ فِي قَاعَةِ الدَّرْسِ، سَأُحْضِرُهُ حَالاً!",
                        "لَمْ أَسْتَعِرْ أَيَّ كِتَابٍ أَبَدًا",
                        "الكتاب ضاع في الشارع ولن أرجعه"
                    ),
                    correctAnswer = "عَفْوًا، نَسِيتُهُ فِي قَاعَةِ الدَّرْسِ، سَأُحْضِرُهُ حَالاً!",
                    hint1 = "اعترف بالنسيان واقترح إحضاره فوراً.",
                    hint2 = "نسيته في قاعة الدرس = saya lupa di ruang kelas.",
                    hint3 = "عفواً نسيته وسأحضره حالاً.",
                    explanationAr = "شكرت أمينة المكتبة أمانتك وسرعة تصرفك لإحضار الكتاب.",
                    explanationId = "Pustakawan menghargai kejujuranmu dan menunggumu mengambil buku."
                )
            )
        ),

        // ==================== 🧑‍🏫 قاعة الدرس (Classroom) ====================
        MissionModel(
            id = "m_class_1",
            locationId = "loc_classroom",
            titleAr = "أدوات الدراسة",
            titleId = "Peralatan Belajar",
            missionType = "RECOGNIZE",
            difficulty = "Easy",
            objective = "تعرف على أسماء الأدوات المدرسية والأفعال الأمرية (خذ، افتح، ضع).",
            xp = 20,
            orderNumber = 1,
            characterSpeakerId = "teacher_male_01",
            characterNameAr = "الأستاذ أحمد",
            activities = listOf(
                ActivityModel(
                    id = "act_c1_1",
                    activityType = "MULTIPLE_CHOICE",
                    speakerId = "teacher_male_01",
                    speakerNameAr = "الأستاذ أحمد",
                    promptAr = "قال الأستاذ: 'افْتَحِ الدَّفْتَرَ وَاكْتُبْ بِـ...' بأي أداة نكتب؟",
                    promptId = "Dengan alat apa kita menulis?",
                    audioTextAr = "اكْتُبْ بِالقَلَمِ فِي دَفْتَرِكَ.",
                    options = listOf("القَلَمِ", "المِمْحَاةِ", "المِسْطَرَةِ", "الحَقِيبَةِ"),
                    correctAnswer = "القَلَمِ",
                    hint1 = "أداة الحبر أو الرصاص للكتابة.",
                    hint2 = "القلم = pena/pulpen.",
                    hint3 = "القَلَمِ",
                    explanationAr = "القَلَم هو أداة التدوين والكتابة.",
                    explanationId = "Al-Qalam (القلم) adalah pena untuk menulis."
                )
            )
        ),
        MissionModel(
            id = "m_class_2",
            locationId = "loc_classroom",
            titleAr = "اسأل الأستاذ",
            titleId = "Bertanya kepada Guru",
            missionType = "USE",
            difficulty = "Medium",
            objective = "التفاعل الصفي والسؤال عن معاني المفردات غير المفهومة: ماذا يعني هذا؟",
            xp = 50,
            orderNumber = 2,
            characterSpeakerId = "teacher_male_01",
            characterNameAr = "الأستاذ أحمد",
            activities = listOf(
                ActivityModel(
                    id = "act_c2_1",
                    activityType = "SPEAKING",
                    speakerId = "teacher_male_01",
                    speakerNameAr = "الأستاذ أحمد",
                    promptAr = "ارفع يدك واسأل بأدب: (Permisi Ustadz, apa arti kata ini?)",
                    promptId = "Ucapkan atau ketik: عَفْوًا يَا أُسْتَاذُ، مَاذَا يَعْنِي هَذَا؟",
                    audioTextAr = "عَفْوًا يَا أُسْتَاذُ، مَاذَا يَعْنِي هَذَا؟",
                    correctAnswer = "عفوا يا أستاذ ماذا يعني هذا",
                    wordChips = listOf("عَفْوًا", "يَا", "أُسْتَاذُ،", "مَاذَا", "يَعْنِي", "هَذَا؟"),
                    hint1 = "ماذا يعني = apa artinya.",
                    hint2 = "هذا = ini.",
                    hint3 = "عَفْوًا يَا أُسْتَاذُ، مَاذَا يَعْنِي هَذَا؟",
                    explanationAr = "أجاب الأستاذ بابتسامة وشرح الكلمة بالتفصيل!",
                    explanationId = "Guru tersenyum dan menjelaskan arti kata tersebut dengan senang hati."
                )
            )
        ),
        MissionModel(
            id = "m_class_3",
            locationId = "loc_classroom",
            titleAr = "الاختبار المفاجئ",
            titleId = "Kuis Mendadak",
            missionType = "PROBLEM_SOLVING",
            difficulty = "Hard",
            objective = "اختبار شامل يجمع مهارات الاستماع والمفردات والقراءة وحل المشكلات للحصول على وسام الطالب المجتهد!",
            xp = 200,
            orderNumber = 3,
            characterSpeakerId = "teacher_male_01",
            characterNameAr = "الأستاذ أحمد",
            activities = listOf(
                ActivityModel(
                    id = "act_c3_1",
                    activityType = "LISTENING",
                    speakerId = "teacher_male_01",
                    speakerNameAr = "الأستاذ أحمد",
                    promptAr = "السؤال الأول: استمع إلى الجملة وحدد المعنى المقصود بدقة:",
                    promptId = "Dengarkan audio kuis dengan seksama:",
                    audioTextAr = "الطَّالِبُ النَّشِيطُ يَكْتُبُ دُرُوسَهُ بِالقَلَمِ فِي الفَصْلِ.",
                    options = listOf(
                        "Siswa yang rajin menulis pelajarannya dengan pena di kelas",
                        "Guru membaca buku di perpustakaan kampus",
                        "Pelayan membawa makanan ke meja makan",
                        "Sopir taksi mengemudi di jalan raya"
                    ),
                    correctAnswer = "Siswa yang rajin menulis pelajarannya dengan pena di kelas",
                    hint1 = "استمع لكلمة 'الطالب النشيط'.",
                    hint2 = "يكتب دروسه بالقلم في الفصل.",
                    hint3 = "Siswa rajin menulis dengan pulpen di kelas.",
                    explanationAr = "إجابة متقنة ومطابقة تماماً للنص المسموع!",
                    explanationId = "Jawaban sempurna sesuai dengan rekaman suara ustadz."
                ),
                ActivityModel(
                    id = "act_c3_2",
                    activityType = "SENTENCE_BUILDER",
                    speakerId = "teacher_male_01",
                    speakerNameAr = "الأستاذ أحمد",
                    promptAr = "السؤال الثاني: رتب جملة الحكمة الختامية: (Ilmu itu cahaya di dalam hati)",
                    promptId = "Susun kalimat: العِلْمُ نُورٌ فِي القَلْبِ",
                    audioTextAr = "العِلْمُ نُورٌ فِي القَلْبِ.",
                    wordChips = listOf("العِلْمُ", "نُورٌ", "فِي", "القَلْبِ.", "كَبِيرٌ"),
                    correctAnswer = "العِلْمُ نُورٌ فِي القَلْبِ",
                    hint1 = "العلم = ilmu.",
                    hint2 = "نور = cahaya.",
                    hint3 = "العِلْمُ نُورٌ فِي القَلْبِ.",
                    explanationAr = "مبارك! نلت الدرجة الكاملة في الاختبار المفاجئ واستحققت وسام الطالب المجتهد!",
                    explanationId = "Selamat! Nilai sempurna untuk kuis mendadak, lencana Siswa Rajin terbuka!"
                )
            )
        )
    )
}
