package org.onesoftnet.spark.core;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

class CommandPayload(val messageEvent: MessageReceivedEvent? = null, val commandEvent: SlashCommandInteractionEvent? = null, val args: List<String>? = null) {
    fun isMessageCommand(): Boolean = messageEvent != null;
    fun isSlashCommand(): Boolean = commandEvent != null;
}