package com.mustafa.newsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.mustafa.newsapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.bottomNavigationView.setupWithNavController(findNavController())


    }

    private fun findNavController(): NavController {
        val navHostFragment =
            (this as AppCompatActivity).supportFragmentManager.findFragmentById(R.id.newsNavHostFragment) as NavHostFragment
        return navHostFragment.navController
    }

}