package com.apex.codeassesment.data.remote

import android.util.Log
import com.apex.codeassesment.api.ApiService
import com.apex.codeassesment.data.model.User
import javax.inject.Inject

// TODO (2 points): Add tests (COMPLETED)
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    // TODO (5 points): Load data from endpoint https://randomuser.me/api (COMPLETED)
    suspend fun loadUser(): User? {
        try {
            val user = apiService.getUser()
            Log.e("ApiService", "No Exception ${user.results.firstOrNull()}")
            return user.results.firstOrNull()
        } catch (e: Exception) {
            Log.e("ApiService", "Error loading user", e)
        }
        return null
    }

    // TODO (3 points): Load data from endpoint https://randomuser.me/api?results=10 (COMPLETED)
    // TODO (Optional Bonus: 3 points): Handle success and failure from endpoints (COMPLETED)
    suspend fun loadUsers(): List<User> {
        return try {
            val users = apiService.getUsers()

            if (users.isSuccessful) {
                users.body()?.results ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            // Handle exceptions here
            emptyList()
        }
    }
}
