/*
 * Copyright (c) OSN Inc. 2022
 */

package org.onesoftnet.spark.commands

import dev.kord.common.entity.optional.optional
import dev.kord.core.behavior.reply
import me.jakejmattson.discordkt.arguments.AnyArg
import me.jakejmattson.discordkt.arguments.EveryArg
import me.jakejmattson.discordkt.commands.commands

@Suppress("unused")
fun issueTrackingCommands() = commands("Issue Tracking") {
    text("issue") {
        description = "Have an issue? Report it!"

        execute {
            message.reply {
                content = "You must provide an argument!"
            }
        }

        execute(AnyArg("Issue")) {
            val (issue) = args

            message.reply {
                content = "Input:\n${issue}"
            }
        }
    }
}