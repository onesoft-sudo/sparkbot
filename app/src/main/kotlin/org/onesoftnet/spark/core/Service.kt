package org.onesoftnet.spark.core;

import org.onesoftnet.spark.App;

abstract class Service(val app: App) {
    abstract val name: String;
    
    public fun boot() {
        
    }
}