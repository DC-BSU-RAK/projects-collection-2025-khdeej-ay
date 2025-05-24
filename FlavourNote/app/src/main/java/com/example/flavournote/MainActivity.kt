package com.example.flavournote

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.home_screen)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val createButton = findViewById<ImageButton>(R.id.create_button)
        val savedButton = findViewById<ImageButton>(R.id.saved_button)
        val infoButton = findViewById<ImageButton>(R.id.info_button)
        val prefButton = findViewById<ImageButton>(R.id.preferences_button)

        createButton.setOnClickListener {
            val intent = Intent(this, DetailsActivity::class.java)
            startActivity(intent)
        }

        savedButton.setOnClickListener {
            val intent = Intent(this, CardActivity::class.java)
            startActivity(intent)
        }

        infoButton.setOnClickListener {
            showInstructionsPopup()
        }

        prefButton.setOnClickListener {
            showPreferencesPopup()
        }
    }

    private fun showInstructionsPopup() {
        val dialogView = layoutInflater.inflate(R.layout.activity_instructions, null)
        val dialog = AlertDialog.Builder(this, R.style.CustomDialog)
            .setView(dialogView)
            .setCancelable(true)
            .create()

        val closeButton = dialogView.findViewById<ImageButton>(R.id.close_button)
        closeButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()
    }

    private fun showPreferencesPopup() {
        val dialogView = layoutInflater.inflate(R.layout.activity_preferences, null)
        val prefs = getSharedPreferences("user_prefs", MODE_PRIVATE)

        val editServings = dialogView.findViewById<EditText>(R.id.edit_servings)
        val spinnerCategory = dialogView.findViewById<Spinner>(R.id.spinner_category)
        val closeButton = dialogView.findViewById<ImageButton>(R.id.close_button)
        val saveButton = dialogView.findViewById<ImageButton>(R.id.save_button)

        // Populate the spinner
        val categories = listOf("Breakfast", "Lunch", "Dinner", "Snack", "Dessert", "Drink")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categories)
        spinnerCategory.adapter = adapter

        // Set saved values
        val savedServings = prefs.getInt("default_servings", 4)
        val savedCategory = prefs.getString("default_category", "Dinner")

        editServings.setText(savedServings.toString())
        spinnerCategory.setSelection(categories.indexOf(savedCategory))

        // Setup dialog
        val dialog = AlertDialog.Builder(this, R.style.CustomDialog)
            .setView(dialogView)
            .setCancelable(true)
            .create()

        closeButton.setOnClickListener {
            dialog.dismiss()
        }

        saveButton.setOnClickListener {
            val newServings = editServings.text.toString().toIntOrNull() ?: 4
            val newCategory = spinnerCategory.selectedItem.toString()

            prefs.edit()
                .putInt("default_servings", newServings)
                .putString("default_category", newCategory)
                .apply()

            Toast.makeText(this, "Preferences saved!", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()
    }

}