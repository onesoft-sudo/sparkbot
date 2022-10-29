package org.onesoftnet.spark.utils

import kotlinx.serialization.Serializable

@Serializable
data class Config(
    val prefix: String,
    val channel: String
)