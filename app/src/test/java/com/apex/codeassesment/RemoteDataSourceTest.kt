package com.apex.codeassesment

import com.apex.codeassesment.api.ApiService
import com.apex.codeassesment.data.model.Name
import com.apex.codeassesment.data.model.User
import com.apex.codeassesment.data.model.UserResponse
import com.apex.codeassesment.data.remote.RemoteDataSource
import retrofit2.Response
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Test

class RemoteDataSourceTest {

    @Test
    fun `loadUsers should return a list of users on success`() = runBlocking {
        // Given
        val apiService = mockk<ApiService>()
        coEvery { apiService.getUsers() } returns createMockUsersResponse()

        val remoteDataSource = RemoteDataSource(apiService)

        // When
        val result = remoteDataSource.loadUsers()

        // Then
        assertEquals(createMockUsers(), result)
    }

    @Test
    fun `loadUsers should return an empty list on API failure`() = runBlocking {
        // Given
        val apiService = mockk<ApiService>()
        coEvery { apiService.getUsers() } returns createErrorResponse()

        val remoteDataSource = RemoteDataSource(apiService)

        // When
        val result = remoteDataSource.loadUsers()

        // Then
        assertEquals(emptyList<User>(), result)
    }

    // Helper method to create an error response with a mocked ResponseBody
    private fun createErrorResponse(): Response<UserResponse> {
        val errorResponseBody = ResponseBody.create("application/json".toMediaType(), "")
        return Response.error(500, errorResponseBody)
    }

    private fun createMockUserResponse(): Response<UserResponse> {
        return Response.success(createMockUserResponseBody())
    }

    private fun createMockUserResponseBody(): UserResponse {
        return UserResponse(results = listOf(createMockUser()), info = Any())
    }

    private fun createMockUsersResponse(): Response<UserResponse> {
        return Response.success(UserResponse(results = createMockUsers(), info = Any()))
    }

    private fun createMockUser(): User {
        return User(gender = "Male", name = Name(title = "Mr.", first = "John", last = "Doe"))
    }

    private fun createMockUsers(): List<User> {
        return listOf(createMockUser(), createMockUser())
    }
}
