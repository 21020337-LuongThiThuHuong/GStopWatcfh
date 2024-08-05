package com.example.gstopwatch

import Stopwatch
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StopwatchViewModel : ViewModel() {
    val stopwatches = mutableListOf<Stopwatch>()

    fun addStopwatch() {
        stopwatches.add(Stopwatch())
    }

    fun startStopwatch(stopwatch: Stopwatch, adapter: StopwatchAdapter) {
        if (stopwatch.isPlaying) return

        stopwatch.isPlaying = true
        stopwatch.job = viewModelScope.launch(Dispatchers.Main) {
            while (stopwatch.isPlaying) {
                delay(10)
                val timeParts = stopwatch.time.split(":", ".")
                var minutes = timeParts[0].toInt()
                var seconds = timeParts[1].toInt()
                var milliseconds = timeParts[2].toInt()

                milliseconds++
                if (milliseconds == 100) {
                    milliseconds = 0
                    seconds++
                }
                if (seconds == 60) {
                    seconds = 0
                    minutes++
                }

                stopwatch.time = String.format("%02d:%02d.%02d", minutes, seconds, milliseconds)
                // Notify the adapter to update the UI for the running stopwatch
                adapter.notifyItemChanged(stopwatches.indexOf(stopwatch))
            }
        }
    }

    fun pauseStopwatch(stopwatch: Stopwatch) {
        stopwatch.isPlaying = false
        stopwatch.job?.cancel()
        stopwatch.job = null
    }

    fun resetStopwatch(stopwatch: Stopwatch) {
        stopwatch.isPlaying = false
        stopwatch.job?.cancel()
        stopwatch.job = null
        stopwatch.time = "00:00.00"
    }
}
