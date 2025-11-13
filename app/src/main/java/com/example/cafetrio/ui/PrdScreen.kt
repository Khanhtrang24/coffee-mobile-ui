package com.example.cafetrio.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.cafetrio.R
import com.example.cafetrio.ui.theme.*

// Mock product data
data class MockProductDetail(
    val id: String,
    val name: String,
    val price: String,
    val description: String,
    val imageRes: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrdScreen(
    productId: String,
    onBackClick: () -> Unit = {},
    onViewCart: () -> Unit = {},
    onNavigateToMain: () -> Unit = {}
) {
    var showSuccessDialog by remember { mutableStateOf(false) }

    // Mock data
    val product = remember {
        MockProductDetail(
            id = productId,
            name = "Cà Phê Sữa Đá",
            price = "39.000đ",
            description = "Cà phê sữa đá truyền thống Việt Nam với hương vị đậm đà, kết hợp hoàn hảo giữa cà phê robusta thơm ngon và sữa đặc béo ngậy. Phù hợp cho những ai yêu thích vị cà phê mạnh mẽ, đậm chất.",
            imageRes = R.drawable.cfs_da
        )
    }
    val isLoading = false
    val error: String? = null

    var isInWishlist by remember { mutableStateOf(false) }
    var isDescriptionExpanded by remember { mutableStateOf(false) }
    var quantity by remember { mutableStateOf(1) }

    if (showSuccessDialog) {
        Dialog(
            onDismissRequest = {
                showSuccessDialog = false
                onNavigateToMain()
            },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .clip(RoundedCornerShape(24.dp))
                    .background(HighlandWhite)
                    .padding(24.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Đóng",
                        tint = HighlandRed,
                        modifier = Modifier
                            .align(Alignment.End)
                            .size(24.dp)
                            .clickable {
                                showSuccessDialog = false
                                onNavigateToMain()
                            }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Icon(
                        painter = painterResource(id = R.drawable.ic_cart),
                        contentDescription = "Success",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(64.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Đã thêm vào giỏ hàng!",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = HighlandText,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = buildAnnotatedString {
                            append("Xem giỏ hàng của bạn tại ")
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = HighlandRed)) {
                                append("Giỏ hàng")
                            }
                        },
                        fontSize = 14.sp,
                        color = HighlandText,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.clickable {
                            showSuccessDialog = false
                            onViewCart()
                        }
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = {
                            showSuccessDialog = false
                            onNavigateToMain()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = HighlandRed),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Tiếp tục mua sắm", color = HighlandWhite, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }

    Scaffold(
        containerColor = HighlandWhite,
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Quay lại",
                            tint = HighlandWhite
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { isInWishlist = !isInWishlist }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_love),
                            contentDescription = "Yêu thích",
                            tint = if (isInWishlist) HighlandRed else HighlandWhite
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = HighlandRed
                )
            )
        },
        // *** ĐẶT BOTTOM BAR VÀO ĐÂY ***
        bottomBar = {
            if (!isLoading && error == null) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Tổng cộng",
                                fontSize = 14.sp,
                                color = HighlandText.copy(alpha = 0.7f)
                            )
                            Text(
                                text = product.price,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = HighlandRed
                            )
                        }

                        Button(
                            onClick = { showSuccessDialog = true },
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 16.dp)
                                .height(52.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = HighlandRed),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_cart),
                                contentDescription = null,
                                tint = Color.Unspecified,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Thêm vào giỏ",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = HighlandWhite
                            )
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        if (isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = HighlandRed)
            }
        } else if (error != null) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(error, color = HighlandRed)
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues) // Áp dụng padding (có cả top và bottom)
                    .verticalScroll(rememberScrollState())
            ) {
                // Product image
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .background(HighlandRed.copy(alpha = 0.05f))
                ) {
                    Image(
                        painter = painterResource(id = product.imageRes),
                        contentDescription = product.name,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                    Box(
                        modifier = Modifier
                            .padding(12.dp)
                            .align(Alignment.TopStart)
                            .background(HighlandRed, RoundedCornerShape(6.dp))
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "NEW",
                            color = HighlandWhite,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Product info
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Text(
                        text = product.name,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = HighlandText
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = product.price,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = HighlandRed
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Divider(color = HighlandText.copy(alpha = 0.1f))

                    Spacer(modifier = Modifier.height(20.dp))

                    // Description
                    Text(
                        text = "Mô tả sản phẩm",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = HighlandText
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = product.description,
                        fontSize = 14.sp,
                        color = HighlandText.copy(alpha = 0.8f),
                        lineHeight = 20.sp,
                        maxLines = if (isDescriptionExpanded) Int.MAX_VALUE else 3
                    )

                    if (product.description.length > 100) {
                        TextButton(onClick = { isDescriptionExpanded = !isDescriptionExpanded }) {
                            Text(
                                text = if (isDescriptionExpanded) "Thu gọn" else "Xem thêm",
                                color = HighlandRed,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Divider(color = HighlandText.copy(alpha = 0.1f))

                    Spacer(modifier = Modifier.height(20.dp))

                    // Quantity selector
                    Text(
                        text = "Số lượng",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = HighlandText
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .background(HighlandRed.copy(alpha = 0.1f), RoundedCornerShape(12.dp))
                            .padding(8.dp)
                    ) {
                        IconButton(
                            onClick = { if (quantity > 1) quantity-- },
                            modifier = Modifier.size(40.dp)
                        ) {
                            Text(
                                text = "−",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = HighlandRed
                            )
                        }

                        Text(
                            text = "$quantity",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = HighlandText,
                            modifier = Modifier.padding(horizontal = 24.dp)
                        )

                        IconButton(
                            onClick = { quantity++ },
                            modifier = Modifier.size(40.dp)
                        ) {
                            Text(
                                text = "+",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = HighlandRed
                            )
                        }

                    }

                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }

        // *** XÓA HOÀN TOÀN KHỐI BOX VÀ CARD Ở ĐÂY ***
    }
}

@Preview(showBackground = true)
@Composable
fun PrdScreenPreview() {
    CafeTrioTheme {
        PrdScreen(productId = "1")
    }
}