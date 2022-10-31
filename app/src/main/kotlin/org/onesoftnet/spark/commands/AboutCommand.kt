package org.onesoftnet.spark.commands;

import org.onesoftnet.spark.CommandPayload;
import org.onesoftnet.spark.Command;
import org.onesoftnet.spark.App;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.EmbedBuilder;
import java.awt.Color;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;

class AboutCommand : Command() {
    override val name = "about";
    override val description = "About the bot";
    override val metaData = Commands.slash(name, description);

    override fun run(payload: CommandPayload) {
        val embed = EmbedBuilder();

        embed.setAuthor("SparkBot", null, App.app!!.jda!!.getSelfUser().getAvatarUrl());
        embed.setDescription("A bot for issue tracking.");

        val builder = MessageCreateBuilder()
            .setEmbeds(embed.build());

        if (payload.isSlashCommand()) {
            payload.commandEvent!!.reply(builder.build()).queue();
        }
    }
}