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

class IngredientsActivity : AppCompatActivity() {

    private lateinit var ingredientName: EditText
    private lateinit var ingredientQuantity: EditText
    private lateinit var addButton: ImageButton
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: IngredientAdapter
    private val ingredientList = mutableListOf<Ingredient>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ingredients)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ingredients)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        ingredientName = findViewById(R.id.ingredient_name)
        ingredientQuantity = findViewById(R.id.ingredient_quantity)
        addButton = findViewById(R.id.add_button)
        recyclerView = findViewById(R.id.ingredients_list)

        adapter = IngredientAdapter(ingredientList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        addButton.setOnClickListener {
            val name = ingredientName.text.toString().trim()
            val quantity = ingredientQuantity.text.toString().trim()

            if (name.isEmpty() || quantity.isEmpty()) {
                Toast.makeText(this, "Please fill in both ingredient and quantity", Toast.LENGTH_SHORT).show()
            } else {
                ingredientList.add(Ingredient(name, quantity))
                adapter.notifyItemInserted(ingredientList.size - 1)
                ingredientName.text.clear()
                ingredientQuantity.text.clear()
            }
        }

        val nextButton: Button = findViewById(R.id.next_button)
        nextButton.setOnClickListener {
            if (ingredientList.isEmpty()) {
                Toast.makeText(this, "Please add at least one ingredient", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Get data from previous activity
            val title = intent.getStringExtra("title")
            val category = intent.getStringExtra("category")
            val prepTime = intent.getStringExtra("prepTime")
            val cookTime = intent.getStringExtra("cookTime")
            val servings = intent.getStringExtra("servings")

            // Pass everything to DirectionsActivity
            val intent = Intent(this, DirectionsActivity::class.java).apply {
                putExtra("title", title)
                putExtra("category", category)
                putExtra("prepTime", prepTime)
                putExtra("cookTime", cookTime)
                putExtra("servings", servings)
                putExtra("ingredientsList", ArrayList(ingredientList)) // must be Serializable
            }
            startActivity(intent)
        }
    }
}