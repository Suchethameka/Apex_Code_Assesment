package com.apex.codeassesment

import com.apex.codeassesment.data.model.User
import org.junit.Assert.assertTrue
import org.junit.Test

class UserTest {

    @Test
    fun `createRandom should generate a User with valid random values`() {
        // When
        val randomUser = User.createRandom()
        assertTrue(randomUser.dob?.age in 0..100)
        assertTrue(randomUser.location?.coordinates?.latitude?.toDoubleOrNull() ?: 0.0 in -90.0..90.0)
        assertTrue(randomUser.location?.coordinates?.longitude?.toDoubleOrNull() ?: 0.0 in -180.0..180.0)
    }
}