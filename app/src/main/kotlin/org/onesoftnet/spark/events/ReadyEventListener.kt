package org.onesoftnet.spark.events;

import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import org.onesoftnet.spark.App;

class ReadyEventListener : ListenerAdapter() {
    override fun onReady(event: ReadyEvent) {
        println("Bot is ready!");
        App.app?.commandManager!!.registerSlashCommands();
    }
}