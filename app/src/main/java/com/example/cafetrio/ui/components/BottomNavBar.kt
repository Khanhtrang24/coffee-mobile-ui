package com.example.cafetrio.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cafetrio.R
import com.example.cafetrio.ui.theme.HighlandRed
import com.example.cafetrio.ui.theme.HighlandWhite
import com.example.cafetrio.ui.theme.CafeTrioTheme

enum class NavigationItem {
    HOME, ORDER, REWARDS, MORE
}

@Composable
fun BottomNavBar(
    currentItem: NavigationItem,
    onNavigate: (String) -> Unit
) {
    Surface(
        color = HighlandRed,
        shape = RectangleShape,
        shadowElevation = 6.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Trang chủ
            NavBarItem(
                iconRes = R.drawable.ic_home,
                label = "Trang chủ",
                isActive = currentItem == NavigationItem.HOME,
                onClick = { onNavigate("home") }
            )

            // Đặt hàng
            NavBarItem(
                iconRes = R.drawable.ic_booked,
                label = "Đặt hàng",
                isActive = currentItem == NavigationItem.ORDER,
                onClick = { onNavigate("order") }
            )

            // Ưu đãi
            NavBarItem(
                iconRes = R.drawable.ic_voucher,
                label = "Ưu đãi",
                isActive = currentItem == NavigationItem.REWARDS,
                onClick = { onNavigate("rewards") }
            )

            // Khác
            NavBarItem(
                iconRes = R.drawable.ic_differ,
                label = "Khác",
                isActive = currentItem == NavigationItem.MORE,
                onClick = { onNavigate("differ") }
            )
        }
    }
}

@Composable
private fun NavBarItem(
    iconRes: Int,
    label: String,
    isActive: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp)
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = label,
            modifier = Modifier.size(24.dp),
            colorFilter = ColorFilter.tint(HighlandWhite)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = label,
            color = HighlandWhite,
            fontSize = 12.sp,
            fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal
        )

        // Active indicator
        if (isActive) {
            Box(
                modifier = Modifier
                    .padding(top = 4.dp)
                    .width(32.dp)
                    .height(3.dp)
                    .background(
                        color = HighlandWhite,
                        shape = RoundedCornerShape(2.dp)
                    )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavBarPreview() {
    CafeTrioTheme {
        BottomNavBar(
            currentItem = NavigationItem.HOME,
            onNavigate = {}
        )
    }
}
