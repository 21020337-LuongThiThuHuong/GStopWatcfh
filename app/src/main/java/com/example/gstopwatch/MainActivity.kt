package com.example.gstopwatch

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gstopwatch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: StopwatchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = StopwatchAdapter(viewModel)
        binding.list.layoutManager = LinearLayoutManager(this)
        binding.list.adapter = adapter

        binding.fab.setOnClickListener {
            viewModel.addStopwatch()
            adapter.notifyItemInserted(viewModel.stopwatches.size - 1)
            updateNoClocksTextView()
        }

        updateNoClocksTextView()
    }

    private fun updateNoClocksTextView() {
        if (viewModel.stopwatches.isEmpty()) {
            binding.noClocks.visibility = View.VISIBLE
        } else {
            binding.noClocks.visibility = View.GONE
        }
    }
}
