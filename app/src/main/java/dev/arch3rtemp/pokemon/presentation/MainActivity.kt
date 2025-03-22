package dev.arch3rtemp.pokemon.presentation

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentContainerView
import dagger.hilt.android.AndroidEntryPoint
import dev.arch3rtemp.pokemon.R
import dev.arch3rtemp.pokemon.presentation.home.HomeFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var container: FragmentContainerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        container = findViewById(R.id.fragmentContainer)

        ViewCompat.setOnApplyWindowInsetsListener(container) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
        }

        val homeFragment = HomeFragment.newInstance()

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, homeFragment)
            .commit()

    }
}