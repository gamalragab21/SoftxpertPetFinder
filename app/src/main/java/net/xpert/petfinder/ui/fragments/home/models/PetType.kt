package net.xpert.petfinder.ui.fragments.home.models

data class PetType(
    val id: Int,
    val type: String
) {
    companion object {
        fun getAllTypes() = listOf(
            PetType(1, "All"),
            PetType(2, "Cat"),
            PetType(3, "Horse"),
            PetType(4, "Bird"),
            PetType(5, "Rabbit"),
            PetType(6, "Dog"),
            PetType(7, "Small & Furry"),
            PetType(8, "Scales, Fins & Other"),
            PetType(9, "Barnyard"),
        )
    }
}