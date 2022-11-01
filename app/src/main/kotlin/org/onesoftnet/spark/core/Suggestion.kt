package org.onesoftnet.spark.core;

import kotlinx.serialization.Serializable;

@Serializable
data class Suggestion(
    val content: String,
    val user_id: String
);