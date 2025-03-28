package com.example.seekhoanime.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.seekhoanime.R
import com.example.seekhoanime.domain.model.TopRatedAnimeInfo
import com.example.seekhoanime.databinding.ItemAnimeListBinding

class AnimeAdapter(
    private val onItemClick: (TopRatedAnimeInfo) -> Unit
) : ListAdapter<TopRatedAnimeInfo, AnimeAdapter.AnimeViewHolder>(AnimeDiffCallback()) {

    inner class AnimeViewHolder(private val binding: ItemAnimeListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n", "StringFormatMatches")
        fun bind(anime: TopRatedAnimeInfo) = with(binding) {
            tvTitle.text = anime.title
            tvEpisodes.text = String.format(itemView.context.getString(R.string.episodes), anime.episodes)
            tvRating.text = String.format(itemView.context.getString(R.string.rating), anime.score)

            Glide.with(root.context)
                .load(anime.images.jpg.imageUrl)
                .into(ivAnimePosterImg)

            root.setOnClickListener {
                onItemClick(anime)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        val binding = ItemAnimeListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return AnimeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class AnimeDiffCallback : DiffUtil.ItemCallback<TopRatedAnimeInfo>() {
    override fun areItemsTheSame(oldItem: TopRatedAnimeInfo, newItem: TopRatedAnimeInfo): Boolean {
        return oldItem.malId == newItem.malId
    }

    override fun areContentsTheSame(
        oldItem: TopRatedAnimeInfo,
        newItem: TopRatedAnimeInfo
    ): Boolean {
        return oldItem == newItem
    }
}
