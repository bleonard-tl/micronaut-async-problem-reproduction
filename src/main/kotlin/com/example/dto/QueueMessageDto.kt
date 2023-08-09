package com.example.dto

import com.fasterxml.jackson.annotation.JsonIgnore

// Base class for all queue messages.
// The body is a JSON encoded String that gets serialized/deserialized into the member variables
// of the sub-class.  We use a message attribute named "message.type" along with messages we
// generate to allow the receiver to route the message to the appropriate handler.  Each sub-class
// defines it's own messageType and must pass it to this parent during construction.  The messageType
// field IS NOT serialized to the JSON body/payload.
abstract class QueueMessageDto(
    @get:JsonIgnore
    val messageType: String,

    @get:JsonIgnore
    val messageId: String
) {

    companion object {
        @JvmStatic
        val MESSAGE_TYPE_ATTRIBUTE = "message.type"
    }
}
