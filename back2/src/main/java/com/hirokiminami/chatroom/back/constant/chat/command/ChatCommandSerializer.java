package com.hirokiminami.chatroom.back.constant.chat.command;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class ChatCommandSerializer extends JsonSerializer<ChatCommand> {

    @Override
    public void serialize(ChatCommand command, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(command.getStringValue());
    }
}
