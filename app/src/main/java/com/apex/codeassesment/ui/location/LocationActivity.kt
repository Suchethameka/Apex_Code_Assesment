    package com.apex.codeassesment.ui.location

    import android.Manifest
    import android.content.pm.PackageManager
    import android.os.Bundle
    import android.widget.Toast
    import androidx.appcompat.app.AppCompatActivity
    import androidx.core.app.ActivityCompat
    import com.apex.codeassesment.R
    import com.apex.codeassesment.databinding.ActivityLocationBinding
    import com.apex.codeassesment.ui.details.DetailsActivity.Companion.LAT_KEY
    import com.apex.codeassesment.ui.details.DetailsActivity.Companion.LONG_KEY
    import com.google.android.gms.location.FusedLocationProviderClient
    import com.google.android.gms.location.LocationServices


    // TODO (Optional Bonus 8 points): Calculate distance between 2 coordinates using phone's location (COMPLETED)
    class LocationActivity : AppCompatActivity() {

        private lateinit var fusedLocationClient: FusedLocationProviderClient

        private var lastKnownLatitude: Double? = null
        private var lastKnownLongitude: Double? = null

        private var latitudeRandomUser: String? = null
        private var longitudeRandomUser: String? = null
        private lateinit var binding: ActivityLocationBinding

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityLocationBinding.inflate(layoutInflater)
            setContentView(binding.root)
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            latitudeRandomUser = intent.getStringExtra(LAT_KEY)
            longitudeRandomUser = intent.getStringExtra(LONG_KEY)

            binding.locationCalculateButton.setOnClickListener {
                requestLocationPermission(binding)
                calculateDistance(binding)
            }
            binding.locationRandomUser.text =
                getString(R.string.location_random_user, latitudeRandomUser, longitudeRandomUser)
        }

        private fun calculateDistance(binding: ActivityLocationBinding) {
            binding.locationPhone.text = getString(
                R.string.location_phone,
                lastKnownLatitude.toString(),
                lastKnownLongitude.toString()
            )
        }

        private fun requestLocationPermission(binding: ActivityLocationBinding) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ),
                    REQUEST_CODE
                )
            } else {
                // This doesn't gaurante last location
               val lastLocation =  fusedLocationClient.lastLocation
                // Permission already granted, proceed with location-related operations.
                lastLocation.addOnSuccessListener { location ->
                        location?.let {
                            lastKnownLatitude = location.latitude
                            lastKnownLongitude = location.longitude

                            val distance = latitudeRandomUser?.toDouble()?.let { latitudeRandomUser ->
                                longitudeRandomUser?.toDouble()?.let { longitudeRandomUser ->
                                    DistanceHelper().distance(
                                        lastKnownLatitude ?: 0.0,
                                        lastKnownLongitude ?: 0.0,
                                        latitudeRandomUser,
                                        longitudeRandomUser,
                                        'K'
                                    )
                                }
                            }
                            updateDistanceUI(distance, binding)
                        } ?: run {
                            Toast.makeText(this, "Failed to retrieve location", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
            }
        }

        private fun updateDistanceUI(distance: Double?, binding: ActivityLocationBinding) {
            distance?.let {
                Toast.makeText(
                    this,
                    "Distance between Random User and Current device is $it Kms",
                    Toast.LENGTH_SHORT
                ).show()
                            binding.locationDistance.text = getString(R.string.location_result_miles,it.toFloat())
            } ?: run {
                Toast.makeText(this, "Distance calculation failed", Toast.LENGTH_SHORT).show()
            }

        }

        override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
        ) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)

            if (requestCode == REQUEST_CODE) {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    requestLocationPermission(binding)
                } else {
                    Toast.makeText(this, "Location Permission Denied", Toast.LENGTH_SHORT).show()
                }
            }
        }


        companion object {
            const val REQUEST_CODE = 100
        }
    }
