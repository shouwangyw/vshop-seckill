package com.veli.vshop.seckill.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.Duration;

/**
 * @author yangwei
 */
public class DurationDeserializer extends StdDeserializer<Duration> {
    private static final long serialVersionUID = 1L;

    public DurationDeserializer() {
        super(Duration.class);
    }

    @Override
    public Duration deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        return Duration.parse(parser.getValueAsString());
    }
}
