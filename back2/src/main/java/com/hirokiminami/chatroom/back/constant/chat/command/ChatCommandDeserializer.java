package com.hirokiminami.chatroom.back.constant.chat.command;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class ChatCommandDeserializer extends StdDeserializer<ChatCommand> {
    protected ChatCommandDeserializer() {
        super(ChatCommand.class);
    }

    @Override
    public ChatCommand deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        return ChatCommand.fromValue(node.asText());
    }
}

