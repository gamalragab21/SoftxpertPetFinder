package net.xpert.features.token

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("dd")
data class Token(
    @PrimaryKey(autoGenerate = true) val id: Long
)