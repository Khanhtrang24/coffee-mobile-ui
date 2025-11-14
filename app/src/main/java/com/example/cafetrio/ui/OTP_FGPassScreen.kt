package com.example.cafetrio.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cafetrio.data.api.ApiClient
import com.example.cafetrio.data.dto.ResendOtpRequest
import com.example.cafetrio.ui.components.OtpTextField
import com.example.cafetrio.ui.theme.CafeBeige
import com.example.cafetrio.ui.theme.CafeBrown
import com.example.cafetrio.ui.theme.CafeButtonBackground
import com.example.cafetrio.ui.theme.CafeLoginBackground
import com.example.cafetrio.ui.theme.CafeTrioTheme
import com.example.cafetrio.ui.theme.HighlandRed
import com.example.cafetrio.ui.theme.HighlandText
import kotlinx.coroutines.delay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale

@Composable
fun OTP_FGPassScreen(
    emailAddress: String = "example@gmail.com",
    onBackClick: () -> Unit = {},
    onVerifyOtp: (String) -> Unit = {}
) {
    var otpValue by remember { mutableStateOf("") }
    var timeRemaining by remember { mutableStateOf(120) } // 2 phút = 120 giây
    var isResendEnabled by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    
    val context = LocalContext.current
    
    // Đếm ngược thời gian
    LaunchedEffect(key1 = true) {
        while (timeRemaining > 0) {
            delay(1000) // Đợi 1 giây
            timeRemaining--
        }
        isResendEnabled = true
    }
    
    // Format thời gian còn lại dạng mm:ss
    val minutes = timeRemaining / 60
    val seconds = timeRemaining % 60
    val timeString = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
    
    val handleVerifyOtp = {
        Toast.makeText(context, "Xác thực OTP thành công!", Toast.LENGTH_SHORT).show()
        onVerifyOtp(otpValue.ifEmpty { "123456" })
    }
    
    val handleResendOtp = {
        if (isResendEnabled || timeRemaining <= 0) {
            Toast.makeText(context, "Đã gửi lại mã OTP", Toast.LENGTH_SHORT).show()
            timeRemaining = 120
            isResendEnabled = false
        }
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CafeLoginBackground),
        contentAlignment = Alignment.Center // Căn chỉnh tất cả về trung tâm
    ) {
        // Nút đóng (X) ở góc trên bên phải
        Text(
            text = "×",
            color = Color.Black,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .clickable(onClick = onBackClick)
                .padding(32.dp)
        )
        
        // Hiển thị loading khi đang xử lý API
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = CafeBrown
            )
        }
        
        // Nội dung chính - căn giữa màn hình
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Tiêu đề xác nhận OTP
            Text(
                text = "Xác nhận Mã OTP",
                color = HighlandRed,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(12.dp))

            // Mô tả
            Text(
                text = "Mã xác thực OTP đã được gửi đến",
                color = HighlandText,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(4.dp))

            // Email
            Text(
                text = emailAddress,
                color = HighlandRed,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(32.dp))

            // OTP Input Field
            OtpTextField(
                otpText = otpValue,
                onOtpTextChange = { value, otpInputFilled ->
                    otpValue = value
                }
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Thời gian còn lại
            Text(
                text = "Thời gian còn lại: $timeString",
                color = if (timeRemaining > 0) HighlandText else HighlandRed,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Gửi lại OTP
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Không nhận được mã OTP? ",
                    color = HighlandText,
                    fontSize = 14.sp
                )
                Text(
                    text = "Gửi lại",
                    color = if (isResendEnabled || timeRemaining <= 0) HighlandRed else Color.Gray,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable(enabled = isResendEnabled || timeRemaining <= 0) {
                        handleResendOtp()
                    }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Nút xác nhận
            Button(
                onClick = { handleVerifyOtp() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = HighlandRed
                ),
                enabled = otpValue.length == 6 && !isLoading
            ) {
                Text(
                    text = "Xác nhận",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OTP_FGPassScreenPreview() {
    CafeTrioTheme {
        OTP_FGPassScreen()
    }
}
