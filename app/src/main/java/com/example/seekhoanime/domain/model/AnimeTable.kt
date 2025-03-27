package com.example.seekhoanime.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "anime_table")
data class TopRatedAnimeInfo(
    @PrimaryKey @SerializedName("mal_id")
    val malId: Long,
    val url: String,
    val images: Images,
    val trailer: Trailer,
    val approved: Boolean,
    val title: String,
    @SerializedName("title_english")
    val titleEnglish: String?,
    @SerializedName("title_japanese")
    val titleJapanese: String,
    @SerializedName("title_synonyms")
    val titleSynonyms: List<String>,
    val type: String,
    val source: String,
    val episodes: Long,
    val status: String,
    val airing: Boolean,
    val duration: String,
    val rating: String,
    val score: Double,
    @SerializedName("scored_by")
    val scoredBy: Long,
    val rank: Long,
    val popularity: Long,
    val members: Long,
    val favorites: Long,
    val synopsis: String,
    val background: String,
    val season: String?,
    val year: Long?,
    val producers: List<TrailerInfo>,
    val licensors: List<TrailerInfo>,
    val studios: List<TrailerInfo>,
    val genres: List<TrailerInfo>,
    val themes: List<TrailerInfo>,
    val demographics: List<TrailerInfo>,
)

data class AnimeTable(
    val pagination: Pagination,
    val data: List<TopRatedAnimeInfo>,
)

data class Pagination(
    @SerializedName("last_visible_page")
    val lastVisiblePage: Long,
    @SerializedName("has_next_page")
    val hasNextPage: Boolean,
    @SerializedName("current_page")
    val currentPage: Long,
    val items: Items,
)

data class Items(
    val count: Long,
    val total: Long,
    @SerializedName("per_page")
    val perPage: Long,
)

data class Images(
    val jpg: Jpg,
    val webp: Webp,
)

data class Jpg(
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("small_image_url")
    val smallImageUrl: String,
    @SerializedName("large_image_url")
    val largeImageUrl: String,
)

data class Webp(
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("small_image_url")
    val smallImageUrl: String,
    @SerializedName("large_image_url")
    val largeImageUrl: String,
)

data class Trailer(
    @SerializedName("youtube_id")
    val youtubeId: String?,
    val url: String?,
    @SerializedName("embed_url")
    val embedUrl: String?,
    val images: Images2,
)

data class Images2(
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("small_image_url")
    val smallImageUrl: String?,
    @SerializedName("medium_image_url")
    val mediumImageUrl: String?,
    @SerializedName("large_image_url")
    val largeImageUrl: String?,
    @SerializedName("maximum_image_url")
    val maximumImageUrl: String?,
)

data class Title(
    val type: String,
    val title: String,
)

data class Aired(
    val from: String,
    val to: String?,
    val prop: Prop,
    val string: String,
)

data class Prop(
    val from: From,
    val to: To,
)

data class From(
    val day: Long,
    val month: Long,
    val year: Long,
)

data class To(
    val day: Long?,
    val month: Long?,
    val year: Long?,
)

data class Broadcast(
    val day: String?,
    val time: String?,
    val timezone: String?,
    val string: String?,
)

data class TrailerInfo(
    @SerializedName("mal_id")
    val malId: Long,
    val type: String,
    val name: String,
    val url: String,
)


data class AnimeDetailsModel(
    val data: Data,
)

data class Data(
    @SerializedName("mal_id")
    val malId: Long,
    val url: String,
    val images: Images,
    val trailer: Trailer,
    val approved: Boolean,
    val titles: List<Title>,
    val title: String,
    @SerializedName("title_english")
    val titleEnglish: String,
    @SerializedName("title_japanese")
    val titleJapanese: String,
    @SerializedName("title_synonyms")
    val titleSynonyms: List<String>,
    val type: String,
    val source: String,
    val episodes: Long,
    val status: String,
    val airing: Boolean,
    val aired: Aired,
    val duration: String,
    val rating: String,
    val score: Double,
    @SerializedName("scored_by")
    val scoredBy: Long,
    val rank: Long,
    val popularity: Long,
    val members: Long,
    val favorites: Long,
    val synopsis: String,
    val background: String,
    val season: String,
    val year: Long,
    val broadcast: Broadcast,
    val producers: List<TrailerInfo>,
    val licensors: List<TrailerInfo>,
    val studios: List<TrailerInfo>,
    val genres: List<TrailerInfo>,
    val themes: List<Any?>,
    val demographics: List<TrailerInfo>,
)
