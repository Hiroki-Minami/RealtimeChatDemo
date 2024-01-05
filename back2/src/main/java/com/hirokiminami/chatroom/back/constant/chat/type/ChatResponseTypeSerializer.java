package com.hirokiminami.chatroom.back.constant.chat.type;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class ChatResponseTypeSerializer extends JsonSerializer<ChatResponseType> {

    @Override
    public void serialize(ChatResponseType chatResponseType, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(chatResponseType.getStringValue());
    }
}
