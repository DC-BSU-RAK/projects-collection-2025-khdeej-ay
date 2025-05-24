package com.example.flavournote

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.*

class DirectionsActivity : AppCompatActivity() {

    private val directions = mutableListOf<Direction>()
    private lateinit var adapter: DirectionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_directions)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.directions)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val directionInput = findViewById<EditText>(R.id.direction_input)
        val addButton = findViewById<ImageButton>(R.id.add_button)
        val recyclerView = findViewById<RecyclerView>(R.id.directions_list)
        val saveButton = findViewById<Button>(R.id.save_button)

        adapter = DirectionAdapter(directions)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        addButton.setOnClickListener {
            val step = directionInput.text.toString().trim()
            if (step.isEmpty()) {
                Toast.makeText(this, "Please enter a direction step", Toast.LENGTH_SHORT).show()
            } else {
                directions.add(Direction(step))
                adapter.notifyItemInserted(directions.size - 1)
                directionInput.text.clear()
            }
        }

        saveButton.setOnClickListener {
            if (directions.isEmpty()) {
                Toast.makeText(this, "Please add at least one direction", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Retrieve previous data
            val title = intent.getStringExtra("title")
            val category = intent.getStringExtra("category")
            val prepTime = intent.getStringExtra("prepTime")
            val cookTime = intent.getStringExtra("cookTime")
            val servings = intent.getStringExtra("servings")
            val ingredients = intent.getSerializableExtra("ingredientsList") as? ArrayList<Ingredient>

            // Send everything to CardActivity
            val intent = Intent(this, CardActivity::class.java).apply {
                putExtra("title", title)
                putExtra("category", category)
                putExtra("prepTime", prepTime)
                putExtra("cookTime", cookTime)
                putExtra("servings", servings)
                putExtra("ingredientsList", ingredients)
                putExtra("directionsList", ArrayList(directions))
            }
            startActivity(intent)
        }
    }
}