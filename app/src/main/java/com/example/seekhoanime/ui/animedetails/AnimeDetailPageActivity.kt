package com.example.seekhoanime.ui.animedetails

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.seekhoanime.AnimeUiEvents
import com.example.seekhoanime.databinding.ActivityAnimeDetailPageBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AnimeDetailPageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAnimeDetailPageBinding
    private val viewModel: AnimeDetailsViewModel by viewModels()
    private var player: YouTubePlayer? = null

    @SuppressLint("SetJavaScriptEnabled", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimeDetailPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val animeId = intent.getIntExtra("anime_id", -1)
        if (animeId != -1) {
            viewModel.getAnimeDetail(animeId)
        }

        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    is AnimeUiEvents.Loading -> {
                        Log.i("TAG", "Loading: " + "Loading")
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is AnimeUiEvents.Success -> {
                        binding.apply {
                            val anime = state.data
                            Log.i("TAG", "Success: $anime")
                            binding.progressBar.visibility = View.GONE
                            tvTitle.text = anime.data.title
                            tvEpisodes.text = "Episodes: ${anime.data.episodes}"
                            tvRating.text = "Rating: ${anime.data.score}"
                            tvSynopsis.text = anime.data.synopsis

                            if (anime.data.trailer.youtubeId?.isNotEmpty() == true) {
                                setupYouTubePlayer(anime.data.trailer.youtubeId)
                            } else {
                                youtubePlayerView.visibility = View.GONE
                                ivImgPoster.visibility = View.VISIBLE
                                Glide.with(this@AnimeDetailPageActivity)
                                    .load(anime.data.images.jpg.imageUrl)
                                    .into(ivImgPoster)
                            }
                        }
                    }

                    is AnimeUiEvents.Error -> {
                        Log.i("TAG", "Error: ${state.message}")
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(
                            this@AnimeDetailPageActivity,
                            state.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun setupYouTubePlayer(youtubeId: String) {
        binding.youtubePlayerView.apply {
            addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    player = youTubePlayer
                    youTubePlayer.loadVideo(youtubeId, 0f)

                }

                override fun onError(youTubePlayer: YouTubePlayer, error: PlayerConstants.PlayerError) {
                    super.onError(youTubePlayer, error)
                    Log.e("TAG", error.toString())
                }
            })
        }

    }
}