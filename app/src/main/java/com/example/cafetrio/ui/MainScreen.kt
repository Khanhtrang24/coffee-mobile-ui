package com.example.cafetrio.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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
    val userName = "Nguyen Phan"
    val beanCount = 88
    var selectedCategory by remember { mutableStateOf("Tất cả") }
    
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
    
    val categories = listOf("Tất cả", "Cà phê", "Trà sữa", "Smoothie", "Khác")

    Scaffold(
        topBar = {
            // Simplified header
            Surface(
                modifier = Modifier.fillMaxWidth(),
                        color = HighlandWhite,
                shadowElevation = 4.dp
                    ) {
                        Row(
                            modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Chào, $userName",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = HighlandText
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = R.drawable.coffee_beans),
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "$beanCount Bean",
                                fontSize = 13.sp,
                                color = HighlandRed,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                    
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Voucher icon
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(HighlandRed.copy(alpha = 0.1f), CircleShape)
                                .clickable { onNavigate("rewards") },
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_coupon),
                                contentDescription = "Vouchers",
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        
                        // Notification icon
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(HighlandRed.copy(alpha = 0.1f), CircleShape)
                                .clickable { onNavigateToNoti() },
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_noti),
                                contentDescription = "Notifications",
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                }
            }
        },
        bottomBar = {
            BottomNavBar(
                currentItem = NavigationItem.HOME,
                onNavigate = onNavigate
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onNavigate("orders") },
                containerColor = HighlandRed,
                contentColor = HighlandWhite
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.invoice),
                    contentDescription = "Cart",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    ) { paddingValues ->
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF5F5F5))
                .verticalScroll(scrollState)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            
            // Promotional Banner Carousel - Larger and more prominent
            val pagerState = rememberPagerState(pageCount = { adImages.size })
            val coroutineScope = rememberCoroutineScope()
            
            LaunchedEffect(Unit) {
                while(true) {
                    delay(3500)
                    val nextPage = (pagerState.currentPage + 1) % adImages.size
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(
                            page = nextPage,
                            animationSpec = tween(durationMillis = 600)
                        )
                    }
                }
            }
                
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                    .height(200.dp)
                    .padding(horizontal = 16.dp)
                ) {
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier.fillMaxSize()
                    ) { page ->
                    Card(
                        modifier = Modifier.fillMaxSize(),
                        shape = RoundedCornerShape(20.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Image(
                            painter = painterResource(id = adImages[page]),
                            contentDescription = "Promotion",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                    }
                    
                // Modern page indicators
                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp)
                        .background(Color.Black.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        repeat(adImages.size) { index ->
                            Box(
                                modifier = Modifier
                                .padding(horizontal = 2.dp)
                                .width(if (pagerState.currentPage == index) 20.dp else 6.dp)
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp))
                                    .background(
                                    if (pagerState.currentPage == index) HighlandWhite else HighlandWhite.copy(alpha = 0.5f)
                                )
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(20.dp))
            
            // Quick Service Icons - Horizontal scroll
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                item {
                    ServiceIconCard(
                        icon = R.drawable.shipping,
                        label = "Giao hàng",
                        onClick = { onNavigate("order") },
                    )
                }
                item {
                    ServiceIconCard(
                        icon = R.drawable.take_away,
                        label = "Mang đi",
                        onClick = { onNavigate("order") }
                    )
                }
                item {
                    ServiceIconCard(
                        icon = R.drawable.coffee_beans,
                        label = "Đổi Bean",
                        onClick = { onNavigate("rewards") }
                    )
                }
                item {
                    ServiceIconCard(
                        icon = R.drawable.ic_gift,
                        label = "Ưu đãi",
                        onClick = { onNavigate("rewards") }
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(20.dp))
            
            // Category Tabs
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories) { category ->
                    CategoryChip(
                        text = category,
                        selected = category == selectedCategory,
                        onClick = { selectedCategory = category }
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Featured Products Section
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
                        text = "Món Nổi Bật",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = HighlandText
                    )

                    TextButton(
                        onClick = { onNavigate("order") },
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .background(HighlandRed, shape = RoundedCornerShape(20.dp))
                            //.padding(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "Xem tất cả",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                
                // Vertical list of products
                mustTryProducts.forEach { product ->
                    ModernProductCard(
                        product = product,
                        onClick = { onNavigate("product/${product.id}") }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
            
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

// New modern components
@Composable
fun ServiceIconCard(
    icon: Int,
    label: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(80.dp)
            .clickable(onClick = onClick)
    ) {

        Box(
            modifier = Modifier
                .size(64.dp)
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(16.dp),
                    clip = false
                )
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = label,
                modifier = Modifier.size(32.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = label,
            fontSize = 12.sp,
            color = HighlandText,
            textAlign = TextAlign.Center,
            maxLines = 1
        )
    }
}

@Composable
fun CategoryChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier.clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        color = if (selected) HighlandRed else Color.White,
        shadowElevation = if (selected) 4.dp else 2.dp
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
            color = if (selected) HighlandWhite else HighlandText,
            fontSize = 14.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
fun ModernProductCard(
    product: MockProduct,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Product Image
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(HighlandRed.copy(alpha = 0.05f))
            ) {
                Image(
                    painter = painterResource(id = product.imageRes),
                    contentDescription = product.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                
                if (product.isNew) {
                    Box(
                        modifier = Modifier
                            .padding(6.dp)
                            .background(HighlandRed, RoundedCornerShape(6.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "NEW",
                            color = HighlandWhite,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Product Info
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = product.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = HighlandText,
                    maxLines = 2
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = product.price,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = HighlandRed
                )
            }
            
            // Add button
            Box(
                modifier = Modifier
                    .size(44.dp)
                    //.background(HighlandRed, CircleShape)
                    .clickable { /* Add to cart */ },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.button_plus),
                    contentDescription = "Add",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(24.dp)
                )
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
