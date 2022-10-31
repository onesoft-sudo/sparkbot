package org.onesoftnet.spark.commands;

import org.onesoftnet.spark.CommandPayload;
import org.onesoftnet.spark.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

class AboutCommand : Command() {
    override val name = "about";
    override val description = "About the bot";
    override val metaData = Commands.slash(name, description);

    override fun run(payload: CommandPayload) {
        if (payload.isSlashCommand()) {
            payload.commandEvent!!.reply("Pong!").queue();
        }
    }
}