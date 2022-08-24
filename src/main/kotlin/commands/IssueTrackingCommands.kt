/*
 * Copyright (c) OSN Inc. 2022
 */

package org.onesoftnet.spark.commands

import dev.kord.core.behavior.reply
import me.jakejmattson.discordkt.arguments.EveryArg
import me.jakejmattson.discordkt.commands.commands

@Suppress("unused")
fun issueTrackingCommands() = commands("Issue Tracking") {
    text("issue") {
        description = "Have an issue? Report it!"

        execute(EveryArg) {
            val (issue) = args

            message.reply {
                content = "What's your issue?\nYour issue is: $issue"
            }
        }
    }
}