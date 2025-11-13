package com.example.cafetrio.ui

import android.app.DatePickerDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cafetrio.R
import com.example.cafetrio.ui.theme.HighlandRed
import com.example.cafetrio.ui.theme.HighlandWhite
import com.example.cafetrio.ui.theme.HighlandText
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserInfScreen(
    onBackClick: () -> Unit,
    onUpdateInfoClick: () -> Unit = {},
    onDeleteAccountClick: () -> Unit = {}
) {
    var name by remember { mutableStateOf("Tuấn Nguyễn") }
    var email by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var showCurrentPassword by remember { mutableStateOf(false) }
    var showNewPassword by remember { mutableStateOf(false) }

    val context = LocalContext.current

    // Date picker
    val calendar = Calendar.getInstance()
    val currentYear = calendar.get(Calendar.YEAR)
    val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    val datePickerDialog = DatePickerDialog(
        context,
        R.style.DatePickerTheme,
        { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            birthDate = dateFormatter.format(calendar.time)
        },
        currentYear - 18,
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
    datePickerDialog.datePicker.maxDate = System.currentTimeMillis()

    // Gender Dropdown
    var expandedGenderDropdown by remember { mutableStateOf(false) }
    val genderOptions = listOf("Nam", "Nữ", "Khác")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Cập nhật thông tin",
                        color = com.example.cafetrio.ui.theme.HighlandWhite,
                        fontWeight = FontWeight.Medium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = com.example.cafetrio.ui.theme.HighlandWhite
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = com.example.cafetrio.ui.theme.HighlandRed
                )
            )
        },
        containerColor = com.example.cafetrio.ui.theme.HighlandWhite
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(com.example.cafetrio.ui.theme.HighlandWhite)
                .verticalScroll(rememberScrollState())
        ) {
            // Profile Header Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(HighlandRed)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Avatar with edit badge
                Box(
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.coffee_beans),
                            contentDescription = "Avatar",
                            modifier = Modifier.size(50.dp)
                        )
                    }

                    // Edit icon badge
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(HighlandWhite, CircleShape)
                            .padding(6.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_delete),
                            contentDescription = "Edit",
                            tint = HighlandRed,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = name.ifEmpty { "Người dùng" },
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = HighlandWhite
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Personal Info Section
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Thông tin cá nhân",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = HighlandRed
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Name Field
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Họ và tên") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = HighlandRed,
                            unfocusedBorderColor = Color.LightGray,
                            cursorColor = HighlandRed,
                            focusedLabelColor = HighlandRed
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Email Field
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = HighlandRed,
                            unfocusedBorderColor = Color.LightGray,
                            cursorColor = HighlandRed,
                            focusedLabelColor = HighlandRed
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Birth Date and Gender in Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Birth Date
                        OutlinedTextField(
                            value = birthDate,
                            onValueChange = { birthDate = it },
                            label = { Text("Ngày sinh") },
                            modifier = Modifier
                                .weight(1f)
                                .clickable { datePickerDialog.show() },
                            readOnly = true,
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = HighlandRed,
                                unfocusedBorderColor = Color.LightGray
                            )
                        )

                        // Gender
                        ExposedDropdownMenuBox(
                            expanded = expandedGenderDropdown,
                            onExpandedChange = { expandedGenderDropdown = !expandedGenderDropdown },
                            modifier = Modifier.weight(1f)
                        ) {
                            OutlinedTextField(
                                value = gender,
                                onValueChange = { },
                                label = { Text("Giới tính") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor(),
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedGenderDropdown) },
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = HighlandRed,
                                    unfocusedBorderColor = Color.LightGray
                                )
                            )
                            ExposedDropdownMenu(
                                expanded = expandedGenderDropdown,
                                onDismissRequest = { expandedGenderDropdown = false }
                            ) {
                                genderOptions.forEach { option ->
                                    DropdownMenuItem(
                                        text = { Text(option) },
                                        onClick = {
                                            gender = option
                                            expandedGenderDropdown = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Security Section
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Bảo mật",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = HighlandRed
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Current Password
                    OutlinedTextField(
                        value = currentPassword,
                        onValueChange = { currentPassword = it },
                        label = { Text("Mật khẩu hiện tại") },
                        modifier = Modifier.fillMaxWidth(),
                        visualTransformation = if (showCurrentPassword) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        trailingIcon = {
                            IconButton(onClick = { showCurrentPassword = !showCurrentPassword }) {
                                Icon(
                                    painter = painterResource(
                                        id = if (showCurrentPassword) R.drawable.eye else R.drawable.close_eye
                                    ),
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = HighlandRed,
                            unfocusedBorderColor = Color.LightGray,
                            cursorColor = HighlandRed,
                            focusedLabelColor = HighlandRed
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // New Password
                    OutlinedTextField(
                        value = newPassword,
                        onValueChange = { newPassword = it },
                        label = { Text("Mật khẩu mới") },
                        modifier = Modifier.fillMaxWidth(),
                        visualTransformation = if (showNewPassword) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        trailingIcon = {
                            IconButton(onClick = { showNewPassword = !showNewPassword }) {
                                Icon(
                                    painter = painterResource(
                                        id = if (showNewPassword) R.drawable.eye else R.drawable.close_eye
                                    ),
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = HighlandRed,
                            unfocusedBorderColor = Color.LightGray,
                            cursorColor = HighlandRed,
                            focusedLabelColor = HighlandRed
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Action Buttons
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                // Update Button
                Button(
                    onClick = onUpdateInfoClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = HighlandRed)
                ) {
                    Text(
                        text = "Cập nhật thông tin",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Delete Account
                OutlinedButton(
                    onClick = onDeleteAccountClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = HighlandRed
                    ),
                    border = androidx.compose.foundation.BorderStroke(1.dp, HighlandRed)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_delete),
                        contentDescription = null,
                        tint = HighlandRed,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Xóa tài khoản",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
