package com.example.pawfectmatch

// Import necessary Android and UI libraries
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

// Activity where the user selects their living situation and hobby to get a pet match
class SelectionsActivity : AppCompatActivity() {

    // Variables to store the user's selections
    private var selectedLiving: String? = null
    private var selectedHobby: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enable edge-to-edge layout for fullscreen appearance
        enableEdgeToEdge()
        setContentView(R.layout.activity_selections)

        // Ensure padding is added for system UI (status/navigation bars)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.selection)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Find buttons and UI elements by ID
        val houseBtn = findViewById<ImageButton>(R.id.house_button)
        val apartmentBtn = findViewById<ImageButton>(R.id.apartment_button)
        val sharingBtn = findViewById<ImageButton>(R.id.sharing_button)
        val readingBtn = findViewById<ImageButton>(R.id.reading_button)
        val paintingBtn = findViewById<ImageButton>(R.id.painting_button)
        val hikingBtn = findViewById<ImageButton>(R.id.hiking_button)
        val generateBtn = findViewById<Button>(R.id.generate_button)
        val userChoicesText = findViewById<TextView>(R.id.chosen_text)

        // ----- Living Situation Selection -----
        val livingButtons = listOf(houseBtn, apartmentBtn, sharingBtn)

        houseBtn.setOnClickListener {
            selectedLiving = "House"
            updateChosenText(userChoicesText)
            highlightSelectedButton(houseBtn, livingButtons)
        }

        apartmentBtn.setOnClickListener {
            selectedLiving = "Apartment"
            updateChosenText(userChoicesText)
            highlightSelectedButton(apartmentBtn, livingButtons)
        }

        sharingBtn.setOnClickListener {
            selectedLiving = "Shared Housing"
            updateChosenText(userChoicesText)
            highlightSelectedButton(sharingBtn, livingButtons)
        }

        // ----- Hobby Selection -----
        val hobbyButtons = listOf(readingBtn, paintingBtn, hikingBtn)

        readingBtn.setOnClickListener {
            selectedHobby = "Reading"
            updateChosenText(userChoicesText)
            highlightSelectedButton(readingBtn, hobbyButtons)
        }

        paintingBtn.setOnClickListener {
            selectedHobby = "Painting"
            updateChosenText(userChoicesText)
            highlightSelectedButton(paintingBtn, hobbyButtons)
        }

        hikingBtn.setOnClickListener {
            selectedHobby = "Hiking"
            updateChosenText(userChoicesText)
            highlightSelectedButton(hikingBtn, hobbyButtons)
        }

        // ----- Match Button Logic -----
        generateBtn.setOnClickListener {
            // Ensure both selections are made
            if (selectedLiving == null) {
                Toast.makeText(this, "Please choose a living situation!", Toast.LENGTH_SHORT).show()
            } else if (selectedHobby == null) {
                Toast.makeText(this, "Please choose a hobby!", Toast.LENGTH_SHORT).show()
            } else {
                // Mapping of selections to pet images
                val matchMap = mapOf(
                    "House" to mapOf(
                        "Reading" to R.drawable.calico_cat,
                        "Painting" to R.drawable.rabbit,
                        "Hiking" to R.drawable.golden_retriever
                    ),
                    "Apartment" to mapOf(
                        "Reading" to R.drawable.tortoise,
                        "Painting" to R.drawable.hedgehog,
                        "Hiking" to R.drawable.cocker_spaniel
                    ),
                    "Shared Housing" to mapOf(
                        "Reading" to R.drawable.fish,
                        "Painting" to R.drawable.guinea_pig,
                        "Hiking" to R.drawable.parrot
                    )
                )

                // Get the image for the selected combination, fallback to default if missing
                val matchedImage = matchMap[selectedLiving]?.get(selectedHobby) ?: R.drawable.tortoise

                // Show the matched pet in a popup dialog
                showResultPopup(matchedImage)
            }
        }
    }

    // Updates the UI text to show the user's current selections
    private fun updateChosenText(textView: TextView) {
        val living = selectedLiving
        val hobby = selectedHobby

        val chosenText = when {
            living != null && hobby != null -> "You chose: $living and $hobby"
            living != null -> "You chose: $living"
            hobby != null -> "You chose: $hobby"
            else -> ""
        }

        textView.text = chosenText
    }

    // Displays a custom dialog with the matched pet image
    private fun showResultPopup(matchImageResId: Int) {
        // Inflate the result layout to use as the dialog content
        val dialogView = layoutInflater.inflate(R.layout.activity_result, null)

        // Set the matched image in the ImageView
        val imageView = dialogView.findViewById<ImageView>(R.id.pet_poster)
        imageView.setImageResource(matchImageResId)

        // Create and show the AlertDialog
        val dialog = AlertDialog.Builder(this, R.style.CustomDialog)
            .setView(dialogView)
            .setCancelable(true) // Allow user to dismiss the dialog
            .create()

        // Set up close button inside the dialog
        val closeButton = dialogView.findViewById<ImageButton>(R.id.close_button)
        closeButton.setOnClickListener {
            dialog.dismiss()
        }

        // Set transparent background for custom appearance
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()
    }

    // Highlights the selected button by changing its alpha, dims others
    private fun highlightSelectedButton(selected: ImageButton, group: List<ImageButton>) {
        group.forEach {
            it.alpha = if (it == selected) 1.0f else 0.5f
        }
    }
}

