package org.onesoftnet.spark;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

abstract class Command(var app: App? = null) {
    abstract val name: String;
    abstract fun run(event: MessageReceivedEvent, args: List<String>): Unit;

    public fun setApplication(app: App?) {
        this.app = app;
    }
}