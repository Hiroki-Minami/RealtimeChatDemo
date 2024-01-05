package com.hirokiminami.chatroom.back.constant.chat.type;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class ChatResponseTypeDeserializer extends StdDeserializer<ChatResponseType> {
    protected ChatResponseTypeDeserializer() {
        super(ChatResponseType.class);
    }

    @Override
    public ChatResponseType deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        return ChatResponseType.fromValue(node.asText());
    }
}
