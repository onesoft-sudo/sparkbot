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
import org.onesoftnet.spark.services.PropertyLoaderService

class App(private val token: String, val prefix: String = "-") {
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

                intents = Intents(
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

    private fun onReady(discord: Discord) {
        val (propertyLoaderService) = discord.getInjectionObjects(
            PropertyLoaderService::class
        )

        propertyLoaderService.load();
    }

    private fun configureMentionEmbed(embedBuilder: EmbedBuilder, discordContext: DiscordContext) {
        embedBuilder.author {
            name = System.getProperty("name")
        }

        embedBuilder.field {
            name = "Version"
            value = System.getProperty("version")
        }
    }
}