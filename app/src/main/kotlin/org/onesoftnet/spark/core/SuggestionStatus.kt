package org.onesoftnet.spark.core;

import java.awt.Color;

enum class SuggestionStatus(val stringified: String, val color: Color, val description: String = "") {
    NO_STATUS("No Status", Color(100, 100, 100)), 
    UNDER_REVIEW("Under Review", Color(0, 123, 255)), 
    ACCEPTED("Accepted", Color.GREEN),
    NOT_ACCEPTED("Not Accepted", Color.RED),
    UNDER_DISCUSSION("Under Discussion", Color.CYAN),
    INVALID("Invalid", Color.MAGENTA),
    SPAM("Spam", Color(123, 255, 0)),
    FIXED("Fixed", Color.GREEN),
    IMPLEMENTED("Implemented", Color.GREEN),
    DUPLICATE("Duplicate", Color.GRAY);

    override fun toString() = stringified;
}