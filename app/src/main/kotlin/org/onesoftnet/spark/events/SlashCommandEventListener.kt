package org.onesoftnet.spark.events;

import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.onesoftnet.spark.App;

class SlashCommandEventListener : ListenerAdapter() {
    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        App.app?.commandManager?.runSlashCommand(event.getName(), event);
    }
}