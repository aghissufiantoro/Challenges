package com.example.challenges.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MenuMakanan(
    val nama : String,
    val harga : String,
    val photo : Int
) : Parcelable
