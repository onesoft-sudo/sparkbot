package org.onesoftnet.spark;

import kotlinx.serialization.json.Json;
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
    val file = File("$prefix/config/config.json");
    val config = Json.decodeFromString<Map<String, Config>>(file.readText());
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

    public fun boot() {
        jda = jdaBuilder.build();
        registerEvents();
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

fun main() {  
    val app = App();
    
    app.boot();
    app.registerEvents(
        MessageEventListener(),
        ReadyEventListener()
    );

    app.registerCommands(
        TestCommand()
    );
}
