/*
 * Copyright (c) OSN Inc. 2022
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

class App(private val token: String, val prefix: String = "-") {
    var discord: Discord? = null;

    @KordPreview
    @PrivilegedIntent
    fun run() {
        bot(token) {
            prefix {
                prefix
            }

            configure {
                mentionAsPrefix = true
                commandReaction = null
                entitySupplyStrategy = EntitySupplyStrategy.cacheWithRestFallback
                deleteInvocation = false
                recommendCommands = false

                intents = Intents(*(this@App.registerIntents()))
            }

            mentionEmbed() {
                configureMentionEmbed(this, it);
            }

            onException {
                println(exception.message)
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
        this.discord = discord;
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