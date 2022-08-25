/*
 * Copyright (c) OSN Inc. 2022
 */

package org.onesoftnet.spark.services

import com.mongodb.client.FindIterable
import me.jakejmattson.discordkt.annotations.Service
import org.litote.kmongo.KMongo
import org.litote.kmongo.getCollection
import org.onesoftnet.spark.dataclasses.GuildConfig

@Service
class Database {
    val connectionString = System.getenv("MONGODB_URI")!!
    val dbName = "spark"
    private val client = KMongo.createClient(connectionString)
    val db = client.getDatabase(dbName)

    fun configs(): FindIterable<GuildConfig> {
        return db.getCollection<GuildConfig>("collections.guildConfig").find()
    }
}