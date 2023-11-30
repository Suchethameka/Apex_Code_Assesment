import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.apex.codeassesment.data.UserRepo
import com.apex.codeassesment.data.model.User
import com.apex.codeassesment.viewmodel.UserViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class UserViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    private val userRepository: UserRepo = mockk()
    private val userViewModel = UserViewModel(userRepository, testDispatcher)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher) // Set the main dispatcher to the test dispatcher
    }

    @Test
    fun `refreshUsers should update users LiveData with the data from repository`() = testDispatcher.runBlockingTest {
        val fakeUsers = listOf(User(), User())
        coEvery { userRepository.getUsers() } returns fakeUsers
        userViewModel.refreshUsers()
        assertEquals(fakeUsers, userViewModel.users.value)
    }

    @Test
    fun `refreshRandomUser should update randomUser LiveData with the data from repository`() = testDispatcher.runBlockingTest {
        val fakeRandomUser = User()
        coEvery { userRepository.getUser(true) } returns fakeRandomUser
        userViewModel.refreshRandomUser()
        assertEquals(fakeRandomUser, userViewModel.randomUser.value)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset the main dispatcher after the test
        testScope.cleanupTestCoroutines()
    }
}
