/*
 * Copyright (c) OSN Inc. 2022
 */

package org.onesoftnet.spark.listeners

import dev.kord.core.behavior.reply
import dev.kord.core.event.message.MessageCreateEvent
import me.jakejmattson.discordkt.dsl.listeners
import org.onesoftnet.spark.App

@Suppress("unused")
fun messageEventListener() = listeners {
    on<MessageCreateEvent> {
        if (message.author?.isBot == true) {
            return@on
        }

        val config = App.app.config[guildId?.value];

        if (config === null) {
            println("Config not found for guild: $guildId")
            return@on
        }

        if (config.channel != message.channelId.value) {
            println("This channel is not set as issue tracking channel. Channel ID: ${message.channelId}")
            return@on
        }

        message.reply {
            content = "Suggestion added!"
        }
    }
}