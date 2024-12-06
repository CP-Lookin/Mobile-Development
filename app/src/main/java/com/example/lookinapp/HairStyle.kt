package com.example.lookinapp

import android.content.Context
import com.google.gson.Gson
import java.io.InputStreamReader

data class HairStyle(
    val faceShape: String,
    val gender: String,
    val image: Map<String, String>,
    val nameHairStyle: Map<String, String>
)

data class HairStyleResponse(
    val faces: List<HairStyle>
)

fun getHairStyleData(context: Context): List<HairStyle>? {
    val raw = context.resources.openRawResource(R.raw.hair_style)
    val reader = InputStreamReader(raw)
    val response = Gson().fromJson(reader, HairStyleResponse::class.java)
    return response.faces
}