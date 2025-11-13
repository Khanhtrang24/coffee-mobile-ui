package com.example.cafetrio.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cafetrio.R
import com.example.cafetrio.data.api.ApiClient
import android.widget.Toast
import android.content.Context
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.cafetrio.ui.theme.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import com.example.cafetrio.ui.components.BottomNavBar
import com.example.cafetrio.ui.components.NavigationItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DifferScreen(
    onBackClick: () -> Unit = {},
    onNavigationItemClick: (String) -> Unit = {},
    onLogoutClick: () -> Unit = {},
    onHistoryClick: () -> Unit = {},
    onNavigateToNoti: () -> Unit = {}
) {
    val backgroundColor = HighlandWhite
    val context = LocalContext.current
    val sharedPreferences = remember { context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE) }
    
    // Hàm xử lý đăng xuất - COMMENT API và auto pass
    val handleLogout = {
        // val token = sharedPreferences.getString("auth_token", null)
        //
        // if (token != null) {
        //     ApiClient.apiService.logout("Bearer $token").enqueue(object : Callback<Void> {
        //         override fun onResponse(call: Call<Void>, response: Response<Void>) {
        //             if (response.isSuccessful) {
        //                 sharedPreferences.edit().clear().apply()
        //                 Toast.makeText(context, "Đăng xuất thành công", Toast.LENGTH_SHORT).show()
        //                 onLogoutClick()
        //             } else {
        //                 Toast.makeText(context, "Đăng xuất thất bại: ${response.code()}", Toast.LENGTH_SHORT).show()
        //             }
        //         }
        //
        //         override fun onFailure(call: Call<Void>, t: Throwable) {
        //             Toast.makeText(context, "Lỗi kết nối: ${t.message}", Toast.LENGTH_SHORT).show()
        //         }
        //     })
        // } else {
        //     Toast.makeText(context, "Bạn chưa đăng nhập", Toast.LENGTH_SHORT).show()
        //     onLogoutClick()
        // }

        // Auto pass logout
        sharedPreferences.edit().clear().apply()
        Toast.makeText(context, "Đăng xuất thành công", Toast.LENGTH_SHORT).show()
        onLogoutClick()
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Khác",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = HighlandWhite
                    )
                },
                actions = {
                    // Voucher Button
                    Box(
                        modifier = Modifier.padding(end = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            modifier = Modifier
                                .width(70.dp)
                                .height(40.dp)
                                .background(
                                    color = HighlandWhite,
                                    shape = RoundedCornerShape(size = 25.dp)
                                )
                                .clickable { onNavigationItemClick("rewards") },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_coupon),
                                contentDescription = "Vouchers",
                                modifier = Modifier
                                    .size(32.dp)
                                    .padding(start = 8.dp)
                            )
                            
                            Text(
                                text = "11", 
                                color = HighlandRed,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(start = 4.dp, end = 8.dp)
                            )
                        }
                    }
                    
                    // Notification button
                    Box(
                        modifier = Modifier.padding(end = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(
                                    color = HighlandWhite,
                                    shape = RoundedCornerShape(size = 20.dp)
                                )
                                .clickable { onNavigateToNoti() },
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_noti),
                                contentDescription = "Notifications",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = HighlandRed
                )
            )
        },
        bottomBar = {
            BottomNavBar(
                currentItem = NavigationItem.MORE,
                onNavigate = onNavigationItemClick
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(backgroundColor)
                .verticalScroll(rememberScrollState())
        ) {
            // Profile Card Section
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable { onNavigationItemClick("user_info") },
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Avatar
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .background(
                                color = HighlandRed.copy(alpha = 0.1f),
                                shape = androidx.compose.foundation.shape.CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.coffee_beans),
                            contentDescription = "Avatar",
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Người dùng",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = HighlandText
                        )
                        Text(
                            text = "Hạng: Đồng",
                            fontSize = 14.sp,
                            color = HighlandText.copy(alpha = 0.7f)
                        )
                    }

                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = null,
                        tint = HighlandRed,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Tiện ích Section
            Text(
                text = "Tiện ích",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = HighlandText,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
            )
            
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                UtilityButton(
                    iconResId = R.drawable.ic_lichsudonhang,
                    title = "Lịch sử\nđơn hàng",
                    iconTint = HighlandRed,
                    modifier = Modifier.weight(1f),
                    onClick = onHistoryClick
                )
                
                UtilityButton(
                    iconResId = R.drawable.ic_dieukhoan,
                    title = "Điều khoản",
                    iconTint = HighlandRed,
                    modifier = Modifier.weight(1f),
                    onClick = { /* TODO */ }
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))

            // Tài khoản Section
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Tài khoản",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = HighlandText,
                        modifier = Modifier.padding(16.dp)
                    )

                    SupportItem(
                        iconResId = R.drawable.ic_ttcanhan,
                        title = "Thông tin cá nhân",
                        onClick = { onNavigationItemClick("user_info") }
                    )

                    Divider(color = Color.LightGray.copy(alpha = 0.3f))

                    SupportItem(
                        iconResId = R.drawable.ic_diachi,
                        title = "Địa chỉ đã lưu",
                        onClick = { /* TODO */ }
                    )

                    Divider(color = Color.LightGray.copy(alpha = 0.3f))

                    SupportItem(
                        iconResId = R.drawable.ic_caidat,
                        title = "Cài đặt",
                        onClick = { /* TODO */ }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Hỗ trợ Section
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Hỗ trợ",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = HighlandText,
                        modifier = Modifier.padding(16.dp)
                    )

                    SupportItem(
                        iconResId = R.drawable.ic_danhgiadonhang,
                        title = "Đánh giá đơn hàng",
                        onClick = { /* TODO */ }
                    )

                    Divider(color = Color.LightGray.copy(alpha = 0.3f))

                    SupportItem(
                        iconResId = R.drawable.ic_lienhe,
                        title = "Liên hệ và góp ý",
                        onClick = { /* TODO */ }
                    )

                    Divider(color = Color.LightGray.copy(alpha = 0.3f))

                    SupportItem(
                        iconResId = R.drawable.ic_hoadon,
                        title = "Xuất hóa đơn GTGT",
                        onClick = { /* TODO */ }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Logout Button
            OutlinedButton(
                onClick = { handleLogout() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(54.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = HighlandRed
                ),
                border = androidx.compose.foundation.BorderStroke(1.dp, HighlandRed)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_logout),
                    contentDescription = null,
                    tint = HighlandRed,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Đăng xuất",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun UtilityButton(
    iconResId: Int,
    title: String,
    iconTint: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(110.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = iconResId),
                contentDescription = title,
                modifier = Modifier.size(40.dp),
                colorFilter = ColorFilter.tint(iconTint)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = title,
                fontSize = 13.sp,
                color = HighlandText,
                fontWeight = FontWeight.Medium,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}

@Composable
fun SupportItem(
    iconResId: Int,
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = iconResId),
            contentDescription = title,
            modifier = Modifier.size(24.dp),
            colorFilter = ColorFilter.tint(HighlandRed)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = title,
            fontSize = 15.sp,
            color = HighlandText,
            modifier = Modifier.weight(1f)
        )

        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DifferScreenPreview() {
    CafeTrioTheme {
        DifferScreen()
    }
}
