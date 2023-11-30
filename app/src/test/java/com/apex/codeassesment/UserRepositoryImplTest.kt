package com.apex.codeassesment

import com.apex.codeassesment.data.UserRepo
import com.apex.codeassesment.data.UserRepositoryImpl
import com.apex.codeassesment.data.local.LocalDataSource
import com.apex.codeassesment.data.model.Name
import com.apex.codeassesment.data.model.User
import com.apex.codeassesment.data.remote.RemoteDataSource
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class UserRepositoryImplTest {

    private val localDataSource: LocalDataSource = mockk()
    private val remoteDataSource: RemoteDataSource = mockk()
    private val userRepository: UserRepo = UserRepositoryImpl(localDataSource, remoteDataSource)

    @Test
    fun `getSavedUser should return user from local data source`() {
        val expectedUser = User(name = Name("John Doe"))
        every { localDataSource.loadUser() } returns expectedUser
        val result = userRepository.getSavedUser()
        assertEquals(expectedUser, result)
    }

    @Test
    fun `getUser with forceUpdate=true should update and return user from remote data source`() = runBlocking {
        val expectedUser = User(name = Name("John Doe"))
        coEvery { remoteDataSource.loadUser() } returns expectedUser
        every { localDataSource.saveUser(any()) } just runs
        val result = userRepository.getUser(forceUpdate = true)
        assertEquals(expectedUser, result)
    }

    @Test
    fun `getUser with forceUpdate=false should return user from savedUser`() = runBlocking {
        val expectedUser = User(gender = null, name = null, location = null, email = null, login = null, dob = null, registered = null, phone = null, cell = null, id = null, picture = null, nat = null)
        every { localDataSource.loadUser() } returns expectedUser // Simulate a saved user with a null name
        val result = userRepository.getUser(forceUpdate = false)
        assertEquals(expectedUser, result)
    }

    @Test
    fun `getUsers should return list of users from remote data source`() = runBlocking {
        val expectedUsers = listOf(User(name = Name("John Doe")), User(name = Name("Jane Doe")))
        coEvery { remoteDataSource.loadUsers() } returns expectedUsers
        val result = userRepository.getUsers()
        assertEquals(expectedUsers, result)
    }
}
