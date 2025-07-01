package com.example.ecommerceapi.config;

import graphql.language.StringValue;
import graphql.scalars.ExtendedScalars;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Configuration
public class GraphQLConfig {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

    public static final GraphQLScalarType Date = GraphQLScalarType.newScalar()
            .name("Date")
            .description("Java LocalDate as scalar")
            .coercing(new Coercing<LocalDate, String>() {
                @Override
                public String serialize(Object dataFetcherResult) {
                    if (dataFetcherResult instanceof LocalDate) {
                        return ((LocalDate) dataFetcherResult).format(formatter);
                    }
                    throw new CoercingSerializeException("Expected a LocalDate object.");
                }

                @Override
                public LocalDate parseValue(Object input) {
                    try {
                        if (input instanceof String) {
                            return LocalDate.parse((String) input, formatter);
                        }
                        throw new CoercingParseValueException("Expected a String");
                    } catch (Exception e) {
                        throw new CoercingParseValueException("Invalid date format");
                    }
                }

                @Override
                public LocalDate parseLiteral(Object input) {
                    if (input instanceof StringValue) {
                        try {
                            return LocalDate.parse(((StringValue) input).getValue(), formatter);
                        } catch (Exception e) {
                            throw new CoercingParseLiteralException("Invalid date format");
                        }
                    }
                    throw new CoercingParseLiteralException("Expected a StringValue.");
                }
            })
            .build();

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return wiringBuilder -> wiringBuilder
                .scalar(ExtendedScalars.DateTime);
    }
}
