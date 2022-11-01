package org.onesoftnet.spark.commands;

import org.onesoftnet.spark.core.Command;
import org.onesoftnet.spark.core.CommandPayload;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

class TestCommand : Command() {
    override val name = "test";
    override val supportsSlashCommand = false;

    override fun run(payload: CommandPayload) {
        payload.messageEvent?.getMessage()?.reply("hello!")?.queue();
    }
}