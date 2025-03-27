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
import com.example.seekhoanime.databinding.ActivityMainBinding
import com.example.seekhoanime.utils.Utility
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: AnimeViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getAnimeFromApiAndSave()

        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        val adapter = AnimeAdapter { animeDetails ->
            if (Utility.isNetworkAvailable(this)) {
                val intent = Intent(this, AnimeDetailPageActivity::class.java)
                intent.putExtra("anime_id", animeDetails.malId.toInt())
                startActivity(intent)
            }
            else{
                Toast.makeText(this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show()
            }
        }
        binding.recyclerView.adapter = adapter

        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when(state){
                    is AnimeUiEvents.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        Log.i("TAG", "Loading: "+ "Loading")
                    }

                    is AnimeUiEvents.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val animeList = state.data
                        adapter.submitList(animeList)
                        Log.i("TAG", "Success: $animeList")
                    }

                    is AnimeUiEvents.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Log.i("TAG", "Error: ${state.message}")
                        Toast.makeText(this@MainActivity, state.message, Toast.LENGTH_SHORT).show()
                        viewModel.fetchAnimeListFromLocal()
                    }
                }
            }
        }
    }
}