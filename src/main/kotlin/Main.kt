package org.onesoftnet.spark;

import dev.kord.common.annotation.KordPreview
import dev.kord.gateway.PrivilegedIntent
import io.github.cdimascio.dotenv.Dotenv

@KordPreview
@PrivilegedIntent
fun main() {
    val dotenv = Dotenv.load();
    val token = dotenv.get("TOKEN");
    val app = App(token, "-")
    app.run()
}