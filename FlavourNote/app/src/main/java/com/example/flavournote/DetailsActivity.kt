package com.example.flavournote

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.content.ContextCompat
import android.content.Intent
import android.widget.*

class DetailsActivity : AppCompatActivity() {

    private var selectedCategory: String? = null
    private lateinit var categoryButtons: List<Button>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_details)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.recipe_details)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val title = findViewById<EditText>(R.id.editTitle)
        val servings = findViewById<EditText>(R.id.editServings)
        val prepTime = findViewById<EditText>(R.id.editPrepTime)
        val cookTime = findViewById<EditText>(R.id.editCookTime)
        val nextButton = findViewById<Button>(R.id.next_button)

        // Load preferences
        val prefs = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val defaultServings = prefs.getInt("default_servings", 0)
        val defaultCategory = prefs.getString("default_category", null)

        if (defaultServings > 0) {
            servings.setText(defaultServings.toString())
        }

        categoryButtons = listOf(
            findViewById(R.id.btnBreakfast),
            findViewById(R.id.btnLunch),
            findViewById(R.id.btnDinner),
            findViewById(R.id.btnSnack),
            findViewById(R.id.btnDessert),
            findViewById(R.id.btnDrink)
        )

        if (!defaultCategory.isNullOrEmpty()) {
            categoryButtons.firstOrNull { it.text.toString().equals(defaultCategory, ignoreCase = true) }?.let { button ->
                selectCategory(button)
            }
        }

        categoryButtons.forEach { button ->
            button.setOnClickListener {
                selectCategory(button)
            }
        }
        nextButton.setOnClickListener {
            val title = title.text.toString().trim()
            val servings = servings.text.toString().trim()
            val prepTime = prepTime.text.toString().trim()
            val cookTime = cookTime.text.toString().trim()

            if (title.isEmpty() || selectedCategory == null || servings.isEmpty() || prepTime.isEmpty() || cookTime.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, IngredientsActivity::class.java).apply {
                    putExtra("title", title)
                    putExtra("category", selectedCategory)
                    putExtra("servings", servings)
                    putExtra("prepTime", prepTime)
                    putExtra("cookTime", cookTime)
                }
                startActivity(intent)
            }
        }
    }

    private fun selectCategory(selectedButton: Button) {
        categoryButtons.forEach { button ->
            button.setBackgroundColor(ContextCompat.getColor(this, R.color.beige))
        }

        selectedButton.setBackgroundColor(ContextCompat.getColor(this, R.color.brown)) // Custom color from palette
        selectedCategory = selectedButton.text.toString()
    }
}