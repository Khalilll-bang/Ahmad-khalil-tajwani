package com.example.data.seed

import com.example.data.model.VocabularyEntity

object SeedVocabulary {
    val initialVocabulary = listOf(
        VocabularyEntity("v_bab", "باب", "Pintu", "بَابٌ", "فَتَحَ أَحْمَدُ البَابَ.", "Ahmad membuka pintu.", "house", 75),
        VocabularyEntity("v_nafidha", "نافذة", "Jendela", "نَافِذَةٌ", "النَّافِذَةُ مَفْتُوحَةٌ.", "Jendela itu terbuka.", "house", 65),
        VocabularyEntity("v_tawilah", "طاولة", "Meja", "طَاوِلَةٌ", "الكِتَابُ عَلَى الطَّاوِلَةِ.", "Buku itu di atas meja.", "house", 80),
        VocabularyEntity("v_kursi", "كرسي", "Kursi", "كُرْسِيٌّ", "أَجْلِسُ عَلَى الكُرْسِيِّ.", "Saya duduk di atas kursi.", "house", 90),
        VocabularyEntity("v_sarir", "سرير", "Tempat tidur", "سَرِيرٌ", "أَنَامُ عَلَى السَّرِيرِ.", "Saya tidur di ranjang.", "house", 70),
        VocabularyEntity("v_kitab", "كتاب", "Buku", "كِتَابٌ", "أَقْرَأُ كِتَابَ اللُّغَةِ.", "Saya membaca buku bahasa.", "house", 95),
        VocabularyEntity("v_haqibah", "حقيبة", "Tas", "حَقِيبَةٌ", "حَقِيبَتِي جَدِيدَةٌ.", "Tas saya baru.", "house", 85),
        VocabularyEntity("v_miftah", "مفتاح", "Kunci", "مِفْتَاحٌ", "أَبْحَثُ عَنْ مِفْتَاحِي.", "Saya mencari kunciku.", "house", 60),

        VocabularyEntity("v_ma", "ماء", "Air", "مَاءٌ", "أَشْرَبُ المَاءَ البَارِدَ.", "Saya minum air dingin.", "food", 90),
        VocabularyEntity("v_halib", "حليب", "Susu", "حَلِيبٌ", "الحَلِيبُ طَازَجٌ.", "Susunya segar.", "food", 85),
        VocabularyEntity("v_khubz", "خبز", "Roti", "خُبْزٌ", "اشْتَرَيْتُ الخُبْزَ.", "Saya membeli roti.", "food", 80),
        VocabularyEntity("v_aruzz", "أرز", "Nasi", "أَرُزٌّ", "آكُلُ الأَرُزَّ مَعَ الدَّجَاجِ.", "Saya makan nasi dengan ayam.", "food", 90),
        VocabularyEntity("v_bayd", "بيض", "Telur", "بَيْضٌ", "البَيْضُ مَسْلُوقٌ.", "Telurnya direbus.", "food", 70),
        VocabularyEntity("v_sukkar", "سكر", "Gula", "سُكَّرٌ", "شَايٌ بِدُونِ سُكَّرٍ.", "Teh tanpa gula.", "food", 75),
        VocabularyEntity("v_dajaj", "دجاج", "Ayam", "دَجَاجٌ", "أُرِيدُ دَجَاجًا مَشْوِيًّا.", "Saya mau ayam bakar.", "food", 85),
        VocabularyEntity("v_lahm", "لحم", "Daging", "لَحْمٌ", "هَذَا لَحْمٌ لَذِيذٌ.", "Ini daging lezat.", "food", 65),
        VocabularyEntity("v_samak", "سمك", "Ikan", "سَمَكٌ", "السَّمَكُ طَازَجٌ مِنَ البَحْرِ.", "Ikannya segar dari laut.", "food", 70),
        VocabularyEntity("v_ashir", "عصير", "Jus", "عَصِيرٌ", "عَصِيرُ البُرْتُقَالِ لَذِيذٌ.", "Jus jeruk itu enak.", "food", 85),

        VocabularyEntity("v_yamin", "يمين", "Kanan", "يَمِينٌ", "ادْخُلْ إِلَى الجِهَةِ اليُمْنَى.", "Masuklah ke sebelah kanan.", "directions", 50),
        VocabularyEntity("v_yasar", "يسار", "Kiri", "يَسَارٌ", "المَكْتَبُ عَلَى اليَسَارِ.", "Kantor berada di sebelah kiri.", "directions", 50),
        VocabularyEntity("v_amam", "أمام", "Depan", "أَمَامَ", "السَّيَّارَةُ أَمَامَ البَيْتِ.", "Mobil di depan rumah.", "directions", 65),
        VocabularyEntity("v_khalf", "خلف", "Belakang", "خَلْفَ", "المَسْجِدُ خَلْفَ المَدْرَسَةِ.", "Masjid di belakang sekolah.", "directions", 60),

        VocabularyEntity("v_qalam", "قلم", "Pena", "قَلَمٌ", "كَتَبْتُ بِالقَلَمِ.", "Saya menulis dengan pena.", "study", 95),
        VocabularyEntity("v_daftar", "دفتر", "Buku tulis", "دَفْتَرٌ", "افْتَحِ الدَّفْتَرَ.", "Bukalah buku catatan.", "study", 85),
        VocabularyEntity("v_sabburah", "سبورة", "Papan tulis", "سَبُّورَةٌ", "انْظُرْ إِلَى السَّبُّورَةِ.", "Lihatlah ke papan tulis.", "study", 75),

        VocabularyEntity("v_raas", "رأس", "Kepala", "رَأْسٌ", "أَشْعُرُ بِأَلَمٍ فِي رَأْسِي.", "Saya merasa sakit kepala.", "health", 70),
        VocabularyEntity("v_batn", "بطن", "Perut", "بَطْنٌ", "وَجَعٌ فِي البَطْنِ.", "Nyeri pada perut.", "health", 65),
        VocabularyEntity("v_dawa", "دواء", "Obat", "دَوَاءٌ", "تَنَاوَلْ هَذَا الدَّوَاءَ.", "Minumlah obat ini.", "health", 80),
        VocabularyEntity("v_kammah", "كمامة", "Masker", "كَمَّامَةٌ", "الْبَسِ الكَمَّامَةَ.", "Pakailah masker.", "health", 85),

        VocabularyEntity("v_jawaz", "جواز السفر", "Paspor", "جَوَازُ السَّفَرِ", "أَيْنَ جَوَازُ سَفَرِكَ؟", "Di mana paspormu?", "travel", 80),
        VocabularyEntity("v_tadhkirah", "تذكرة", "Tiket", "تَذْكِرَةٌ", "حَجَزْتُ تَذْكِرَةَ القِطَارِ.", "Saya memesan tiket kereta.", "travel", 85),
        VocabularyEntity("v_bawwabah", "بوابة", "Gerbang / Gate", "بَوَّابَةٌ", "البَوَّابَةُ رَقْمُ سَبْعَةٍ.", "Pintu gerbang nomor 7.", "travel", 75),
        VocabularyEntity("v_funduq", "فندق", "Hotel", "فُنْدُقٌ", "أَسْكُنُ فِي الفُنْدُقِ.", "Saya menginap di hotel.", "travel", 80),

        VocabularyEntity("v_masjid", "مسجد", "Masjid", "مَسْجِدٌ", "نُصَلِّي فِي المَسْجِدِ.", "Kami sholat di masjid.", "community", 95),
        VocabularyEntity("v_wudu", "وضوء", "Wudhu", "وُضُوءٌ", "أَيْنَ مَكَانُ الوُضُوءِ؟", "Di mana tempat wudhu?", "community", 90),
        VocabularyEntity("v_qahwah", "قهوة", "Kopi", "قَهْوَةٌ", "أَشْرَبُ القَهْوَةَ صَبَاحًا.", "Saya minum kopi di pagi hari.", "community", 90)
    )
}
