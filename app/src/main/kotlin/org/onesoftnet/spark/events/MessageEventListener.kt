package org.onesoftnet.spark.events;

import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.onesoftnet.spark.App;
import org.onesoftnet.spark.Command;

class MessageEventListener : ListenerAdapter() {
    override fun onMessageReceived(event: MessageReceivedEvent) {
        val message = event.getMessage();

        if (message.getAuthor().isBot()) {
            return;
        }

        println(event.toString());

        val guild = event.getGuild();
        val prefix = App.app?.config!![guild.id.toString()]?.prefix;

        if (prefix == null) {
            return;
        }

        val content = message.getContentRaw();

        if (!content.startsWith(prefix)) {
            return;
        }

        val argv = content.substring(prefix.length).split(" ");
        val (commandName) = argv;
        val args = mutableListOf<String>();
        var index = -1;

        println(argv);
        println(commandName);
        
        argv.forEach { arg ->
            index++;
            println("Index: $index");

            if (index == 0) {
                return@forEach;
            }

            args += arg;
        }

        App.app?.commandManager!!.runMessageCommand(commandName, event, args);
    }
}