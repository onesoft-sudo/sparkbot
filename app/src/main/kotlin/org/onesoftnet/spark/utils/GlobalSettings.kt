package org.onesoftnet.spark.utils;

import kotlinx.serialization.Serializable;

@Serializable
data class GlobalSettings(
    val homeGuild: String,
    val registerCommandsAtBoot: Boolean
);