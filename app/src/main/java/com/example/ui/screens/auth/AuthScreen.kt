package com.example.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.Emerald100
import com.example.ui.theme.Emerald700
import com.example.ui.theme.Emerald800
import com.example.ui.theme.Emerald900
import com.example.ui.theme.ErrorRed
import com.example.ui.theme.Gold500
import com.example.ui.theme.Gold600
import com.example.ui.theme.Gold700
import com.example.ui.theme.Sand50
import com.example.viewmodel.MainViewModel

@Composable
fun AuthScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    var isRegisterMode by remember { mutableStateOf(false) }

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var selectedLevel by remember { mutableStateOf("BEGINNER") } // BEGINNER or INTERMEDIATE
    var selectedRole by remember { mutableStateOf("STUDENT") } // STUDENT or TEACHER

    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Emerald900, Emerald800, Sand50)
                )
            )
            .testTag("auth_screen")
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(30.dp))

            // App Emblem / Badge
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "🌍", fontSize = 42.sp)
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Main Title
            Text(
                text = "عَرَبِيٌّ فِي حَيَاتِي",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Arabiyyun fī Ḥayātī",
                fontSize = 15.sp,
                color = Gold500,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "عِشِ المَوْقِفَ، تَكَلَّمْ بِالعَرَبِيَّةِ، تَعَلَّمْ مِنَ التَّجْرِبَةِ",
                fontSize = 12.sp,
                color = Emerald100,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Auth Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    // Title inside card
                    Text(
                        text = if (isRegisterMode) "حساب جديد (Daftar Akun)" else "تسجيل الدخول (Masuk)",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Emerald800
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    if (isRegisterMode) {
                        // Full Name
                        OutlinedTextField(
                            value = name,
                            onValueChange = { name = it },
                            label = { Text("الاسم الكامل (Nama Lengkap)") },
                            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("register_name_input")
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                    }

                    // Email Field
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("البريد الإلكتروني (Email)") },
                        leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("auth_email_input")
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Password Field
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("كلمة المرور (Password)") },
                        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                        visualTransformation = PasswordVisualTransformation(),
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("auth_password_input")
                    )

                    if (isRegisterMode) {
                        Spacer(modifier = Modifier.height(10.dp))

                        // Confirm Password
                        OutlinedTextField(
                            value = confirmPassword,
                            onValueChange = { confirmPassword = it },
                            label = { Text("تأكيد كلمة المرور (Konfirmasi Password)") },
                            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                            visualTransformation = PasswordVisualTransformation(),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        // Arabic Level Selection
                        Text(text = "مستوى اللغة العربية (Level Bahasa Arab):", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            val levels = listOf("BEGINNER" to "مبتدئ (Pemula)", "INTERMEDIATE" to "متوسط (Menengah)")
                            levels.forEach { (key, label) ->
                                val isSel = selectedLevel == key
                                Surface(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(10.dp))
                                        .clickable { selectedLevel = key },
                                    color = if (isSel) Emerald800 else MaterialTheme.colorScheme.surfaceVariant
                                ) {
                                    Text(
                                        text = label,
                                        fontSize = 12.sp,
                                        fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal,
                                        color = if (isSel) Color.White else MaterialTheme.colorScheme.onSurface,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.padding(vertical = 10.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Role Selection
                        Text(text = "الدور (Peran):", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            val roles = listOf("STUDENT" to "طالب (Student)", "TEACHER" to "معلم (Teacher)")
                            roles.forEach { (key, label) ->
                                val isSel = selectedRole == key
                                Surface(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(10.dp))
                                        .clickable { selectedRole = key },
                                    color = if (isSel) Gold700 else MaterialTheme.colorScheme.surfaceVariant
                                ) {
                                    Text(
                                        text = label,
                                        fontSize = 12.sp,
                                        fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal,
                                        color = if (isSel) Color.White else MaterialTheme.colorScheme.onSurface,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.padding(vertical = 10.dp)
                                    )
                                }
                            }
                        }
                    }

                    if (errorMessage != null) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = errorMessage ?: "",
                            color = ErrorRed,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Submit Button
                    Button(
                        onClick = {
                            errorMessage = null
                            if (!isRegisterMode) {
                                if (email.isBlank() || password.isBlank()) {
                                    errorMessage = "يرجى إدخال البريد الإلكتروني وكلمة المرور."
                                    return@Button
                                }
                                isLoading = true
                                viewModel.login(
                                    email = email,
                                    pass = password,
                                    onSuccess = { isLoading = false },
                                    onError = { err ->
                                        isLoading = false
                                        errorMessage = err
                                    }
                                )
                            } else {
                                if (name.isBlank() || email.isBlank() || password.isBlank()) {
                                    errorMessage = "يرجى تعبئة كافة الحقول."
                                    return@Button
                                }
                                if (password != confirmPassword) {
                                    errorMessage = "كلمتا المرور غير متطابقتين!"
                                    return@Button
                                }
                                isLoading = true
                                viewModel.register(
                                    name = name,
                                    email = email,
                                    pass = password,
                                    level = selectedLevel,
                                    role = selectedRole,
                                    onSuccess = { isLoading = false },
                                    onError = { err ->
                                        isLoading = false
                                        errorMessage = err
                                    }
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("auth_submit_btn"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Emerald800)
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                        } else {
                            Text(
                                text = if (isRegisterMode) "إنشاء الحساب (Daftar)" else "دُخُول (Masuk)",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Switch Mode Toggle
                    TextButton(
                        onClick = {
                            isRegisterMode = !isRegisterMode
                            errorMessage = null
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = if (isRegisterMode) "لديك حساب بالفعل؟ تسجيل الدخول" else "ليس لديك حساب؟ سجل الآن مجانًا",
                            fontSize = 13.sp,
                            color = Emerald700
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Quick Demo Login options for easy testing
                    Text(
                        text = "خيارات تجريبية سريعة (Akun Demo):",
                        fontSize = 11.sp,
                        color = Color.Gray,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(
                            onClick = {
                                email = "ahmad@student.com"
                                password = "123456"
                                viewModel.login(email, password, {}, {})
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(text = "طالب (Student)", fontSize = 11.sp)
                        }

                        OutlinedButton(
                            onClick = {
                                email = "ustadz@teacher.com"
                                password = "teacher123"
                                viewModel.login(email, password, {}, {})
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(text = "معلم (Teacher)", fontSize = 11.sp)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}
