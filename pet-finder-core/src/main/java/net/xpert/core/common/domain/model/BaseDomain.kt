package net.xpert.core.common.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
open class BaseDomain(
    var message: String = "",
    var codeDto: Int = -1
) : Parcelable