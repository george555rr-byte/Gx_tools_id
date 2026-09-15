package com.gx.tools.id.data

data class AppData(
    val name: String,
    val packageName: String,
    val version: String,
    val tracker: String,
    val trackerColor: Long,
    val icon: android.graphics.drawable.Drawable? = null,
    val gaid: String = "",
    val afId: String = "",
    val installId: String = "",
    val uuid: String = "",
    val deviceId: String = "",
    val amazonId: String = "",
    val singularId: String = "",
    val adjustId: String = ""
)
