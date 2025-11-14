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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.Dp
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
    val userName = "Nguyen Phan"
    val beanCount = 88
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Voucher của tôi", "Đổi Bean", "Lịch sử")

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(HighlandRed)
            ) {
                // Header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Ưu đãi & Điểm thưởng",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = HighlandWhite
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = R.drawable.coffee_beans),
                                contentDescription = null,
                                modifier = Modifier.size(18.dp),
                                colorFilter = ColorFilter.tint(HighlandWhite)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "$beanCount Bean",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = HighlandWhite
                            )
                        }
                    }
                }
                
                // Tabs
                TabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = HighlandRed,
                    contentColor = HighlandWhite,
                    indicator = { tabPositions ->
                        if (selectedTab < tabPositions.size) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentSize(Alignment.BottomStart)
                                    .offset(x = tabPositions[selectedTab].left)
                                    .width(tabPositions[selectedTab].width)
                                    .height(3.dp)
                                    .padding(horizontal = 20.dp)
                                    .background(HighlandWhite, RoundedCornerShape(topStart = 3.dp, topEnd = 3.dp))
                            )
                        }
                    }
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            text = {
                                Text(
                                    text = title,
                                    fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal,
                                    fontSize = 14.sp
                                )
                            }
                        )
                    }
                }
            }
        },
        bottomBar = {
            BottomNavBar(
                currentItem = NavigationItem.REWARDS,
                onNavigate = onNavigationItemClick
            )
        },
        containerColor = Color(0xFFF5F5F5)
    ) { paddingValues ->
        when (selectedTab) {
            0 -> VoucherTab(paddingValues)
            1 -> ExchangeBeanTab(beanCount, paddingValues)
            2 -> HistoryTab(paddingValues)
        }
    }
}

@Composable
fun VoucherTab(paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "Voucher khả dụng",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = HighlandText,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        
        ModernVoucherCard(
            discountText = "30%",
            titleText = "Giảm 30% toàn bộ Menu Nước Size Lớn",
            expiryDate = "HSD: 23/04/2024",
            imageRes = R.drawable.vc_1
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        ModernVoucherCard(
            discountText = "40%",
            titleText = "Giảm 40% + Freeship Đơn Từ 10 Ly",
            expiryDate = "HSD: 30/04/2024",
            imageRes = R.drawable.vc_2,
            hasFreeship = true
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        ModernVoucherCard(
            discountText = "30%",
            titleText = "Giảm 30% + Freeship Đơn Từ 3 Ly",
            expiryDate = "HSD: 30/04/2024",
            imageRes = R.drawable.vc_3,
            hasFreeship = true
        )
    }
}

@Composable
fun ExchangeBeanTab(beanCount: Int, paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Bean balance card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(HighlandRed, HighlandDarkRed)
                        )
                    )
                    .padding(24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Bean hiện có",
                        fontSize = 14.sp,
                        color = HighlandWhite.copy(alpha = 0.9f)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "$beanCount Bean",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = HighlandWhite
                    )
                }
                
                Image(
                    painter = painterResource(id = R.drawable.coffee_beans),
                    contentDescription = null,
                    modifier = Modifier.size(60.dp),
                    colorFilter = ColorFilter.tint(HighlandWhite.copy(alpha = 0.3f))
                )
            }
        }
        
        Spacer(modifier = Modifier.height(20.dp))
        
        Text(
            text = "Đổi Bean lấy ưu đãi",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = HighlandText,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        
        // Exchange items
        ExchangeItem(
            title = "Voucher Giảm 20%",
            beanCost = 30,
            imageRes = R.drawable.ic_voucher
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        ExchangeItem(
            title = "Free 1 Cà Phê",
            beanCost = 50,
            imageRes = R.drawable.coffee_beans
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        ExchangeItem(
            title = "Freeship Toàn Quốc",
            beanCost = 25,
            imageRes = R.drawable.shipping
        )
    }
}

@Composable
fun HistoryTab(paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "Lịch sử Bean",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = HighlandText,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        
        HistoryItem(
            title = "Tích Bean từ đơn hàng",
            amount = "+15 Bean",
            date = "15/11/2024",
            isPositive = true
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        HistoryItem(
            title = "Đổi voucher giảm 20%",
            amount = "-30 Bean",
            date = "14/11/2024",
            isPositive = false
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        HistoryItem(
            title = "Tích Bean từ đơn hàng",
            amount = "+25 Bean",
            date = "12/11/2024",
            isPositive = true
        )
    }
}

// New modern components
@Composable
fun ModernVoucherCard(
    discountText: String,
    titleText: String,
    expiryDate: String,
    imageRes: Int,
    hasFreeship: Boolean = false
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Image section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            ) {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                
                // Overlay gradient
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.4f))
                            )
                        )
                )
                
                // Discount badge
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(12.dp)
                        .background(HighlandRed, RoundedCornerShape(20.dp))
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = discountText,
                        color = HighlandWhite,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            
            // Content section
            Column(modifier = Modifier.padding(16.dp)) {
                if (hasFreeship) {
                    Box(
                        modifier = Modifier
                            .background(Color(0xFF4CAF50), RoundedCornerShape(4.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "FREESHIP",
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
                
                Text(
                    text = titleText,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = HighlandText,
                    maxLines = 2
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = expiryDate,
                        fontSize = 12.sp,
                        color = HighlandText.copy(alpha = 0.6f)
                    )
                    
                    Button(
                        onClick = { /* Use voucher */ },
                        colors = ButtonDefaults.buttonColors(containerColor = HighlandRed),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text("Sử dụng", fontSize = 13.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun ExchangeItem(
    title: String,
    beanCost: Int,
    imageRes: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(HighlandRed.copy(alpha = 0.1f), RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = imageRes),
                        contentDescription = null,
                        modifier = Modifier.size(28.dp)
                    )
                }
                
                Spacer(modifier = Modifier.width(16.dp))
                
                Column {
                    Text(
                        text = title,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        color = HighlandText
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.coffee_beans),
                            contentDescription = null,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "$beanCost Bean",
                            fontSize = 13.sp,
                            color = HighlandRed,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            
            Button(
                onClick = { /* Exchange */ },
                colors = ButtonDefaults.buttonColors(containerColor = HighlandRed),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text("Đổi", fontSize = 13.sp)
            }
        }
    }
}

@Composable
fun HistoryItem(
    title: String,
    amount: String,
    date: String,
    isPositive: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = HighlandText
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = date,
                    fontSize = 12.sp,
                    color = HighlandText.copy(alpha = 0.6f)
                )
            }
            
            Text(
                text = amount,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = if (isPositive) Color(0xFF4CAF50) else HighlandRed
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
