package org.onesoftnet.spark.commands;

import org.onesoftnet.spark.Command;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

class TestCommand : Command() {
    override val name = "test";

    override fun run(event: MessageReceivedEvent, args: List<String>) {
        event.getMessage().reply("hello!").queue();
    }
}