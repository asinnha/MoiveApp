package com.example.myapplication.dataclasses

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "movie_results")
data class Results(

    @SerializedName("adult"             ) var adult           : Boolean?        = null,
    @SerializedName("backdrop_path"     ) var backdropPath    : String?         = null,
    @Ignore @SerializedName("genre_ids" ) var genreIds        : ArrayList<Int>? = arrayListOf(),
    @PrimaryKey @SerializedName("id"    ) var id              : Int?            = null,
    @SerializedName("original_language" ) var originalLanguage: String?         = null,
    @SerializedName("original_title"    ) var originalTitle   : String?         = null,
    @SerializedName("overview"          ) var overview        : String?         = null,
    @SerializedName("popularity"        ) var popularity      : Double?         = null,
    @SerializedName("poster_path"       ) var posterPath      : String?         = null,
    @SerializedName("release_date"      ) var releaseDate     : String?         = null,
    @SerializedName("title"             ) var title           : String?         = null,
    @SerializedName("video"             ) var video           : Boolean?        = null,
    @SerializedName("vote_average"      ) var voteAverage     : Double?         = null,
    @SerializedName("vote_count"        ) var voteCount       : Int?            = null


): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readString(),
        TODO("genreIds"),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Int::class.java.classLoader) as? Int
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(adult)
        parcel.writeString(backdropPath)
        parcel.writeValue(id)
        parcel.writeString(originalLanguage)
        parcel.writeString(originalTitle)
        parcel.writeString(overview)
        parcel.writeValue(popularity)
        parcel.writeString(posterPath)
        parcel.writeString(releaseDate)
        parcel.writeString(title)
        parcel.writeValue(video)
        parcel.writeValue(voteAverage)
        parcel.writeValue(voteCount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Results> {
        override fun createFromParcel(parcel: Parcel): Results {
            return Results(parcel)
        }

        override fun newArray(size: Int): Array<Results?> {
            return arrayOfNulls(size)
        }
    }
}
