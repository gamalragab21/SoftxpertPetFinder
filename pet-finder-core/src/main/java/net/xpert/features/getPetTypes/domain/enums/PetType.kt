package net.xpert.features.getPetTypes.domain.enums

enum class PetType(
    val id: Int,
    val type: String
) {
    ALL(1, "All"),
    CAT(2, "Cat"),
    HORSE(3, "Horse"),
    BIRD(4, "Bird"),
    RABBIT(5, "Rabbit"),
    DOG(6, "Dog"),
    SMALL_FURRY(7, "Small & Furry"),
    SCALES_FINS_OTHER(8, "Scales, Fins & Other"),
    BARNYARD(9, "Barnyard");
}