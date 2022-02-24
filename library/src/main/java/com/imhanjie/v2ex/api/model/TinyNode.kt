package com.imhanjie.v2ex.api.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TinyNode(
    var title: String,
    var name: String
) : Parcelable
