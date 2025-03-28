package com.example.seekhoanime.ui.animedetails

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.seekhoanime.AnimeUiEvents
import com.example.seekhoanime.R
import com.example.seekhoanime.databinding.ActivityAnimeDetailPageBinding
import com.example.seekhoanime.utils.ANIME_ID
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

private const val TAG = "AnimeDetailPageActivity"

@AndroidEntryPoint
class AnimeDetailPageActivity : AppCompatActivity() {
    private var _binding: ActivityAnimeDetailPageBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AnimeDetailsViewModel by viewModels()
    private var player: YouTubePlayer? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_anime_detail_page)

        binding.lifecycleOwner = this

        val animeId = intent.getIntExtra(ANIME_ID, -1)
        if (animeId != -1) {
            viewModel.getAnimeDetail(animeId)
        }

        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    is AnimeUiEvents.Loading -> binding.progressBar.visibility = View.VISIBLE
                    is AnimeUiEvents.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val anime = state.data
                        Log.i(TAG, "Success: $anime")
                        binding.anime = anime.data
                        if (anime.data.trailer.youtubeId?.isNotEmpty() == true) {
                            setupYouTubePlayer(anime.data.trailer.youtubeId)
                        } else {
                            binding.youtubePlayerView.visibility = View.INVISIBLE
                            binding.ivImgPoster.visibility = View.VISIBLE
                            Glide.with(this@AnimeDetailPageActivity)
                                .load(anime.data.images.jpg.imageUrl)
                                .into(binding.ivImgPoster)
                        }
                    }

                    is AnimeUiEvents.Error -> {
                        Log.i(TAG, "Error: ${state.message}")
                        binding.progressBar.visibility = View.GONE
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

                override fun onError(
                    youTubePlayer: YouTubePlayer,
                    error: PlayerConstants.PlayerError
                ) {
                    super.onError(youTubePlayer, error)
                    Log.e(TAG, error.toString())
                }
            })
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        binding.youtubePlayerView.release()
        _binding = null
    }

}