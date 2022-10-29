package org.onesoftnet.spark.events

import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

class MessageEventListener : ListenerAdapter() {
    override fun onMessageReceived(event: MessageReceivedEvent) {
        println(event.toString())
    }
}