package org.gonnaup.examples.middleware.messagequeues.kafka;

import org.apache.kafka.common.serialization.Serializer;
import org.gonnaup.examples.middleware.JsonUtil;
import org.gonnaup.examples.middleware.messagebody.Product;

/**
 * 自定义kafka序列化器
 * @author gonnaup
 * @version created at 2021/7/20 11:51
 */
public class ProductKafksSerializer implements Serializer<Product> {
    @Override
    public byte[] serialize(String topic, Product data) {
        if (data == null) {
            return null;
        }
        return JsonUtil.toByteArray(data);
    }
}
