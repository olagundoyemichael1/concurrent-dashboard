// Lab 8, Step 4: Create the Concurrent Service
package com.example.concurrent

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class DashboardService {
    suspend fun getUserDashboard(userId: Int): UserDashboard = coroutineScope {
        val profileDeferred = async { ExternalServices.fetchUserProfile(userId) }
        val ordersDeferred = async { ExternalServices.fetchOrders(userId) }
        val recommendationsDeferred = async { ExternalServices.fetchRecommendations(userId) }
        val profile = profileDeferred.await()
        val orders = ordersDeferred.await()
        val recommendations = recommendationsDeferred.await()
        val totalSpent = orders.sumOf { it.amount }
        UserDashboard(profile, orders, recommendations, totalSpent)
    }
    suspend fun getMultipleDashboards(userIds: List<Int>): List<UserDashboard> = coroutineScope {
        userIds.map { userId -> async { getUserDashboard(userId) } }.map { it.await() }
    }
}