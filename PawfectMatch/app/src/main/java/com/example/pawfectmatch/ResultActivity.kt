package com.example.pawfectmatch

// Import necessary Android libraries
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

// Activity that displays the result (e.g., matched pet) to the user
class ResultActivity : AppCompatActivity() {

    // Called when the activity is created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enables edge-to-edge layout (fullscreen layout support)
        enableEdgeToEdge()

        // Sets the layout to be used for this screen
        setContentView(R.layout.activity_result)

        // Adjusts padding to avoid overlapping with system UI (e.g., status bar, navigation bar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Reference to the ImageView that will show the pet image
        val imageView: ImageView = findViewById(R.id.pet_poster)

        // Retrieve the matched image resource passed via Intent, defaulting to 'tortoise' if none is provided
        val matchImage = intent.getIntExtra("MATCH_IMAGE", R.drawable.tortoise)

        // Set the retrieved image resource to the ImageView
        imageView.setImageResource(matchImage)
    }
}

