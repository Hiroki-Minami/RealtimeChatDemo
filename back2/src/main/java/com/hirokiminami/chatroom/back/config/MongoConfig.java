package com.hirokiminami.chatroom.back.config;

import com.hirokiminami.chatroom.back.constant.chatroom.ChatRoomStatusReadConverter;
import com.hirokiminami.chatroom.back.constant.chatroom.ChatRoomStatusWriteConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MongoConfig {
    @Bean
    public MongoCustomConversions customConversions() {
        List<Converter<?, ?>> converters = new ArrayList<>();
        converters.add(new ChatRoomStatusWriteConverter());
        converters.add(new ChatRoomStatusReadConverter());
        return new MongoCustomConversions(converters);
    }
}
