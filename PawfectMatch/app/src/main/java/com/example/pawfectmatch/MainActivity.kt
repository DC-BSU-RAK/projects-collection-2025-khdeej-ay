package com.example.pawfectmatch

// Import necessary Android libraries
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

// MainActivity is the entry point of the app where users can start or view instructions
class MainActivity : AppCompatActivity() {

    // Variables to hold selected options (used elsewhere if needed)
    private var selectedLiving: String? = null
    private var selectedHobby: String? = null

    // Called when the activity is created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enables edge-to-edge layout (draw behind system bars)
        enableEdgeToEdge()

        // Sets the layout to be displayed for this activity
        setContentView(R.layout.activity_main)

        // Adjusts view padding to avoid overlapping with system bars (status/navigation bar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize UI elements
        val infoButton = findViewById<ImageButton>(R.id.info_button)   // Button to show instructions
        val startButton = findViewById<Button>(R.id.start_button)      // Button to begin selections

        // Show instruction popup when info button is clicked
        infoButton.setOnClickListener {
            showInstructionsPopup()
        }

        // Start the SelectionsActivity when the start button is clicked
        startButton.setOnClickListener {
            val intent = Intent(this, SelectionsActivity::class.java)
            startActivity(intent)
        }
    }

    // Displays a custom popup with instructions
    private fun showInstructionsPopup() {
        // Inflate the instructions layout for the popup content
        val dialogView = layoutInflater.inflate(R.layout.activity_instructions, null)

        // Create an alert dialog with custom style and set the view
        val dialog = AlertDialog.Builder(this, R.style.CustomDialog)
            .setView(dialogView)
            .setCancelable(false) // Prevents dialog from closing when tapped outside
            .create()

        // Set up close button to dismiss the dialog
        val closeButton = dialogView.findViewById<ImageButton>(R.id.close_button)
        closeButton.setOnClickListener {
            dialog.dismiss()
        }

        // Make the dialog background transparent
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        // Show the dialog
        dialog.show()
    }
}
