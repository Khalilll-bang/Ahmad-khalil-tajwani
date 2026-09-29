package com.example.data.seed

import com.example.data.model.AchievementModel
import com.example.data.model.LocationModel
import com.example.data.model.SpeakerProfile
import com.example.data.model.VocabularyEntity

object SeedLocations {
    val locations = listOf(
        // Level 1: الحياة اليومية
        LocationModel("loc_house", 1, "البيت", "Rumah", "🏠", "تعلم مفردات البيت ومواضع الأشياء وحل مشكلة المفتاح المفقود.", 1),
        LocationModel("loc_shop", 1, "المتجر", "Toko", "🛒", "شراء الاحتياجات اليومية، السؤال عن الأسعار، واستخدام الأرقام.", 2),
        LocationModel("loc_restaurant", 1, "المطعم", "Restoran", "🍽️", "طلب الطعام والشراب، التعامل مع النادل، وحل خطأ في الطلب.", 3),
        LocationModel("loc_grocery", 1, "البقالة", "Minimarket", "🏪", "البحث عن المنتجات واتباع الاتجاهات والتسوق السريع.", 4),

        // Level 2: حياتي الدراسية
        LocationModel("loc_school", 2, "المدرسة", "Sekolah", "🏫", "التعريف بالنفس، السؤال عن الفصول، والاعتذار عند التأخر.", 5),
        LocationModel("loc_university", 2, "الجامعة", "Kampus", "🎓", "اليوم الأول في الحرم الجامعي، قاعات المحاضرات، والتفاعل الأكاديمي.", 6),
        LocationModel("loc_library", 2, "المكتبة", "Perpustakaan", "📚", "استعارة الكتب، البحث في الرفوف، والبحث عن كتاب مفقود.", 7),
        LocationModel("loc_classroom", 2, "قاعة الدرس", "Ruang Kelas", "🧑‍🏫", "أدوات الدراسة، توجيهات الأستاذ، والتحدي الشامل للاختبار المفاجئ.", 8),

        // Level 3: الخدمات العامة
        LocationModel("loc_hospital", 3, "المستشفى", "Rumah Sakit", "🏥", "وصف أجزاء الجسم، الأعراض الصحية، وقسم الطوارئ.", 9),
        LocationModel("loc_pharmacy", 3, "الصيدلية", "Apotek", "💊", "شراء الأدوية والفيتامينات والاستفسار عن الأسعار.", 10),
        LocationModel("loc_bank", 3, "البنك", "Bank", "🏦", "فتح حساب مصرفي، تعبئة الاستمارات، وحل مشكلة البطاقة المتعطلة.", 11),
        LocationModel("loc_post", 3, "مكتب البريد", "Kantor Pos", "🏤", "إرسال الطرود، العناوين، والاختيار بين البريد العادي والمستعجل.", 12),
        LocationModel("loc_police", 3, "مركز الشرطة", "Kantor Polisi", "🚔", "الإبلاغ عن المقتنيات المفقودة وتحديد الأماكن والأوصاف بدقة.", 13),

        // Level 4: السفر والتنقل
        LocationModel("loc_taxi", 4, "سيارة الأجرة", "Taksi", "🚕", "طلب التاكسي، تحديد الوجهة والأجرة، وتصحيح مسار الطريق.", 14),
        LocationModel("loc_bus", 4, "محطة الحافلات", "Terminal Bus", "🚌", "شراء التذاكر، مواعيد الأرصفة، ومواجهة فوات الحافلة.", 15),
        LocationModel("loc_train", 4, "محطة القطار", "Stasiun Kereta", "🚆", "إعلانات المحطة الصوتية، الأرصفة، وتدارك ركوب القطار الخطأ.", 16),
        LocationModel("loc_airport", 4, "المطار", "Bandara", "✈️", "جواز السفر، لوحة المغادرة، بوابات الصعود، والبحث عن الجواز.", 17),
        LocationModel("loc_hotel", 4, "الفندق", "Hotel", "🏨", "حجز الغرف، استلام المفتاح، وحل عطل بطاقة الدخول.", 18),

        // Level 5: المجتمع والتواصل
        LocationModel("loc_mosque", 5, "المسجد", "Masjid", "🕌", "التحية الإسلامية، السؤال عن أماكن الوضوء، وتقديم المساعدة للآخرين.", 19),
        LocationModel("loc_cafe", 5, "المقهى", "Kafe", "☕", "لقاء الأصدقاء، طلب المشروبات، والحديث الودي ودفع الحساب.", 20),
        LocationModel("loc_downtown", 5, "وسط المدينة", "Pusat Kota", "🏙️", "التنقل في وسط المدينة، تغيير الخطط مع الأصدقاء، واليوم المزدحم.", 21)
    )

