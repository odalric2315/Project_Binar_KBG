package com.project_binar.kbg.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "player")
data class Player(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int=0,

    @ColumnInfo(name = "nama")
    val nama: String?,

    @ColumnInfo(name = "username")
    val username: String?,

    @ColumnInfo(name = "password")
    val password: String?,

    @ColumnInfo(name = "win")
    val win: Int? = 0,

    @ColumnInfo(name = "lose")
    val lose: Int? = 0,

    @ColumnInfo(name = "winrate")
    val winrate: Int? = 0
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(nama)
        parcel.writeString(username)
        parcel.writeString(password)
        parcel.writeValue(win)
        parcel.writeValue(lose)
        parcel.writeValue(winrate)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Player> {
        override fun createFromParcel(parcel: Parcel): Player {
            return Player(parcel)
        }

        override fun newArray(size: Int): Array<Player?> {
            return arrayOfNulls(size)
        }
    }
}
