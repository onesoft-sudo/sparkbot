/*
 * Copyright (c) OSN Inc. 2022
 */

package org.onesoftnet.spark.services

import com.mongodb.MongoClient
import com.mongodb.client.MongoClients
import me.jakejmattson.discordkt.annotations.Service

@Service
class Database {
    val connectionString = System.getenv("MONGODB_URI")!!
    val dbName = "spark"
    private val client = MongoClients.create(connectionString)
    val db = client.getDatabase(dbName)
}
