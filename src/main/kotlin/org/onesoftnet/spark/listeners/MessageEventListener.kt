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

package org.onesoftnet.spark.listeners

import dev.kord.core.behavior.channel.createMessage
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.rest.builder.message.create.embed
import kotlinx.datetime.Clock
import me.jakejmattson.discordkt.dsl.listeners
import me.jakejmattson.discordkt.extensions.pfpUrl
import org.onesoftnet.spark.App
import org.onesoftnet.spark.services.Database
import com.mongodb.client.model.Sorts.descending
import dev.kord.common.entity.Snowflake
import dev.kord.core.behavior.reply
import dev.kord.core.cache.data.EmojiData
import dev.kord.core.entity.GuildEmoji
import dev.kord.core.entity.Message
import dev.kord.core.entity.ReactionEmoji
import dev.kord.x.emoji.Emojis
import dev.kord.x.emoji.from
import org.bson.Document

suspend fun addIssue(message: Message, database: Database, guildId: Snowflake?) {
    message.addReaction(ReactionEmoji.Unicode("⏰"))

    val col = database.db.getCollection("issues");
    val prevIssue = col.find().sort(descending("createdAt")).limit(1).first()
    val issueNumber = (prevIssue?.getInteger("issueNumber") ?: 0) + 1

    val doc = Document("issueNumber", issueNumber)
        .append("guild", guildId!!.value.toString())
        .append("channel", message.channelId.value.toString())
        .append("user", message.author!!.id.value.toString())
        .append("message", message.id.value.toString())
        .append("status", 0)
        .append("createdAt", Clock.System.now().toString())
        .append("updatedAt", Clock.System.now().toString())

    col.insertOne(doc)

    message.channel.createMessage {
        embed {
            title = "Issue #$issueNumber"

            author {
                name = message.author?.tag
                icon = message.author?.pfpUrl
            }

            description = message.content

            footer {
                text = "Status: Pending Review"
            }

            timestamp = Clock.System.now()
        }
    }

    message.delete("Cleanup original message after saving it to the database")
}

@Suppress("unused")
fun messageEventListener(database: Database) = listeners {
    on<MessageCreateEvent> {
        if (message.author?.isBot == true || guildId == null) {
            return@on
        }

        val config = App.app.config[guildId?.value];

        if (config === null) {
            println("Config not found for guild: $guildId")
            return@on
        }

        if (message.content.trim() == "<@${App.app.discord!!.kord.selfId.value}>" || message.content.trim() == "<@!${App.app.discord!!.kord.selfId.value}>") {
            return@on
        }

        if (config.channel == message.channelId.value) {
            addIssue(message, database, guildId);
            return@on
        }
    }
}
