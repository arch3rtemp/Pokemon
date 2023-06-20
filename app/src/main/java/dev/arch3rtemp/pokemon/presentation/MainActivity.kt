package dev.arch3rtemp.pokemon.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentContainerView
import dagger.hilt.android.AndroidEntryPoint
import dev.arch3rtemp.pokemon.R
import dev.arch3rtemp.pokemon.presentation.home.HomeFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var container: FragmentContainerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        container = findViewById(R.id.fragmentContainer)
        val homeFragment = HomeFragment.newInstance()

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, homeFragment)
            .commit()

    }
}