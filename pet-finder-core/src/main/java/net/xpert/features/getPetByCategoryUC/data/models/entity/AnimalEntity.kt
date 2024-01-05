package net.xpert.features.getPetByCategoryUC.data.models.entity


import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("pet_table")
data class AnimalEntity(
    @Embedded(prefix = "pet_colors_") var colors: ColorsEntity,
    var age: String,
    var gender: String,
    @PrimaryKey var id: Int,
    var name: String,
    @Embedded(prefix = "pet_photos_") var photos: PhotoEntity,
    var type: String,
    var address: String,
    var size: String,
    var url: String,
)