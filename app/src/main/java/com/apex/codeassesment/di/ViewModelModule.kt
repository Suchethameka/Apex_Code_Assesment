package com.apex.codeassesment.di

import com.apex.codeassesment.data.UserRepositoryImpl
import com.apex.codeassesment.viewmodel.UserViewModel
import dagger.Module
import dagger.Provides

@Module
class ViewModelModule {

    @Provides
    fun provideUserViewModel(repository: UserRepositoryImpl): UserViewModel {
        return UserViewModel(repository)
    }
}