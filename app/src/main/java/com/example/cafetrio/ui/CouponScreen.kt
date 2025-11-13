package com.example.cafetrio.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cafetrio.R
import com.example.cafetrio.ui.theme.*
import com.example.cafetrio.ui.components.BottomNavBar
import com.example.cafetrio.ui.components.NavigationItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CouponScreen(
    onBackClick: () -> Unit = {},
    onNavigationItemClick: (String) -> Unit = {}
) {
    val backgroundColor = HighlandWhite
    val userName = "NGUYEN DINH TUAN"
    val beanCount = 88

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Ưu đãi",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = HighlandWhite
                    )
                },
                actions = {
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
                                ),
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
                currentItem = NavigationItem.REWARDS,
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
            // User card with Brew Co branding
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                HighlandRed,
                                HighlandDarkRed
                            )
                        )
                    )
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // User info section
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Chào bạn,",
                                fontSize = 16.sp,
                                color = HighlandWhite
                            )
                            Text(
                                text = userName,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = HighlandWhite
                            )
                        }
                        
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "$beanCount BEAN",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = HighlandWhite
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Membership card
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(HighlandWhite)
                            .padding(16.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Brew Co",
                                color = HighlandRed,
                                fontSize = 32.sp,
                                fontFamily = FontFamily(Font(R.font.agbalumo_regular)),
                                textAlign = TextAlign.Center
                            )
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            Text(
                                text = "BRONZE CLASS",
                                color = HighlandDarkRed,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            Text(
                                text = "MEMBERSHIP CARD",
                                color = HighlandText,
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Bean info and voucher button
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Còn 100 BEAN nữa bạn sẽ thăng hạng.\nĐổi quà không ảnh hưởng tới việc thăng hạng\ncủa bạn",
                            color = HighlandWhite,
                            fontSize = 12.sp,
                            modifier = Modifier.weight(1f)
                        )
                        
                        // Voucher button
                        Button(
                            onClick = { /* TODO: Handle voucher click */ },
                            modifier = Modifier.wrapContentSize(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = HighlandWhite
                            ),
                            shape = RoundedCornerShape(16.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_voucher),
                                    contentDescription = "Vouchers",
                                    modifier = Modifier.size(20.dp)
                                )
                                
                                Spacer(modifier = Modifier.width(4.dp))

                                Text(
                                    text = "Voucher của tôi",
                                    color = HighlandRed,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
            
            // Function buttons in a grid layout
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                // First row with two buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    FunctionButton(
                        icon = R.drawable.ic_crown,
                        title = "Hạng thành viên",
                        onClick = { /* TODO */ },
                        iconColor = HighlandRed,
                        modifier = Modifier.weight(1f)
                    )
                    
                    FunctionButton(
                        icon = R.drawable.ic_gift,
                        title = "Đổi BEAN",
                        onClick = { /* TODO */ },
                        iconColor = HighlandRed,
                        modifier = Modifier.weight(1f)
                    )
                }
                
                Spacer(modifier = Modifier.height(12.dp))

                // Second row with two buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    FunctionButton(
                        icon = R.drawable.ic_coffeeseed,
                        title = "Lịch sử BEAN",
                        onClick = { /* TODO */ },
                        iconColor = HighlandRed,
                        modifier = Modifier.weight(1f)
                    )
                    
                    FunctionButton(
                        icon = R.drawable.ic_person,
                        title = "Quyền lợi của bạn",
                        onClick = { /* TODO */ },
                        iconColor = HighlandRed,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            
            // Voucher section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Voucher của bạn",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = HighlandText
                    )
                    
                    Text(
                        text = "Xem tất cả",
                        fontSize = 14.sp,
                        color = HighlandRed,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.clickable { /* TODO */ }
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Vouchers
                VoucherCard(
                    discountText = "30%",
                    titleText = "Giảm 30% toàn bộ Menu Nước Size Lớn",
                    expiryDate = "Hết hạn 23/04/2024",
                    color = HighlandRed,
                    imageRes = R.drawable.vc_1
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                VoucherCard(
                    discountText = "40%",
                    titleText = "Giảm 40% + Freeship Đơn Từ 10 Ly Trở Lên",
                    expiryDate = "Hết hạn 30/04/2024",
                    color = HighlandRed,
                    freeshipTag = true,
                    imageRes = R.drawable.vc_2
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                VoucherCard(
                    discountText = "30%",
                    titleText = "Giảm 30% + Freeship Đơn Từ 3 Ly",
                    expiryDate = "Hết hạn 30/04/2024",
                    color = HighlandRed,
                    freeshipTag = true,
                    imageRes = R.drawable.vc_3
                )
            }
        }
    }
}

@Composable
fun FunctionButton(
    icon: Int,
    title: String,
    onClick: () -> Unit,
    iconColor: Color = HighlandRed,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .height(80.dp)
                .fillMaxWidth()
                .background(
                    color = HighlandWhite,
                    shape = RoundedCornerShape(12.dp)
                )
                .border(
                    width = 1.dp,
                    color = HighlandRed.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(12.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = title,
                    modifier = Modifier.size(32.dp),
                    colorFilter = ColorFilter.tint(iconColor)
                )
                
                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = title,
                    fontSize = 13.sp,
                    color = HighlandText,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun VoucherCard(
    discountText: String,
    titleText: String,
    expiryDate: String,
    color: Color,
    freeshipTag: Boolean = false,
    imageRes: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = HighlandWhite
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left side with image
            Box(
                modifier = Modifier
                    .width(80.dp)
                    .height(80.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(color.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = "Voucher image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Right side with text
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Discount tag
                    Box(
                        modifier = Modifier
                            .background(
                                color = color,
                                shape = RoundedCornerShape(4.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = discountText,
                            color = HighlandWhite,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    if (freeshipTag) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .background(
                                    color = Color(0xFF4CAF50),
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "FREESHIP",
                                color = HighlandWhite,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = titleText,
                    fontSize = 14.sp,
                    color = HighlandText,
                    fontWeight = FontWeight.Medium,
                    maxLines = 2
                )
                
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = expiryDate,
                    fontSize = 12.sp,
                    color = HighlandText.copy(alpha = 0.6f)
                )
            }

            // Arrow icon
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "View details",
                tint = HighlandText.copy(alpha = 0.5f),
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CouponScreenPreview() {
    CafeTrioTheme {
        CouponScreen()
    }
}
