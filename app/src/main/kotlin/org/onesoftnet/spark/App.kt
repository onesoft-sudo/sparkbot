package org.onesoftnet.spark;

import kotlinx.serialization.json.Json;
import org.onesoftnet.spark.utils.GlobalSettings;
import org.onesoftnet.spark.utils.Config;
import java.io.File;
import kotlinx.serialization.decodeFromString;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.onesoftnet.spark.events.*;
import org.onesoftnet.spark.commands.*;
import java.util.stream.Stream;
import net.dv8tion.jda.api.requests.GatewayIntent;

class App {
    val token = System.getenv("TOKEN");
    val prefix = System.getenv("SPARK_PREFIX") ?: ".";
    val config = Json.decodeFromString<Map<String, Config>>(File("$prefix/config/config.json").readText());
    val globalSettings = Json.decodeFromString<GlobalSettings>(File("$prefix/config/global.json").readText());
    val jdaBuilder = JDABuilder.createDefault(token);
    var jda: JDA? = null;
    val commandManager = CommandManager(this);

    companion object {
        var app: App? = null;
    }

    init {
        App.app = this;

        if (token == null) {
            println("A token must be set in the env.");
            System.exit(-1);
        }
        
        println(config.toString());
        setIntents();
    }

    public fun boot(callback: (app: App) -> Unit) {
        jda = jdaBuilder.build();
        registerEvents();
        callback(this);
    }

    protected fun setIntents() {
        jdaBuilder.enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MESSAGES);
    }

    public fun registerEvents(vararg events: ListenerAdapter) {
        jda?.addEventListener(*events);
    }

    public fun registerCommands(vararg commands: Command) {
        this.commandManager.addCommands(*commands);
    }
}

fun main(args: Array<String>) {  
    val app = App();
    
    app.boot {
        if (args.contains("--commands") || app.globalSettings.registerCommandsAtBoot) {
            println("Registering application commands...");
            app.commandManager.registerSlashCommands(args.contains("--global"));
        }
    };

    app.registerEvents(
        MessageEventListener(),
        ReadyEventListener(),
        SlashCommandEventListener()
    );

    app.registerCommands(
        TestCommand(),
        AboutCommand()
    );
}
