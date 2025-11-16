package com.example.cafetrio.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cafetrio.R
import com.example.cafetrio.ui.theme.*
import com.example.cafetrio.ui.components.BottomNavBar
import com.example.cafetrio.ui.components.NavigationItem
import com.example.cafetrio.ui.viewmodel.CouponViewModel
import com.example.cafetrio.data.models.*

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun CouponScreen(
    onBackClick: () -> Unit = {},
    onNavigationItemClick: (String) -> Unit = {},
    viewModel: CouponViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Voucher của tôi", "Đổi Bean", "Lịch sử")
    val snackbarHostState = remember { SnackbarHostState() }
    
    // Show error/success messages
    LaunchedEffect(uiState.errorMessage, uiState.successMessage) {
        uiState.errorMessage?.let {
            snackbarHostState.showSnackbar(
                message = it,
                duration = SnackbarDuration.Short
            )
            viewModel.clearErrorMessage()
        }
        uiState.successMessage?.let {
            snackbarHostState.showSnackbar(
                message = it,
                duration = SnackbarDuration.Short
            )
            viewModel.clearSuccessMessage()
        }
    }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(HighlandRed)
            ) {
                // Header with animated bean count
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
                            color = HighlandWhite,
                            modifier = Modifier.semantics {
                                contentDescription = "Màn hình ưu đãi và điểm thưởng"
                            }
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        AnimatedBeanCounter(
                            beanCount = uiState.beanBalance.current,
                            modifier = Modifier
                        )
                    }
                }
                
                // Tabs with animation
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
                                    .background(
                                        HighlandWhite,
                                        RoundedCornerShape(topStart = 3.dp, topEnd = 3.dp)
                                    )
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
                            },
                            modifier = Modifier.semantics {
                                contentDescription = "Tab $title"
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
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = Color(0xFFF5F5F5)
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            // Loading indicator
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(50.dp),
                    color = HighlandRed
                )
            } else {
                // Content with animated transitions
                AnimatedContent(
                    targetState = selectedTab,
                    transitionSpec = {
                        fadeIn(animationSpec = tween(300)) +
                                slideInHorizontally(initialOffsetX = { it / 2 }) with
                                fadeOut(animationSpec = tween(300)) +
                                slideOutHorizontally(targetOffsetX = { -it / 2 })
                    },
                    label = "tab_transition"
                ) { tab ->
                    when (tab) {
                        0 -> VoucherTab(
                            vouchers = uiState.vouchers,
                            onUseVoucher = viewModel::useVoucher,
                            isProcessing = uiState.isProcessing,
                            paddingValues = paddingValues
                        )
                        1 -> ExchangeBeanTab(
                            beanBalance = uiState.beanBalance,
                            exchangeItems = uiState.exchangeItems,
                            onExchange = viewModel::exchangeBean,
                            isProcessing = uiState.isProcessing,
                            paddingValues = paddingValues
                        )
                        2 -> HistoryTab(
                            historyItems = uiState.historyItems,
                            paddingValues = paddingValues
                        )
                    }
                }
            }
        }
    }
}

// Animated Bean Counter Component
@Composable
fun AnimatedBeanCounter(
    beanCount: Int,
    modifier: Modifier = Modifier
) {
    val animatedCount = remember { androidx.compose.animation.core.Animatable(beanCount.toFloat()) }
    
    LaunchedEffect(beanCount) {
        animatedCount.animateTo(
            targetValue = beanCount.toFloat(),
            animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing)
        )
    }
    
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = R.drawable.coffee_beans),
            contentDescription = "Bean icon",
            modifier = Modifier
                .size(18.dp)
                .graphicsLayer {
                    rotationZ = (animatedCount.value % 360)
                },
            colorFilter = ColorFilter.tint(HighlandWhite)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = "${animatedCount.value.toInt()} Bean",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = HighlandWhite,
            modifier = Modifier.semantics {
                contentDescription = "Số Bean hiện có: ${animatedCount.value.toInt()}"
            }
        )
    }
}

