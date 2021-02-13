package com.veli.vshop.seckill.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.Duration;

/**
 * @author yangwei
 * @date 2021-02-10 21:39
 */
public class DurationSerializer extends StdSerializer<Duration> {
    private static final long serialVersionUID = 1L;

    public DurationSerializer() {
        super(Duration.class);
    }

    @Override
    public void serialize(Duration value, JsonGenerator generator, SerializerProvider provider) throws IOException {
        if (value == null) {
            generator.writeNull();
            return;
        }
        generator.writeString(value.toString());
    }
}
