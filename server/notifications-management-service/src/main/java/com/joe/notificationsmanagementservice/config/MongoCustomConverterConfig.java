package com.joe.notificationsmanagementservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WritingConverter
class DateConverter implements Converter<Date, Timestamp> {
    @Override
    public Timestamp convert(Date source) {
        if (source == null) {
            return null;
        }
        return Timestamp.from(source.toInstant());
    }
}

@Configuration
public class MongoCustomConverterConfig {
    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        List<Converter<?,?>> converters = new ArrayList();

        converters.add(new DateConverter());

        return new MongoCustomConversions(converters);
    }
}
