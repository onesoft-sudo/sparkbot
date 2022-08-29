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

package org.onesoftnet.spark

import dev.kord.common.annotation.KordPreview
import dev.kord.core.supplier.EntitySupplyStrategy
import dev.kord.gateway.Intent
import dev.kord.gateway.Intents
import dev.kord.gateway.PrivilegedIntent
import dev.kord.rest.builder.message.EmbedBuilder
import me.jakejmattson.discordkt.Discord
import me.jakejmattson.discordkt.commands.DiscordContext
import me.jakejmattson.discordkt.dsl.bot
import org.onesoftnet.spark.dataclasses.Config
import org.onesoftnet.spark.services.Database
import java.awt.Color

class App(private val defaultPrefix: String = "-") {
    private lateinit var database: Database
    var discord: Discord? = null;
    private val token = System.getenv("TOKEN")
    lateinit var config: Config;

    companion object {
        lateinit var app: App
    }

    init {
        app = this;
    }

    @KordPreview
    @PrivilegedIntent
    fun run() {
        bot(token) {
            config = data("config/config.json") { Config() }

            prefix {
                guild?.let { config[this.guild?.id?.value]?.prefix } ?: defaultPrefix
            }

            configure {
                mentionAsPrefix = true
                commandReaction = null
                entitySupplyStrategy = EntitySupplyStrategy.cacheWithRestFallback
                deleteInvocation = false
                recommendCommands = false
                theme = Color(0, 123, 255)

                intents = Intents(*(this@App.registerIntents()))
            }

            mentionEmbed {
                configureMentionEmbed(this, it);
            }

            onException {
                println("Exception: " + exception.message)
            }

            onStart {
                onReady(this)
            }
        }
    }

    @PrivilegedIntent
    private fun registerIntents(): Array<Intent> {
        return arrayOf(
            Intent.Guilds,
            Intent.GuildBans,
            Intent.GuildMembers,
            Intent.MessageContent,
            Intent.GuildMessages,
            Intent.DirectMessages,
            Intent.GuildMessageReactions,
            Intent.DirectMessagesReactions
        )
    }

    private fun onReady(discord: Discord) {
        this.discord = discord
        this.database = discord.getInjectionObjects(Database::class)
    }

    private fun configureMentionEmbed(embedBuilder: EmbedBuilder, discordContext: DiscordContext) {
        embedBuilder.author {
            name = this@App.discord?.properties?.bot?.get("name")
        }

        embedBuilder.field {
            name = "Version"
            value = this@App.discord?.properties?.bot?.get("version") ?: "Unknown"
        }
    }
}
