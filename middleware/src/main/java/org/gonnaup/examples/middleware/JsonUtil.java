package org.gonnaup.examples.middleware;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.gonnaup.examples.middleware.messagebody.MessageFactory;
import org.gonnaup.examples.middleware.messagebody.Product;

import java.io.IOException;

/**
 * json序列化工具类
 *
 * @author hy
 * @version 1.0
 * @Created on 2021/7/19 16:35
 */
@Slf4j
public final class JsonUtil {

    private static final ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = new ObjectMapper();
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        OBJECT_MAPPER.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        OBJECT_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public static <T> T parseJSON(String json, Class<T> type) {
        try {
            return OBJECT_MAPPER.readValue(json, type);
        } catch (JsonProcessingException e) {
            log.error("parse failed, reasons: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public static String toJSONString(Object source) {
        try {
            return OBJECT_MAPPER.writeValueAsString(source);
        } catch (JsonProcessingException e) {
            log.error("serial failed, reason: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public static byte[] toByteArray(Object source) {
        try {
            return OBJECT_MAPPER.writeValueAsBytes(source);
        } catch (JsonProcessingException e) {
            log.error("serial failed, reason: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public static <T> T byteArrayToObject(byte[] source, Class<T> type) {
        try {
            return OBJECT_MAPPER.readValue(source, type);
        } catch (IOException e) {
            log.error("parse failed, reasons: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void main(String[] args) {
        log.info(toJSONString(MessageFactory.randomProduct()));
        log.info(parseJSON(toJSONString(MessageFactory.randomProduct()), Product.class).toString());
    }

}
