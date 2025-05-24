package com.example.pawfectmatch

// Import necessary Android libraries
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

// This activity displays the instructions screen in the app
class InstructionsActivity : AppCompatActivity() {

    // Called when the activity is first created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enables edge-to-edge content layout (fullscreen display support)
        enableEdgeToEdge()

        // Sets the layout for this activity to 'activity_instructions.xml'
        setContentView(R.layout.activity_instructions)

        // Adjusts padding to account for system bars (e.g., status bar, navigation bar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            // Get insets for system bars
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            // Apply padding to the view to prevent content from being obscured
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)

            // Return the insets so they can be consumed by the system
            insets
        }
    }
}
