package com.apex.codeassesment.data

import com.apex.codeassesment.data.local.LocalDataSource
import com.apex.codeassesment.data.model.User
import com.apex.codeassesment.data.remote.RemoteDataSource
import java.util.concurrent.atomic.AtomicReference
import javax.inject.Inject

// TODO (2 points) : Add tests (COMPLETED)
// TODO (3 points) : Hide this class through an interface, (COMPLETED)
//  inject the interface in the clients instead and remove warnings -> (COMPLETED)
class UserRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : UserRepo {


    private val savedUser = AtomicReference(User())

    override fun getSavedUser(): User {
        return localDataSource.loadUser()
    }

    override suspend fun getUser(forceUpdate: Boolean): User {
        if (forceUpdate) {
            val user = remoteDataSource.loadUser()
            user?.let {
                localDataSource.saveUser(it)
                savedUser.set(user)
            }
        }
        return savedUser.get()
    }

    override suspend fun getUsers(): List<User> {
       return remoteDataSource.loadUsers()
    }
}
