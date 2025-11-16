package com.example.cafetrio.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cafetrio.R
import com.example.cafetrio.data.models.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

/**
 * ViewModel cho CouponScreen
 * Quản lý state và business logic cho rewards/coupon system
 */
class CouponViewModel : ViewModel() {
    
    private val _uiState = MutableStateFlow(CouponUiState())
    val uiState: StateFlow<CouponUiState> = _uiState.asStateFlow()
    
    init {
        loadData()
    }
    
    private fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            
            // Simulate API call
            delay(500)
            
            try {
                // Load mock data - trong thực tế sẽ gọi API
                val vouchers = getMockVouchers()
                val exchangeItems = getMockExchangeItems()
                val historyItems = getMockHistoryItems()
                val beanBalance = BeanBalance(
                    current = 88,
                    total = 250,
                    expiringSoon = 15,
                    expiryDate = "31/12/2024"
                )
                
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        vouchers = vouchers,
                        exchangeItems = exchangeItems,
                        historyItems = historyItems,
                        beanBalance = beanBalance,
                        userName = "Nguyen Phan"
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Không thể tải dữ liệu. Vui lòng thử lại."
                    )
                }
            }
        }
    }
    
    fun useVoucher(voucherId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isProcessing = true) }
            
            // Simulate API call
            delay(500)
            
            // Update voucher status
            val updatedVouchers = _uiState.value.vouchers.map { voucher ->
                if (voucher.id == voucherId) {
                    voucher.copy(isUsed = true)
                } else {
                    voucher
                }
            }
            
            _uiState.update {
                it.copy(
                    vouchers = updatedVouchers,
                    isProcessing = false,
                    successMessage = "Đã áp dụng voucher thành công!"
                )
            }
            
            // Clear success message after 2 seconds
            delay(2000)
            clearSuccessMessage()
        }
    }
    
    fun exchangeBean(itemId: String, beanCost: Int) {
        viewModelScope.launch {
            if (_uiState.value.beanBalance.current < beanCost) {
                _uiState.update {
                    it.copy(errorMessage = "Bean không đủ để đổi quà này!")
                }
                delay(2000)
                clearErrorMessage()
                return@launch
            }
            
            _uiState.update { it.copy(isProcessing = true) }
            
            // Simulate API call
            delay(500)
            
            // Deduct beans and add history item
            val newBalance = _uiState.value.beanBalance.copy(
                current = _uiState.value.beanBalance.current - beanCost
            )
            
            val exchangeItem = _uiState.value.exchangeItems.find { it.id == itemId }
            val newHistoryItem = HistoryItem(
                id = "history_${System.currentTimeMillis()}",
                title = "Đổi ${exchangeItem?.title ?: "quà"}",
                amount = "-$beanCost Bean",
                beanAmount = -beanCost,
                date = getCurrentDate(),
                timestamp = System.currentTimeMillis(),
                isPositive = false,
                type = HistoryType.EXCHANGE
            )
            
            _uiState.update {
                it.copy(
                    beanBalance = newBalance,
                    historyItems = listOf(newHistoryItem) + it.historyItems,
                    isProcessing = false,
                    successMessage = "Đổi quà thành công! Kiểm tra voucher của bạn."
                )
            }
            
            delay(2000)
            clearSuccessMessage()
        }
    }
    
    fun refreshData() {
        loadData()
    }
    
    fun clearErrorMessage() {
        _uiState.update { it.copy(errorMessage = null) }
    }
    
    fun clearSuccessMessage() {
        _uiState.update { it.copy(successMessage = null) }
    }
    
    // Mock data functions
    private fun getMockVouchers(): List<VoucherItem> = listOf(
        VoucherItem(
            id = "v1",
            discountText = "30%",
            titleText = "Giảm 30% toàn bộ Menu Nước Size Lớn",
            expiryDate = "HSD: 23/04/2025",
            imageRes = R.drawable.vc_1,
            hasFreeship = false
        ),
        VoucherItem(
            id = "v2",
            discountText = "40%",
            titleText = "Giảm 40% + Freeship Đơn Từ 10 Ly",
            expiryDate = "HSD: 30/04/2025",
            imageRes = R.drawable.vc_2,
            hasFreeship = true
        ),
        VoucherItem(
            id = "v3",
            discountText = "30%",
            titleText = "Giảm 30% + Freeship Đơn Từ 3 Ly",
            expiryDate = "HSD: 30/04/2025",
            imageRes = R.drawable.vc_3,
            hasFreeship = true
        )
    )
    
    private fun getMockExchangeItems(): List<ExchangeItem> = listOf(
        ExchangeItem(
            id = "e1",
            title = "Voucher Giảm 20%",
            description = "Áp dụng cho tất cả sản phẩm",
            beanCost = 30,
            imageRes = R.drawable.ic_voucher
        ),
        ExchangeItem(
            id = "e2",
            title = "Free 1 Cà Phê",
            description = "Miễn phí 1 ly cà phê bất kỳ",
            beanCost = 50,
            imageRes = R.drawable.coffee_beans
        ),
        ExchangeItem(
            id = "e3",
            title = "Freeship Toàn Quốc",
            description = "Miễn phí giao hàng không giới hạn",
            beanCost = 25,
            imageRes = R.drawable.shipping
        )
    )
    
    private fun getMockHistoryItems(): List<HistoryItem> = listOf(
        HistoryItem(
            id = "h1",
            title = "Tích Bean từ đơn hàng",
            amount = "+15 Bean",
            beanAmount = 15,
            date = "15/11/2024",
            timestamp = System.currentTimeMillis() - 86400000,
            isPositive = true,
            type = HistoryType.EARN
        ),
        HistoryItem(
            id = "h2",
            title = "Đổi voucher giảm 20%",
            amount = "-30 Bean",
            beanAmount = -30,
            date = "14/11/2024",
            timestamp = System.currentTimeMillis() - 172800000,
            isPositive = false,
            type = HistoryType.EXCHANGE
        ),
        HistoryItem(
            id = "h3",
            title = "Tích Bean từ đơn hàng",
            amount = "+25 Bean",
            beanAmount = 25,
            date = "12/11/2024",
            timestamp = System.currentTimeMillis() - 345600000,
            isPositive = true,
            type = HistoryType.EARN
        )
    )
    
    private fun getCurrentDate(): String {
        val sdf = java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault())
        return sdf.format(java.util.Date())
    }
}

data class CouponUiState(
    val isLoading: Boolean = false,
    val isProcessing: Boolean = false,
    val vouchers: List<VoucherItem> = emptyList(),
    val exchangeItems: List<ExchangeItem> = emptyList(),
    val historyItems: List<HistoryItem> = emptyList(),
    val beanBalance: BeanBalance = BeanBalance(0, 0),
    val userName: String = "",
    val errorMessage: String? = null,
    val successMessage: String? = null
)

