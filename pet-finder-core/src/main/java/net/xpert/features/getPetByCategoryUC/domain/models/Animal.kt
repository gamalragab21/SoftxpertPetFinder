package net.xpert.features.getPetByCategoryUC.domain.models


import androidx.room.PrimaryKey
import net.xpert.core.common.domain.model.BaseDomain

data class Animal(
    var colors: Colors,
    var age: String,
    var gender: String,
    @PrimaryKey var id: Int,
    var name: String,
    var photos: List<Photo>,
    var type: String,
    var url: String,
) : BaseDomain() {
    fun getMediumPhotoUrl() = if (photos.isNotEmpty()) photos.first().medium else ""
    fun getSmallPhotoUrl() = if (photos.isNotEmpty()) photos.first().small else ""
}