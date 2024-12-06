package com.example.lookinapp.api

import com.google.gson.annotations.SerializedName

data class PredictionResponse(
    val status: String,
    val message: String,
    val data: PredictionData
)

data class PredictionData(
    val id: String,

    @SerializedName("face shape result")
    val faceShapeResult: String,

    @SerializedName("gender result")
    val genderResult: String,

    @SerializedName("confidence score (face shape)")
    val confidenceScore: Double,

    val createdAt: String
)
