package org.onesoftnet.spark;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

class CommandManager(app: App) : Service(app) {
    override val name = "CommandManager";
    val commands = mutableMapOf<String, Command>();

    public fun addCommands(vararg commands: Command) {
        commands.forEach {
            it.setApplication(app);
            this.commands[it.name] = it;
        }
    }

    public fun runCommand(name: String, event: MessageReceivedEvent, args: List<String>): Boolean {
        val command = commands[name];

        if (command == null) {
            println("\"$name\" command not found");
            return false;
        }

        command.run(event, args);

        return true;
    }
}