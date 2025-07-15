package com.paykidscompose.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Token(
    val userId: String,
    val profileUrl: String,
    val idToken: String,
    val accessToken: String,
    val accessTokenExpiresAt: Date,
    val refreshToken: String,
    val refreshTokenExpiresAt: Date
): Parcelable
