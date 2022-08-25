/*
 * Copyright (c) OSN Inc. 2022
 */

package org.onesoftnet.spark;

import dev.kord.common.annotation.KordPreview
import dev.kord.gateway.PrivilegedIntent

@KordPreview
@PrivilegedIntent
fun main() {
    val app = App("-")
    app.run()
}