@Composable
fun VoucherTab(
    vouchers: List<VoucherItem>,
    onUseVoucher: (String) -> Unit,
    isProcessing: Boolean,
    paddingValues: PaddingValues
) {
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
            modifier = Modifier
                .padding(bottom = 12.dp)
                .semantics {
                    contentDescription = "Danh sách voucher khả dụng"
                }
        )
        
        if (vouchers.isEmpty()) {
            EmptyState(
                message = "Bạn chưa có voucher nào",
                icon = Icons.Default.Info
            )
        } else {
            vouchers.forEachIndexed { index, voucher ->
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn(animationSpec = tween(300, delayMillis = index * 50)) +
                            slideInVertically(initialOffsetY = { it / 2 })
                ) {
        ModernVoucherCard(
                        voucher = voucher,
                        onUseClick = { onUseVoucher(voucher.id) },
                        isProcessing = isProcessing
                    )
                }
                
                if (index < vouchers.size - 1) {
        Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
fun ExchangeBeanTab(
    beanBalance: BeanBalance,
    exchangeItems: List<ExchangeItem>,
    onExchange: (String, Int) -> Unit,
    isProcessing: Boolean,
    paddingValues: PaddingValues
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Bean balance card with improved design
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .semantics {
                    contentDescription = "Thẻ hiển thị số Bean hiện có"
                },
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
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
                    
                    val animatedBean = remember { androidx.compose.animation.core.Animatable(beanBalance.current.toFloat()) }
                    LaunchedEffect(beanBalance.current) {
                        animatedBean.animateTo(
                            targetValue = beanBalance.current.toFloat(),
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessLow
                            )
                        )
                    }
                    
                    Text(
                        text = "${animatedBean.value.toInt()} Bean",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = HighlandWhite
                    )
                    
                    if (beanBalance.expiringSoon > 0) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "${beanBalance.expiringSoon} Bean sắp hết hạn",
                            fontSize = 11.sp,
                            color = Color(0xFFFFEB3B),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
                
                Image(
                    painter = painterResource(id = R.drawable.coffee_beans),
                    contentDescription = "Bean icon",
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
            modifier = Modifier
                .padding(bottom = 12.dp)
                .semantics {
                    contentDescription = "Danh sách quà có thể đổi"
                }
        )
        
        if (exchangeItems.isEmpty()) {
            EmptyState(
                message = "Chưa có quà để đổi",
                icon = Icons.Default.Info
            )
        } else {
            exchangeItems.forEachIndexed { index, item ->
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn(animationSpec = tween(300, delayMillis = index * 50)) +
                            slideInVertically(initialOffsetY = { it / 2 })
                ) {
                    ExchangeItemCard(
                        item = item,
                        currentBeans = beanBalance.current,
                        onExchange = { onExchange(item.id, item.beanCost) },
                        isProcessing = isProcessing
                    )
                }
                
                if (index < exchangeItems.size - 1) {
        Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
fun HistoryTab(
    historyItems: List<HistoryItem>,
    paddingValues: PaddingValues
) {
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
            modifier = Modifier
                .padding(bottom = 12.dp)
                .semantics {
                    contentDescription = "Lịch sử giao dịch Bean"
                }
        )
        
        if (historyItems.isEmpty()) {
            EmptyState(
                message = "Chưa có lịch sử giao dịch",
                icon = Icons.Default.Info
            )
        } else {
            historyItems.forEachIndexed { index, item ->
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn(animationSpec = tween(300, delayMillis = index * 50)) +
                            slideInVertically(initialOffsetY = { it / 2 })
                ) {
                    HistoryItemCard(item = item)
                }
                
                if (index < historyItems.size - 1) {
        Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

// Empty State Component
@Composable
fun EmptyState(
    message: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = HighlandText.copy(alpha = 0.3f)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = message,
            fontSize = 14.sp,
            color = HighlandText.copy(alpha = 0.6f),
            textAlign = TextAlign.Center
        )
    }
}

// Improved Modern Voucher Card with animations
@Composable
fun ModernVoucherCard(
    voucher: VoucherItem,
    onUseClick: () -> Unit,
    isProcessing: Boolean
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.97f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "card_scale"
    )
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .semantics {
                contentDescription = "Voucher ${voucher.titleText}, giảm ${voucher.discountText}"
            },
        colors = CardDefaults.cardColors(
            containerColor = if (voucher.isUsed) Color.Gray.copy(alpha = 0.3f) else Color.White
        ),
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
                    painter = painterResource(id = voucher.imageRes),
                    contentDescription = "Hình ảnh voucher",
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
                        text = voucher.discountText,
                        color = HighlandWhite,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                // Used overlay
                if (voucher.isUsed) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.5f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "ĐÃ SỬ DỤNG",
                            color = HighlandWhite,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            
            // Content section
            Column(modifier = Modifier.padding(16.dp)) {
                if (voucher.hasFreeship) {
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
                    text = voucher.titleText,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (voucher.isUsed) HighlandText.copy(alpha = 0.5f) else HighlandText,
                    maxLines = 2
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = voucher.expiryDate,
                        fontSize = 12.sp,
                        color = HighlandText.copy(alpha = 0.6f)
                    )
                    
                    Button(
                        onClick = onUseClick,
                        enabled = !voucher.isUsed && !isProcessing,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = HighlandRed,
                            disabledContainerColor = HighlandRed.copy(alpha = 0.5f)
                        ),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        modifier = Modifier.semantics {
                            contentDescription = if (voucher.isUsed) "Đã sử dụng" else "Sử dụng voucher"
                        }
                    ) {
                        if (isProcessing) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                color = HighlandWhite,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text(
                                text = if (voucher.isUsed) "Đã dùng" else "Sử dụng",
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

// Improved Exchange Item Card with animations and validations
@Composable
fun ExchangeItemCard(
    item: ExchangeItem,
    currentBeans: Int,
    onExchange: () -> Unit,
    isProcessing: Boolean
) {
    val canAfford = currentBeans >= item.beanCost
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.97f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "exchange_card_scale"
    )
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .semantics {
                contentDescription = "${item.title}, cần ${item.beanCost} Bean"
            },
        colors = CardDefaults.cardColors(
            containerColor = if (!canAfford) Color.Gray.copy(alpha = 0.1f) else Color.White
        ),
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
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(
                            if (canAfford) HighlandRed.copy(alpha = 0.1f) else Color.Gray.copy(alpha = 0.1f),
                            RoundedCornerShape(10.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = item.imageRes),
                        contentDescription = "Icon ${item.title}",
                        modifier = Modifier.size(28.dp),
                        colorFilter = if (!canAfford) ColorFilter.tint(Color.Gray) else null
                    )
                }
                
                Spacer(modifier = Modifier.width(16.dp))
                
                Column {
                    Text(
                        text = item.title,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        color = if (canAfford) HighlandText else HighlandText.copy(alpha = 0.5f)
                    )
                    if (item.description.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = item.description,
                            fontSize = 11.sp,
                            color = HighlandText.copy(alpha = 0.5f),
                            maxLines = 1
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.coffee_beans),
                            contentDescription = "Bean icon",
                            modifier = Modifier.size(14.dp),
                            colorFilter = if (!canAfford) ColorFilter.tint(Color.Gray) else null
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${item.beanCost} Bean",
                            fontSize = 13.sp,
                            color = if (canAfford) HighlandRed else Color.Gray,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            
            Button(
                onClick = onExchange,
                enabled = canAfford && !isProcessing && item.isAvailable,
                colors = ButtonDefaults.buttonColors(
                    containerColor = HighlandRed,
                    disabledContainerColor = HighlandRed.copy(alpha = 0.3f)
                ),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                modifier = Modifier.semantics {
                    contentDescription = if (canAfford) "Đổi ${item.title}" else "Không đủ Bean"
                }
            ) {
                if (isProcessing) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(16.dp),
                        color = HighlandWhite,
                        strokeWidth = 2.dp
                    )
                } else {
                Text("Đổi", fontSize = 13.sp)
                }
            }
        }
    }
}

// Improved History Item Card with icons and better visual feedback
@Composable
fun HistoryItemCard(
    item: HistoryItem
) {
    val icon = when (item.type) {
        HistoryType.EARN -> Icons.Default.Add
        HistoryType.EXCHANGE -> Icons.Default.ShoppingCart
        HistoryType.EXPIRE -> Icons.Default.Warning
        HistoryType.BONUS -> Icons.Default.Star
    }
    
    val iconColor = when (item.type) {
        HistoryType.EARN -> Color(0xFF4CAF50)
        HistoryType.EXCHANGE -> HighlandRed
        HistoryType.EXPIRE -> Color(0xFFFF9800)
        HistoryType.BONUS -> Color(0xFF2196F3)
    }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .semantics {
                contentDescription = "${item.title}, ${item.amount}, ngày ${item.date}"
            },
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
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                // Icon indicator
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(iconColor.copy(alpha = 0.1f), RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = iconColor,
                        modifier = Modifier.size(20.dp)
                    )
                }
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Column {
                Text(
                        text = item.title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = HighlandText
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                        text = item.date,
                    fontSize = 12.sp,
                    color = HighlandText.copy(alpha = 0.6f)
                )
                }
            }
            
            Text(
                text = item.amount,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = if (item.isPositive) Color(0xFF4CAF50) else HighlandRed
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
