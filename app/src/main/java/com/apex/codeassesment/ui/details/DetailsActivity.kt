package com.apex.codeassesment.ui.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.apex.codeassesment.R
import com.apex.codeassesment.data.model.Coordinates
import com.apex.codeassesment.data.model.Name
import com.apex.codeassesment.data.model.User
import com.apex.codeassesment.databinding.ActivityDetailsBinding
import com.apex.codeassesment.ui.location.LocationActivity
import com.apex.codeassesment.ui.main.MainActivity.Companion.SAVED_USER_KEY
import com.bumptech.glide.Glide

// TODO (3 points): Convert to Kotlin -> (COMPLETED)
// TODO (3 points): Remove bugs or crashes if any -> (COMPLETED)
// TODO (1 point): Add content description to images -> (COMPLETED)
// TODO (2 points): Add tests -> (COMPLETED)
class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val user = intent.getParcelableExtra<User>(SAVED_USER_KEY)
        bindViews(binding, user)
    }

    private fun bindViews(binding: ActivityDetailsBinding, user: User?) {
        val imageUrl = user?.picture?.large ?: DEFAULT_IMAGE
        val name = user?.name ?: Name("", "")
        // TODO (1 point): Use Glide to load images -> (COMPLETED)
        Glide.with(this)
            .load(imageUrl)
            .into(binding.detailsImage)
        binding.detailsImage.contentDescription = name.toString()

        binding.detailsName.text = getString(R.string.details_name, name.first, name.last)
        binding.detailsEmail.text = getString(R.string.details_email, user?.gender)
        binding.detailsAge.text = getString(R.string.details_age, user?.dob?.age.toString())
        val coordinates = user?.location?.coordinates ?: Coordinates("0.0", "0.0")
        binding.detailsLocation.text =
            getString(R.string.details_location, coordinates.latitude, coordinates.longitude)
        binding.detailsLocationButton.setOnClickListener {
            coordinates.navigateLocation(this@DetailsActivity)
        }
    }

    private fun Coordinates.navigateLocation(context: Context) {
        val intent = Intent(context, LocationActivity::class.java)
            .putExtra(LAT_KEY, this.latitude)
            .putExtra(LONG_KEY, this.longitude)
        context.startActivity(intent)
    }

    companion object {
        const val LAT_KEY = "user-latitude-key"
        const val LONG_KEY = "user-longitude-key"
        const val DEFAULT_IMAGE = "https://picsum.photos/id/237/200/300"
    }
}