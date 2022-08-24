/*
 * Copyright (c) OSN Inc. 2022
 */

package org.onesoftnet.spark.services

import me.jakejmattson.discordkt.annotations.Service
import java.io.File
import java.io.FileInputStream
import java.util.Properties

@Service
class PropertyLoaderService {
    private val props = Properties()

    fun load() {
        val file = File("/resources/bot.properties")

        FileInputStream(file).use {
            props.load(it)
        }
    }

    fun getProp(prop: String): String? = this.props.getProperty(prop)

    operator fun component1(): PropertyLoaderService {
        return this;
    }
}