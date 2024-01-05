package net.xpert.features.getPetByCategoryUC.domain.models


import androidx.room.PrimaryKey
import net.xpert.core.common.domain.model.BaseDomain

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
) : BaseDomain()