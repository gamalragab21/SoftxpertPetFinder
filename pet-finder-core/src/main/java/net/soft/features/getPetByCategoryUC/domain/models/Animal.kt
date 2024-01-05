package net.soft.features.getPetByCategoryUC.domain.models


import android.os.Parcelable
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
data class Animal(
    var colors: Colors,
    var age: String,
    var gender: String,
    @PrimaryKey var id: Int,
    var name: String,
    var photos: Photo,
    var type: String,
    var address: String,
    var size: String,
    var url: String,
) : Parcelable