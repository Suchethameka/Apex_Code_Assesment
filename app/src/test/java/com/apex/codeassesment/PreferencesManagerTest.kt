package com.apex.codeassesment

import android.content.SharedPreferences
import com.apex.codeassesment.data.local.PreferencesManager
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class PreferencesManagerTest {

    private val sharedPreferences: SharedPreferences = mockk()
    private val editor: SharedPreferences.Editor = mockk(relaxed = true) // relaxed mode allows unmocked calls
    private val preferencesManager = PreferencesManager(sharedPreferences)

    @Before
    fun setUp() {
        // Define the behavior of SharedPreferences
        every { sharedPreferences.edit() } returns editor
        every { editor.putString(any(), any()) } returns editor
        every { editor.apply() } returns Unit
        every { sharedPreferences.getString(any(), any()) } returns null
    }

    @Test
    fun `saveUser should store the user in SharedPreferences`() {
        // Given
        val user = "John Doe"

        // When
        preferencesManager.saveUser(user)

        // Then
        verify {
            editor.putString("saved-user", user)
            editor.apply()
        }
    }

    @Test
    fun `loadUser should retrieve the user from SharedPreferences`() {
        // Given
        val savedUser = "Jane Doe"
        every { sharedPreferences.getString("saved-user", null) } returns savedUser

        // When
        val loadedUser = preferencesManager.loadUser()

        // Then
        assertEquals(savedUser, loadedUser)
    }
}
