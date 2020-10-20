package eg.mahmoudshawky.theAir.data.models.entity

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.Entity
import eg.mahmoudshawky.theAir.utils.SimpleImageText
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
@Entity(primaryKeys = [("id")])
data class TvShow(
    val backdrop_path: String,
    val first_air_date: String,
    val genre_ids: List<Int>,
    val id: Int,
    val name: String,
    val origin_country: List<String>,
    val original_language: String,
    val original_name: String,
    val overview: String,
    val popularity: Float,
    val poster_path: String?,
    val vote_average: Float,
    val vote_count: Int
) : Parcelable, SimpleImageText {
    override fun getText() = name
    override fun getImagePath() = poster_path
}