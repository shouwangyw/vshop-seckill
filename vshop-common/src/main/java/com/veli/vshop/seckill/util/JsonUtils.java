package com.veli.vshop.seckill.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.veli.vshop.seckill.serializer.DurationDeserializer;
import com.veli.vshop.seckill.serializer.DurationSerializer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;

/**
 * @author yangwei
 */
public class JsonUtils {
    private static final Logger LOG = LoggerFactory.getLogger(JsonUtils.class);

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    static {
        MAPPER.setDateFormat(new SimpleDateFormat(DATE_FORMAT_PATTERN));
        MAPPER.setSerializationInclusion(Include.NON_NULL);
        MAPPER.configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, false);
        MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        MAPPER.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Duration.class, new DurationSerializer());
        simpleModule.addDeserializer(Duration.class, new DurationDeserializer());
        MAPPER.registerModule(simpleModule);
    }

    /**
     * 对象转Json字符串
     */
    public static <T> String toStr(T t) {
        if (t == null) return StringUtils.EMPTY;
        try {
            return MAPPER.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            LOG.error(e.getMessage(), e);
        }
        return StringUtils.EMPTY;
    }

    /**
     * Json字符串转对象
     */
    public static <T> T toObj(String json, Class<T> clazz) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        try {
            return MAPPER.readValue(json, clazz);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        return null;
    }
}
