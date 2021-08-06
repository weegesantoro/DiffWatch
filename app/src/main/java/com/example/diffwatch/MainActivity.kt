package com.example.diffwatch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.diffwatch.databinding.ActivityMainBinding
import com.example.diffwatch.ui.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val model = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        binding.viewModel = model
        binding.lifecycleOwner = this
    }


}