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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cafetrio.R
import com.example.cafetrio.data.WishlistManager
import com.example.cafetrio.data.models.WishlistItem
import com.example.cafetrio.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WishListScreen(
    onBackClick: () -> Unit = {}
) {
    val wishlistItems = remember {
        listOf(
            WishlistItem(
                id = "1",
                name = "Cà Phê Sữa Đá",
                price = "39.000đ",
                imageRes = R.drawable.cfs_da
            ),
            WishlistItem(
                id = "2",
                name = "Trà Sữa Oolong",
                price = "55.000đ",
                imageRes = R.drawable.tra_sua_oolong_tu_quy_suong_sao
            )
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Sản phẩm yêu thích",
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
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(HighlandWhite)
        ) {
            if (wishlistItems.isEmpty()) {
                // Empty state
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_love),
                        contentDescription = "Empty Wishlist",
                        modifier = Modifier.size(80.dp)
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Text(
                        text = "Bạn chưa có sản phẩm yêu thích",
                        fontSize = 16.sp,
                        color = HighlandText,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                // Wishlist items
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                ) {
                    items(wishlistItems) { item ->
                        WishlistItemCard(item = item)
                    }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun WishlistItemCard(item: WishlistItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Product image
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(
                        color = HighlandRed.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.coffee_beans),
                    contentDescription = item.name,
                    modifier = Modifier.size(48.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))
            
            // Product details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = item.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = HighlandText
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = item.price,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = HighlandRed
                )
            }

            // Remove button
            IconButton(onClick = { /* Remove from wishlist */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_love),
                    contentDescription = "Remove",
                    tint = HighlandRed
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WishListScreenPreview() {
    CafeTrioTheme {
        WishListScreen()
    }
}
