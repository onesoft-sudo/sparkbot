/*
* Copyright (C) 2022 OSN Inc.
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU Affero General Public License as
* published by the Free Software Foundation, either version 3 of the
* License, or (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU Affero General Public License for more details.
*
* You should have received a copy of the GNU Affero General Public License
* along with this program.  If not, see <https://www.gnu.org/licenses/>.
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
