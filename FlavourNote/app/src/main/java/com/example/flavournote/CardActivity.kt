package com.example.flavournote

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.*

class CardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_card)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.recipe_card)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val title = intent.getStringExtra("title") ?: "Untitled"
        val category = intent.getStringExtra("category") ?: "Unknown"
        val prepTime = intent.getStringExtra("prepTime") ?: "-"
        val cookTime = intent.getStringExtra("cookTime") ?: "-"
        val servings = intent.getStringExtra("servings") ?: "-"
        val ingredients = intent.getSerializableExtra("ingredientsList") as? ArrayList<Ingredient>
        val directions = intent.getSerializableExtra("directionsList") as? ArrayList<Direction>

        findViewById<TextView>(R.id.text_title).text = title
        findViewById<TextView>(R.id.text_category).text = "$category"
        findViewById<TextView>(R.id.prep_time).text = "$prepTime minutes"
        findViewById<TextView>(R.id.cook_time).text = "$cookTime minutes"
        findViewById<TextView>(R.id.text_servings).text = "$servings"

        val ingredientsText = ingredients?.joinToString("\n") { "- ${it.quantity} ${it.name}" } ?: "None"
        val directionsText = directions?.mapIndexed { index, step -> "${index + 1}. ${step.step}" }?.joinToString("\n") ?: "None"

        findViewById<TextView>(R.id.text_ingredients).text = "$ingredientsText\n"
        findViewById<TextView>(R.id.text_directions).text = "$directionsText\n"

        // ✅ Home button listener
        findViewById<ImageButton>(R.id.home_button).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish() // Optional: removes CardActivity from back stack
        }
    }
}
