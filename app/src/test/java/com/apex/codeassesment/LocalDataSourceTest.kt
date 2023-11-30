package com.apex.codeassesment

import com.apex.codeassesment.data.local.LocalDataSource
import com.apex.codeassesment.data.local.PreferencesManager
import com.apex.codeassesment.data.model.Name
import com.apex.codeassesment.data.model.User
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class LocalDataSourceTest {

    private lateinit var localDataSource: LocalDataSource
    private lateinit var preferencesManager: PreferencesManager
    private lateinit var moshi: Moshi

    @Before
    fun setup() {
        preferencesManager = mockk()
        moshi = mockk()

        // Mocking Moshi adapter
        val jsonAdapter = mockk<JsonAdapter<User>>()
        every { moshi.adapter(User::class.java) } returns jsonAdapter

        localDataSource = LocalDataSource(preferencesManager, moshi)

        // Mock the behavior of JsonAdapter.fromJson
        val expectedUser = User(
            gender = "Male",
            name = Name(title = "Mr.", first = "John", last = "Doe")
            // Add other properties as needed
        )
        every { jsonAdapter.fromJson("{\"gender\":\"Male\",\"name\":{\"title\":\"Mr.\",\"first\":\"John\",\"last\":\"Doe\"}}") } returns expectedUser
    }

    @Test
    fun `loadUser should return saved user when available`() {
        val savedUserJson =
            """{"gender":"Male","name":{"title":"Mr.","first":"John","last":"Doe"}}"""
        val expectedUser =
            User(gender = "Male", name = Name(title = "Mr.", first = "John", last = "Doe"))

        coEvery { preferencesManager.loadUser() } returns savedUserJson
        every { moshi.adapter(User::class.java).fromJson(savedUserJson) } returns expectedUser

        val result = localDataSource.loadUser()

        assertEquals(expectedUser, result)
    }

    @Test
    fun `loadUser should return a random user when no user is saved`() {
        coEvery { preferencesManager.loadUser() } returns null

        val result = localDataSource.loadUser()

        assert(result != null)
    }
}
