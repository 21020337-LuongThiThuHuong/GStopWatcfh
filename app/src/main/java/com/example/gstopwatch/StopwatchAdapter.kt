package com.example.gstopwatch

import Stopwatch
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gstopwatch.databinding.ItemBinding

class StopwatchAdapter(private val viewModel: StopwatchViewModel) :
    RecyclerView.Adapter<StopwatchAdapter.StopwatchViewHolder>() {

    inner class StopwatchViewHolder(val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(stopwatch: Stopwatch) {
            binding.clockItemTime.text = stopwatch.time
            if (stopwatch.isPlaying) {
                binding.playnresetButtons.visibility = View.GONE
                binding.pauseButton.visibility = View.VISIBLE
            } else {
                binding.playnresetButtons.visibility = View.VISIBLE
                binding.pauseButton.visibility = View.GONE
            }

            binding.playButton.setOnClickListener {
                if (!stopwatch.isPlaying) {
                    viewModel.startStopwatch(stopwatch, this@StopwatchAdapter)
                    notifyItemChanged(adapterPosition)
                }
            }

            binding.pauseButton.setOnClickListener {
                if (stopwatch.isPlaying) {
                    viewModel.pauseStopwatch(stopwatch)
                    notifyItemChanged(adapterPosition)
                }
            }

            binding.resetButton.setOnClickListener {
                viewModel.resetStopwatch(stopwatch)
                notifyItemChanged(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StopwatchViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StopwatchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StopwatchViewHolder, position: Int) {
        holder.bind(viewModel.stopwatches[position])
    }

    override fun getItemCount() = viewModel.stopwatches.size
}
