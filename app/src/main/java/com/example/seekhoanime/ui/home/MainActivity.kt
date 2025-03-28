package com.example.seekhoanime.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.seekhoanime.ui.animedetails.AnimeDetailPageActivity
import com.example.seekhoanime.AnimeUiEvents
import com.example.seekhoanime.R
import com.example.seekhoanime.databinding.ActivityAnimeDetailPageBinding
import com.example.seekhoanime.databinding.ActivityMainBinding
import com.example.seekhoanime.utils.ANIME_ID
import com.example.seekhoanime.utils.Utility
import kotlinx.coroutines.launch

private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: AnimeViewModel by viewModels()
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

       getData()
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        val adapter = AnimeAdapter { animeDetails ->
            if (Utility.isNetworkAvailable(this)) {
                val intent = Intent(this, AnimeDetailPageActivity::class.java)
                intent.putExtra(ANIME_ID, animeDetails.malId.toInt())
                startActivity(intent)
            } else {
                Toast.makeText(this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT)
                    .show()
            }
        }
        binding.recyclerView.adapter = adapter
        binding.btnRetry.setOnClickListener {
           getData()
        }
        binding.swipeRefreshLayout.setOnRefreshListener {
            getData()
            binding.swipeRefreshLayout.isRefreshing = false
        }

        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    is AnimeUiEvents.Loading -> {
                        binding.btnRetry.visibility = View.GONE
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is AnimeUiEvents.Success -> {
                        binding.progressBar.visibility = View.GONE
                        if (state.data.isEmpty()){
                            binding.btnRetry.visibility = View.VISIBLE
                        }else{
                            binding.btnRetry.visibility = View.GONE
                        }
                        val animeList = state.data
                        adapter.submitList(animeList)
                        Log.i(TAG, "Success: $animeList")
                    }

                    is AnimeUiEvents.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.btnRetry.visibility = View.VISIBLE
                        Log.i(TAG, "Error: ${state.message}")
                        Toast.makeText(this@MainActivity, state.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
    private  fun getData(){
        if (Utility.isNetworkAvailable(this)) {
            viewModel.getAnimeFromApiAndSave()
        }else{
            viewModel.fetchAnimeListFromLocal()
            Toast.makeText(this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT)
                .show()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}