package com.sesamecare.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.sesamecare.util.CustomLocalDateTimeSerializer.FORMAT;

public class CustomLocalDateTimeDeserializer extends StdDeserializer<LocalDateTime> {

    public CustomLocalDateTimeDeserializer() {
        this(null);

    }

    public CustomLocalDateTimeDeserializer(Class<?> vc) {
        super(vc);
    }

    public LocalDateTime deserialize(String valueAsString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT);
        return LocalDateTime.parse(valueAsString, formatter);
    }

    public LocalDateTime deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException {
        return deserialize(jp.getValueAsString());
    }
}
