package com.example.cafetrio.data.models

/**
 * Data models for Coupon/Rewards screen
 */

data class VoucherItem(
    val id: String,
    val discountText: String,
    val titleText: String,
    val expiryDate: String,
    val imageRes: Int,
    val hasFreeship: Boolean = false,
    val isUsed: Boolean = false,
    val isExpired: Boolean = false
)

data class ExchangeItem(
    val id: String,
    val title: String,
    val description: String = "",
    val beanCost: Int,
    val imageRes: Int,
    val isAvailable: Boolean = true
)

data class HistoryItem(
    val id: String,
    val title: String,
    val amount: String,
    val beanAmount: Int,
    val date: String,
    val timestamp: Long,
    val isPositive: Boolean,
    val type: HistoryType
)

enum class HistoryType {
    EARN,      // Tích bean
    EXCHANGE,  // Đổi voucher/quà
    EXPIRE,    // Bean hết hạn
    BONUS      // Bean thưởng
}

data class BeanBalance(
    val current: Int,
    val total: Int,
    val expiringSoon: Int = 0,
    val expiryDate: String? = null
)

