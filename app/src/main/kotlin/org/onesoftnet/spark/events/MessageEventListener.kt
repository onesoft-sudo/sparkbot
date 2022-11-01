package org.onesoftnet.spark.events;

import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.onesoftnet.spark.App;
import org.onesoftnet.spark.core.Command;

class MessageEventListener : ListenerAdapter() {
    override fun onMessageReceived(event: MessageReceivedEvent) {
        val message = event.getMessage();

        if (message.getAuthor().isBot()) {
            return;
        }

        println(event.toString());

        val guild = event.getGuild();
        val channel = event.getChannel();
        val config = App.app?.config!![guild.id.toString()];

        if (config == null) {
            return;
        }

        if (config.channel == channel.id.toString()) {
            println("Suggestion");
            App.app?.suggestionManager!!.createSuggestion(guild, message.getAuthor(), message.getContentRaw());

            try {
                message.delete().queue();
            }
            catch (e: Exception) {
                println(e);
            }

            return;
        }

        val prefix = config.prefix;
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