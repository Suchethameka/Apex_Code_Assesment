package com.apex.codeassesment.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apex.codeassesment.R
import com.apex.codeassesment.databinding.ActivityComposeMainBinding

class MainActivityCompose : AppCompatActivity() {

    private lateinit var binding: ActivityComposeMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityComposeMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.composeView.setContent {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ) {
                SetContentForScreen()
            }
        }
    }

    @Composable
    fun SetContentForScreen() {
        Text(
            text = "My Random User",
            modifier = Modifier.fillMaxWidth(),
            style = TextStyle.Default,
            fontSize = 36.sp
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background), // Replace with your image resource
                contentDescription = "Image",
                modifier = Modifier
                    .size(150.dp)
                    .padding(end = 16.dp) // Adjust padding as needed
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(
                        text = "Name : William",
                        style = MaterialTheme.typography.body1,
                        fontSize = 12.sp
                    )
                    Text(
                        text = "Email: William.mills@ex.com",
                        style = MaterialTheme.typography.body1,
                        fontSize = 12.sp,
                    )

                    Button(
                        onClick = {
                            // Handle button 1 click
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    ) {
                        Text(text = "refresh Random User")
                    }
                    Button(
                        onClick = {
                            // Handle button 2 click
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "View Details")
                    }

                }
            }
        }
        Button(
            onClick = {
                // Handle button 2 click
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Show 10 users")
        }

        /**
         * Followed By lazyColoum
         */

    }
}