package com.apex.codeassesment.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.apex.codeassesment.adapter.UserRecyclerViewAdapter
import com.apex.codeassesment.data.model.User
import com.apex.codeassesment.databinding.ActivityMainBinding
import com.apex.codeassesment.di.MainComponent
import com.apex.codeassesment.ui.details.DetailsActivity
import com.apex.codeassesment.viewmodel.UserViewModel
import com.bumptech.glide.Glide
import javax.inject.Inject

// TODO (5 points): Move calls to repository to Presenter or ViewModel. -> (COMPLETED)
// TODO (5 points): Use combination of sealed/Dataclasses for exposing the data required by the view from viewModel. (COMPLETED)
// TODO (1 point): Add content description to images (COMPLETED)
// TODO (3 points): Add tests (COMPLETED)
// TODO (2 points): Convert to view binding -> (COMPLETED)
// TODO (Optional Bonus 10 points): Make a copy of this activity with different name and convert the current layout it is using in
//  Jetpnoack Compose. (COMPLETED -> Name: MainActivityCompose)
class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding

  @Inject
  lateinit var viewModel: UserViewModel

  private lateinit var userAdapter: UserRecyclerViewAdapter

  private var randomUser: User = User()
    set(value) {
      // TODO (1 point): Use Glide to load images after getting the data from endpoints mentioned in RemoteDataSource -> (COMPLETED)
      Glide.with(this)
        .load(value.picture?.medium)
        .into(binding.mainImage)
      binding.mainImage.contentDescription = value.name.toString()
      binding.mainName.text = value.name?.first
      binding.mainEmail.text = value.email
      field = value
    }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    (applicationContext as MainComponent.Injector).mainComponent.inject(this)
    setUpBinding(binding)
    setUpRecyclerView(binding)
    setUpObservers()
    setUpComposeButton()
  }

  /**
   * This is A Mini Button Just Beside Show 10 Users Button in the MainActivity
   * On Click Of this it will Launch a compose activity
   */
  /**
   * Make the below line View.VISIBLE To Launch A Compose Activity
   * binding.openComposeActivity.visibility = View.GONE
   */
  private fun setUpComposeButton() {
    binding.openComposeActivity.visibility = View.GONE
    binding.openComposeActivity.setOnClickListener { startActivity(Intent(this, MainActivityCompose::class.java)) }
  }

  private fun setUpBinding(binding: ActivityMainBinding) {
    binding.mainSeeDetailsButton.setOnClickListener { navigateDetails(randomUser) }
    binding.mainRefreshButton.setOnClickListener { viewModel.refreshRandomUser() }
    binding.mainUserListButton.setOnClickListener { viewModel.refreshUsers() }
  }

  private fun setUpObservers() {
    viewModel.randomUser.observe(this) { newUser ->
      randomUser = newUser
    }
    viewModel.users.observe(this) { userList ->
      userAdapter.items = userList
    }
  }

  private fun setUpRecyclerView(binding: ActivityMainBinding) {
    userAdapter = UserRecyclerViewAdapter()
    binding.mainUserList.adapter = userAdapter
    binding.mainUserList.layoutManager = LinearLayoutManager(this)
  }

  // TODO (2 points): Convert to extension function. -> (COMPLETED)
  private fun Context.navigateDetails(user: User) {
    val intent = Intent(this, DetailsActivity::class.java).putExtra(SAVED_USER_KEY, user)
    startActivity(intent)
  }


  companion object {
    const val SAVED_USER_KEY = "saved-user-key"
  }
}
