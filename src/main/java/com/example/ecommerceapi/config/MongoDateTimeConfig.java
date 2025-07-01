package com.example.ecommerceapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Date;

@Configuration
public class MongoDateTimeConfig {

    @ReadingConverter
    public static class DateToOffsetConverter
            implements Converter<Date, OffsetDateTime> {

        @Override
        public OffsetDateTime convert(Date source) {
            return source.toInstant().atOffset(ZoneOffset.UTC);
        }
    }

    @WritingConverter
    public static class OffsetToDateConverter
            implements Converter<OffsetDateTime, Date> {

        @Override
        public Date convert(OffsetDateTime source) {
            return Date.from(source.toInstant());
        }
    }

    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        return new MongoCustomConversions(
                Arrays.asList(
                        new DateToOffsetConverter(),
                        new OffsetToDateConverter()
                )
        );
    }
}
