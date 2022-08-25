/*
 * Copyright (c) OSN Inc. 2022
 */

package org.onesoftnet.spark.dataclasses

import kotlinx.serialization.Serializable
import me.jakejmattson.discordkt.dsl.Data

@Serializable
data class GuildConfig(
    val guild: ULong,
    val prefix: String,
    val channel: ULong
) : Data()

@Serializable
data class Config(val guildConfigurations: MutableMap<ULong, GuildConfig> = mutableMapOf()) : Data() {
    operator fun get(id: ULong?) = guildConfigurations[id]

    operator fun set(id: ULong, configuration: GuildConfig) {
        guildConfigurations[id] = configuration
        save()
    }

    fun has(guildId: ULong) = guildConfigurations.containsKey(guildId)
}
