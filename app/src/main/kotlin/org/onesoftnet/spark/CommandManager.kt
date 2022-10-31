package org.onesoftnet.spark;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

class CommandManager(app: App) : Service(app) {
    override val name = "CommandManager";
    val commands = mutableMapOf<String, Command>();

    public fun addCommands(vararg commands: Command) {
        commands.forEach {
            it.setApplication(app);
            this.commands[it.name] = it;
        }
    }

    public fun runMessageCommand(name: String, event: MessageReceivedEvent, args: List<String>): Boolean {
        val command = commands[name];

        if (command == null) {
            println("\"$name\" command not found");
            return false;
        }

        if (!command.supportsMessageCommand) {
            println("\"$name\" does not support message commands");
            return false;
        }

        command.run(CommandPayload(event, null, args));

        return true;
    }

    public fun runSlashCommand(name: String, event: SlashCommandInteractionEvent): Boolean {
        val command = commands[name];

        if (command == null) {
            println("\"$name\" command not found");
            return false;
        }

        if (!command.supportsSlashCommand) {
            println("\"$name\" does not support message commands");
            return false;
        }

        command.run(CommandPayload(null, event));
        return true;
    }

    public fun registerSlashCommands(global: Boolean = false) {
        val homeGuild = app.jda!!.getGuildById(app.globalSettings.homeGuild);

        if (homeGuild != null) {
            val commands: Map<String, Command> = this.commands.filter { (key, value) ->
                return@filter value.supportsSlashCommand;
            };

            val commandInfo: List<SlashCommandData> = commands.map { (key, value) ->
                if (value.metaData == null) {
                    throw Exception("metaData must not be null for slash commands");
                }

                return@map value.metaData!!;
            };

            println(commands.size.toString() + " commands found");
            homeGuild.updateCommands().addCommands(*commandInfo.toTypedArray()).queue();
            println("Guild commands registered");
        }
    }
}