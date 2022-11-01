package org.onesoftnet.spark.core;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.Guild;
import org.onesoftnet.spark.App;
import net.dv8tion.jda.api.EmbedBuilder;
import java.awt.Color;
import java.time.format.DateTimeFormatter;
import java.time.Instant;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.entities.emoji.CustomEmoji;

class SuggestionManager(app: App) : Service(app) {
    override val name = "suggestionManager";

    public fun createSuggestion(guild: Guild, user: User, content: String, status: SuggestionStatus = SuggestionStatus.NO_STATUS) {
        println("Here");
        val channelID: String = app.config[guild.id.toString()]!!.channel;
        val channel = guild.getChannelById(TextChannel::class.java, channelID);

        if (channel == null) {
            println("null");
            return;
        }

        val embed = EmbedBuilder();

        embed.setAuthor("Suggestion from " + user.getAsTag(), null, user.getAvatarUrl());
        embed.setDescription(content);
        embed.addField("Status", status.toString(), false);
        embed.setColor(status.color);

        if (status.description != "")
            embed.setFooter(status.description);

        val date = DateTimeFormatter.ISO_DATE_TIME.parse(Instant.now().toString());
        embed.setTimestamp(date);

        channel.sendMessageEmbeds(embed.build()).queue { message ->
            message.addReaction(Emoji.fromCustom("check", app.globalSettings.checkEmoji.toLong(), false)).queue();
            message.addReaction(Emoji.fromCustom("error", app.globalSettings.errorEmoji.toLong(), false)).queue();
        };
    }
}