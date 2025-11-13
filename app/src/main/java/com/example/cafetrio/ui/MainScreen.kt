package com.example.cafetrio.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
import com.example.cafetrio.ui.components.BottomNavBar
import com.example.cafetrio.ui.components.NavigationItem
import com.example.cafetrio.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.animation.core.tween
import androidx.compose.ui.platform.LocalContext

// Mock data models
data class MockProduct(
    val id: String,
    val name: String,
    val price: String,
    val imageRes: Int,
    val isNew: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onNotificationClick: () -> Unit = {},
    onMenuClick: () -> Unit = {},
    onNavigate: (String) -> Unit = {},
    onNavigateToNoti: () -> Unit = {}
) {

    val userName = "NGUYEN DINH TUAN"
    val userCode = "CFT02809"
    val beanCount = 88

    // Comment API call và dùng mock data
    // val mustTryProducts = remember { mutableStateListOf<ProductResponse>() }
    // val context = LocalContext.current
    // LaunchedEffect(true) {
    //     ApiClient.apiService.getMustTryProducts().enqueue(...)
    // }
    
    // Mock data for products
    val mustTryProducts = remember {
        listOf(
            MockProduct("1", "Smoothie Xoài Nhiệt Đới", "65.000đ", R.drawable.xoai_granola, true),
            MockProduct("2", "Trà Sữa Oolong Tứ Quý", "55.000đ", R.drawable.tra_sua_oolong_tu_quy_suong_sao, true),
            MockProduct("3", "Cà Phê Sữa Đá", "39.000đ", R.drawable.cfs_da, false),
            MockProduct("4", "Chocolate Nóng", "55.000đ", R.drawable.chocolate_nong, true)
        )
    }

    val adImages = listOf(
        R.drawable.ad_1,
        R.drawable.ad_2,
        R.drawable.ad_3,
        R.drawable.ad_4,
        R.drawable.ad_5,
        R.drawable.ad_6,
        R.drawable.ad_7
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Brew Co",
                        color = HighlandWhite,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily(Font(R.font.agbalumo_regular))
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
                                .clickable { onNavigate("rewards") },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_coupon),
                                contentDescription = "Vouchers",
                                modifier = Modifier
                                    .size(24.dp)
                                    .padding(start = 4.dp)
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
                currentItem = NavigationItem.HOME,
                onNavigate = onNavigate
            )
        }
    ) { paddingValues ->
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(HighlandWhite) // Nền trắng
                .verticalScroll(scrollState)
        ) {
            // User Profile Card - Compact design
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    HighlandRed,
                                    HighlandDarkRed
                                )
                            )
                        )
                        .padding(20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Left - User info
                        Column {
                            Text(
                                text = userName,
                                color = HighlandWhite,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                            
                            Spacer(modifier = Modifier.height(4.dp))
                            
                            Text(
                                text = "Mã TV: $userCode",
                                color = HighlandWhite.copy(alpha = 0.9f),
                                fontSize = 13.sp
                            )
                        }
                        
                        // Right - Bean balance
                        Column(
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                text = "$beanCount",
                                color = HighlandWhite,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "BEAN",
                                color = HighlandWhite.copy(alpha = 0.9f),
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
            
            // Quick Action Buttons - Redesigned grid
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "Dịch vụ",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = HighlandText,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                
                // First row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    QuickActionButton(
                        icon = R.drawable.shipping,
                        text = "Giao hàng",
                        onClick = { onNavigate("order") },
                        modifier = Modifier.weight(1f)
                    )
                    
                    QuickActionButton(
                        icon = R.drawable.take_away,
                        text = "Mang đi",
                        onClick = { onNavigate("order") },
                        modifier = Modifier.weight(1f)
                    )
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                
                // Second row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    QuickActionButton(
                        icon = R.drawable.invoice,
                        text = "Đơn hàng",
                        onClick = { onNavigate("orders") },
                        modifier = Modifier.weight(1f)
                    )
                    
                    QuickActionButton(
                        icon = R.drawable.coffee_beans,
                        text = "Đổi Bean",
                        onClick = { onNavigate("rewards") },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Advertisement Carousel
            val pagerState = rememberPagerState(pageCount = { adImages.size })
            val coroutineScope = rememberCoroutineScope()
            
            // Auto slide
            LaunchedEffect(Unit) {
                while(true) {
                    delay(3000)
                    val nextPage = (pagerState.currentPage + 1) % adImages.size
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(
                            page = nextPage,
                            animationSpec = tween(durationMillis = 800)
                        )
                    }
                }
            }
            
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Ưu đãi đặc biệt",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = HighlandText,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
                
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                ) {
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier.fillMaxSize()
                    ) { page ->
                        Image(
                            painter = painterResource(id = adImages[page]),
                            contentDescription = "Advertisement",
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp)
                                .clip(RoundedCornerShape(16.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                    
                    // Indicators
                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 12.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        repeat(adImages.size) { index ->
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 3.dp)
                                    .size(if (pagerState.currentPage == index) 8.dp else 6.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (pagerState.currentPage == index) HighlandWhite 
                                        else HighlandWhite.copy(alpha = 0.5f)
                                    )
                            )
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Món Mới Phải Thử Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Món Mới Phải Thử",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = HighlandText
                    )
                    
                    TextButton(onClick = { onNavigate("order") }) {
                        Text(
                            text = "Xem tất cả",
                            color = HighlandRed,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                
                // Grid 2 columns
                val productChunks = mustTryProducts.chunked(2)
                productChunks.forEach { rowItems ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        rowItems.forEach { product ->
                            ProductCard(
                                product = product,
                                onClick = { onNavigate("product/${product.id}") },
                                modifier = Modifier.weight(1f)
                            )
                        }
                        
                        if (rowItems.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun QuickActionButton(
    icon: Int,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(100.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp),
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
                painter = painterResource(id = icon),
                contentDescription = text,
                modifier = Modifier.size(40.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = text,
                color = HighlandText,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ProductCard(
    product: MockProduct,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .background(HighlandRed.copy(alpha = 0.05f))
            ) {
                Image(
                    painter = painterResource(id = product.imageRes),
                    contentDescription = product.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                )

                if (product.isNew) {
                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.TopStart)
                            .background(HighlandRed, RoundedCornerShape(6.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "NEW",
                            color = HighlandWhite,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Info
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Text(
                    text = product.name,
                    color = HighlandText,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    maxLines = 2,
                    minLines = 2
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = product.price,
                        color = HighlandRed,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(HighlandRed, CircleShape)
                            .clickable { /* Add to cart */ },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.button_plus),
                            contentDescription = "Add",
                            // Remove tint to show original icon color
                            tint = Color.Unspecified,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    CafeTrioTheme {
        MainScreen()
    }
}