    val speakers = mapOf(
        "teacher_male_01" to SpeakerProfile("teacher_male_01", "الأستاذ أحمد", "معلم لغة عربية", "MALE", 0.90f, 0.95f, "فصحى هادئة وتعليمية"),
        "teacher_female_01" to SpeakerProfile("teacher_female_01", "الأستاذة فاطمة", "معلمة لغة عربية", "FEMALE", 1.25f, 0.95f, "فصحى ودودة وواضحة"),
        "waiter_male_01" to SpeakerProfile("waiter_male_01", "النادل كريم", "نادل المطعم", "MALE", 0.88f, 1.0f, "مهذب ومرحب"),
        "waiter_female_01" to SpeakerProfile("waiter_female_01", "النادلة سارة", "نادلة المطعم", "FEMALE", 1.22f, 1.0f, "لطيفة ومرحبة"),
        "doctor_male_01" to SpeakerProfile("doctor_male_01", "الدكتور زياد", "طبيب عام", "MALE", 0.85f, 0.92f, "طبيب وقور ومتعاطف"),
        "doctor_female_01" to SpeakerProfile("doctor_female_01", "الدكتورة مريم", "طبيبة عامة", "FEMALE", 1.24f, 0.92f, "مهنية ومتعاطفة"),
        "driver_male_01" to SpeakerProfile("driver_male_01", "السائق أبو فهد", "سائق سيارة أجرة", "MALE", 0.86f, 1.02f, "طبيعي وعفوي"),
        "driver_female_01" to SpeakerProfile("driver_female_01", "السائقة أمينة", "سائقة تاكسي عائلية", "FEMALE", 1.20f, 1.0f, "هادئة ومحترفة"),
        "receptionist_01" to SpeakerProfile("receptionist_01", "موظف الاستقبال طارق", "استقبال الفندق", "MALE", 0.92f, 1.0f, "رسمي ومساعد"),
        "receptionist_female_01" to SpeakerProfile("receptionist_female_01", "موظفة الاستقبال ليلى", "استقبال الفندق", "FEMALE", 1.22f, 1.0f, "لبقة وودودة"),
        "police_male_01" to SpeakerProfile("police_male_01", "الضابط خالد", "ضابط شرطة", "MALE", 0.82f, 0.95f, "حازم ودقيق"),
        "police_female_01" to SpeakerProfile("police_female_01", "المفتشة سلمى", "ضابطة شرطة", "FEMALE", 1.20f, 0.95f, "حازمة ونظامية"),
        "pharmacist_male_01" to SpeakerProfile("pharmacist_male_01", "الصيدلي عمر", "صيدلي", "MALE", 0.90f, 0.95f, "دقيق وناصح"),
        "pharmacist_female_01" to SpeakerProfile("pharmacist_female_01", "الصيدلانية ريم", "صيدلانية", "FEMALE", 1.22f, 0.95f, "ناصحة ومبسطة"),
        "friend_male_01" to SpeakerProfile("friend_male_01", "الصديق بلال", "صديق وزميل", "MALE", 0.92f, 1.0f, "ودود واجتماعي"),
        "friend_female_01" to SpeakerProfile("friend_female_01", "الصديقة هدى", "صديقة وزميلة", "FEMALE", 1.25f, 1.0f, "مرحة ونشيطة"),
        "shopper_male_01" to SpeakerProfile("shopper_male_01", "البائع محمود", "بائع البقالة والمتجر", "MALE", 0.88f, 0.98f, "صبور وخبير"),
        "shopper_female_01" to SpeakerProfile("shopper_female_01", "البائعة نورة", "بائعة المتجر", "FEMALE", 1.22f, 0.98f, "بشوشة وترحابية")
    )

    val achievements = listOf(
        AchievementModel("ach_key", "🗝️ الباحث عن المفتاح", "Pencari Kunci", "عثرت على المفتاح المفقود في البيت بنجاح.", "🗝️", requiredMissionId = "m_house_3"),
        AchievementModel("ach_restaurant", "🍽️ المتحدث في المطعم", "Penutur di Restoran", "حللت مشكلة الطلب الخاطئ مع النادل بلباقة.", "🍽️", requiredMissionId = "m_restaurant_3"),
        AchievementModel("ach_shopper", "🛒 المتسوق الذكي", "Pembelanja Cerdas", "أكملت جولة التسوق السريع في البقالة بدون أخطاء.", "🛒", requiredMissionId = "m_grocery_3"),
        AchievementModel("ach_student", "🧑‍🎓 طالب مجتهد", "Siswa Rajin", "اجتزت الاختبار المفاجئ في قاعة الدرس بدرجة ممتازة.", "🧑‍🎓", requiredMissionId = "m_classroom_3"),
        AchievementModel("ach_taxi", "🚕 المسافر الذكي", "Pelancong Cerdas", "صححت مسار التاكسي ووصلت إلى وجهتك بأمان.", "🚕", requiredMissionId = "m_taxi_3"),
        AchievementModel("ach_travel_expert", "🚆 خبير السفر", "Ahli Perjalanan", "تداركت خطأ ركوب القطار ووصلت إلى محطتك الصحيحة.", "🚆", requiredMissionId = "m_train_3"),
        AchievementModel("ach_airport", "✈️ مسافر مستعد", "Musafir Siap", "عثرت على جواز سفرك وصعدت للطائرة في الموعد.", "✈️", requiredMissionId = "m_airport_3"),
        AchievementModel("ach_hotel", "🏨 نزيل ذكي", "Tamu Cerdas", "حللت مشكلة مفتاح الغرفة وتواصلت مع الاستقبال.", "🏨", requiredMissionId = "m_hotel_3"),
        AchievementModel("ach_communicator", "🕌 متواصل جيد", "Komunikator Baik", "أجبت بلباقة واعتذرت بأدب في باحة المسجد.", "🕌", requiredMissionId = "m_mosque_3"),
        AchievementModel("ach_cafe", "☕ زبون ذكي", "Pelanggan Cerdas", "دفعت الحساب وأجريت محادثة ممتعة في المقهى.", "☕", requiredMissionId = "m_cafe_3"),
        AchievementModel("ach_explorer", "🏙️ مستكشف المدينة", "Penjelajah Kota", "أنجزت جميع المهام في وسط المدينة بنجاح.", "🏙️", requiredMissionId = "m_downtown_3"),
        AchievementModel("ach_master_day", "🌟 بطل اليوم الكامل", "Master Hari Penuh", "أتممت محاكاة التحدي الكبير ليوم كامل باللغة العربية!", "🌟", requiredMissionId = "m_final_challenge")
    )
}
