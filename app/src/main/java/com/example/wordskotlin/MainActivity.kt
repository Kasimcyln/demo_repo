package com.example.wordskotlin

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wordskotlin.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: WordViewModel by viewModels {
        WordViewModelFactory(WordRepositoryImpl(), PreferencesHelper(this))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = WordAdapter(viewModel::currentPage, viewModel.pageSize, PreferencesHelper(this))
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        viewModel.words.observe(this) { words ->
            adapter.submitList(words)
        }

        binding.btnNext.setOnClickListener {
            lifecycleScope.launch {
                viewModel.nextPage()
            }
        }

        binding.btnBack.setOnClickListener {
            lifecycleScope.launch {
                viewModel.previousPage()
            }
        }
    }
}
