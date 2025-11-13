package com.example.cafetrio.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cafetrio.R
import com.example.cafetrio.ui.theme.*

data class MockOrderItem(
    val id: String,
    val name: String,
    val price: Int,
    val quantity: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    onBackClick: () -> Unit = {},
    onNavigateToPayment: () -> Unit = {}
) {
    val mockOrders = remember {
        listOf(
            MockOrderItem("1", "Cà Phê Sữa Đá", 39000, 2),
            MockOrderItem("2", "Trà Sữa Oolong", 55000, 1),
            MockOrderItem("3", "Smoothie Xoài", 65000, 1)
        )
    }
    val isLoading = false

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Giỏ hàng",
                        color = HighlandWhite,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Quay lại",
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
        Box(modifier = Modifier.fillMaxSize()) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = HighlandRed
                )
            } else if (mockOrders.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_cart),
                        contentDescription = "Empty Cart",
                        modifier = Modifier
                            .size(120.dp)
                            .padding(bottom = 24.dp)
                    )
                    Text(
                        text = "Giỏ hàng trống",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = HighlandText,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Thêm sản phẩm vào giỏ hàng để tiếp tục!",
                        fontSize = 16.sp,
                        color = HighlandText.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 16.dp)
                    ) {
                        items(mockOrders) { orderItem ->
                            CartOrderItem(orderItem = orderItem)
                        }
                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }

                    // Tổng tiền và nút Thanh toán
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Tổng cộng:",
                                    fontSize = 16.sp,
                                    color = HighlandText
                                )
                                Text(
                                    text = "159.000đ",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = HighlandRed
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Button(
                                onClick = onNavigateToPayment,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(52.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = HighlandRed
                                ),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(
                                    text = "Thanh toán",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = HighlandWhite
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CartOrderItem(orderItem: MockOrderItem) {
    var quantity by remember { mutableStateOf(orderItem.quantity) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Hình ảnh sản phẩm
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(
                        HighlandRed.copy(alpha = 0.1f),
                        RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.coffee_beans),
                    contentDescription = "Product",
                    modifier = Modifier.size(48.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Thông tin sản phẩm
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = orderItem.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = HighlandText,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "${orderItem.price}đ",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = HighlandRed
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Điều khiển số lượng
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(
                            HighlandRed.copy(alpha = 0.1f),
                            RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 4.dp, vertical = 4.dp)
                ) {
                    // Nút giảm
                    IconButton(
                        onClick = { if (quantity > 1) quantity-- },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Text(
                            text = "−",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = HighlandRed
                        )
                    }

                    Text(
                        text = "$quantity",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = HighlandText,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    // Nút tăng
                    IconButton(
                        onClick = { quantity++ },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Text(
                            text = "+",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = HighlandRed
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CartScreenPreview() {
    CafeTrioTheme {
        CartScreen()
    }
}