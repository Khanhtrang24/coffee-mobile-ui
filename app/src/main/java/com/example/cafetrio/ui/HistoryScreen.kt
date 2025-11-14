package com.example.cafetrio.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cafetrio.R
import com.example.cafetrio.ui.theme.*

data class OrderHistoryItem(
    val id: String,
    val date: String,
    val items: List<String>,
    val status: OrderStatus
)

enum class OrderStatus {
    DELIVERING,
    DELIVERED,
    CANCELLED
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    onBackClick: () -> Unit = {}
) {
    val orderHistory = remember {
        listOf(
            OrderHistoryItem("1", "10/04", listOf("1 Smoothie Xoài Nhiệt Đới"), OrderStatus.DELIVERING),
            OrderHistoryItem("2", "09/04", listOf("1 Cà Phê Sữa Đá"), OrderStatus.DELIVERED),
            OrderHistoryItem("3", "08/04", listOf("1 Trà Sữa Oolong"), OrderStatus.CANCELLED),
            OrderHistoryItem("4", "07/04", listOf("2 Cà Phê Đen Đá"), OrderStatus.DELIVERED),
            OrderHistoryItem("5", "06/04", listOf("1 Chocolate Nóng"), OrderStatus.DELIVERED)
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Lịch sử đơn hàng",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = HighlandWhite
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = HighlandWhite
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = HighlandRed
                )
            )
        },
        containerColor = HighlandWhite
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            items(orderHistory) { order ->
                OrderHistoryCard(order = order)
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun OrderHistoryCard(order: OrderHistoryItem) {
    val statusColor = when (order.status) {
        OrderStatus.DELIVERED -> Color(0xFF4CAF50)
        OrderStatus.CANCELLED -> HighlandRed
        OrderStatus.DELIVERING -> Color(0xFFFF9800)
    }
    
    val statusText = when (order.status) {
        OrderStatus.DELIVERED -> "Đã giao hàng"
        OrderStatus.CANCELLED -> "Đã hủy"
        OrderStatus.DELIVERING -> "Đang giao"
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Timeline indicator
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.width(70.dp)
            ) {
                // Status icon
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            color = statusColor.copy(alpha = 0.2f),
                            shape = androidx.compose.foundation.shape.CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_shipper),
                        contentDescription = "Status",
                        modifier = Modifier.size(28.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Date badge
                Box(
                    modifier = Modifier
                        .background(
                            color = HighlandRed.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = order.date,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = HighlandRed
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))
            
            // Order details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                // Status badge
                Box(
                    modifier = Modifier
                        .background(
                            color = statusColor,
                            shape = RoundedCornerShape(6.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = statusText,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = HighlandWhite
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Order items
                order.items.forEach { item ->
                    Text(
                        text = "• $item",
                        fontSize = 14.sp,
                        color = HighlandText,
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Order ID
                Text(
                    text = "Mã đơn: #${order.id}",
                    fontSize = 12.sp,
                    color = HighlandText.copy(alpha = 0.6f)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HistoryScreenPreview() {
    CafeTrioTheme {
        HistoryScreen()
    }
}
