package com.example.ecommerceapi.config;

import graphql.scalars.ExtendedScalars;
import graphql.schema.GraphQLScalarType;
import graphql.schema.idl.RuntimeWiring;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Configuration
public class GraphQLScalarConfig implements RuntimeWiringConfigurer {

    @Bean
    public GraphQLScalarType dateScalar() {
        return ExtendedScalars.DateTime;
    }

    @Override
    public void configure(RuntimeWiring.Builder builder) {
        builder.scalar(ExtendedScalars.GraphQLLong);
    }
}