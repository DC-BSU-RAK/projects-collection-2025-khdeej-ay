package com.example.pawfectmatch

// Import necessary Android components
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.os.Handler
import android.os.Looper

// Activity that shows a splash screen when the app starts
class SplashActivity : AppCompatActivity() {

    // Called when the activity is first created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enables edge-to-edge layout (fullscreen mode)
        enableEdgeToEdge()

        // Sets the layout to be displayed for the splash screen
        setContentView(R.layout.activity_splash)

        // Adjusts padding to accommodate system bars (status bar, navigation bar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Post a delayed task on the main thread to wait 5 seconds before moving to MainActivity
        Handler(Looper.getMainLooper()).postDelayed({
            // Start the main activity
            startActivity(Intent(this, MainActivity::class.java))

            // Finish splash activity so the user can't return to it with the back button
            finish()
        }, 5000) // 5000 milliseconds = 5 seconds
    }
}