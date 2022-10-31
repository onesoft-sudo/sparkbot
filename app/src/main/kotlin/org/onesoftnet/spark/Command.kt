package org.onesoftnet.spark;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

abstract class Command(var app: App? = null) {
    abstract val name: String;
    open val description: String = "";
    
    open val supportsMessageCommand = true;
    open val supportsSlashCommand = true;

    open val metaData: SlashCommandData? = null;
    
    abstract fun run(payload: CommandPayload): Unit;

    public fun setApplication(app: App?) {
        this.app = app;
    }
}