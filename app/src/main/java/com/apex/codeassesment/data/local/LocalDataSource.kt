package com.apex.codeassesment.data.local

import com.apex.codeassesment.data.model.User
import com.apex.codeassesment.data.model.User.Companion.createRandom
import com.squareup.moshi.Moshi
import javax.inject.Inject

// TODO (3 points): Convert to Kotlin -> (COMPLETED)
// TODO (2 point): Add tests -> (COMPLETED)
// TODO (1 point): Use the correct naming conventions. -> (COMPLETED)
// TODO (3 points): Inject all dependencies instead of instantiating them. -> (COMPLETED)
class LocalDataSource @Inject constructor(
    private val preferencesManager: PreferencesManager,
    private val moshi : Moshi
) {
    private val jsonAdapter = moshi.adapter(
        User::class.java
    )

    fun loadUser(): User {
        val serializedUser = preferencesManager.loadUser()
        return try {
            // TODO (1 point): Address Android Studio warning -> (COMPLETED)
            val user = serializedUser?.let { jsonAdapter.fromJson(it) }
            user ?: createRandom()
        } catch (e: Exception) {
            e.printStackTrace()
            createRandom()
        }
    }

    fun saveUser(user: User) {
        val serializedUser = jsonAdapter.toJson(user)
        preferencesManager.saveUser(serializedUser)
    }
}