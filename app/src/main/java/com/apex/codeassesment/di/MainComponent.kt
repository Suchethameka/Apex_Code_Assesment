package com.apex.codeassesment.di

import android.content.Context
import com.apex.codeassesment.ui.main.MainActivity
import com.apex.codeassesment.viewmodel.UserViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [MainModule::class, ViewModelModule::class, NetworkModule::class])
interface MainComponent {

  @Component.Factory
  interface Factory {
    fun create(@BindsInstance applicationContext: Context): MainComponent
  }

  interface Injector {
    val mainComponent: MainComponent
  }

  fun inject(mainActivity: MainActivity)

  fun inject(viewModel: UserViewModel)
}