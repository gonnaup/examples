package org.gonnaup.examples.middleware.messagequeues.kafka;

import org.apache.kafka.common.serialization.Deserializer;
import org.gonnaup.examples.middleware.JsonUtil;
import org.gonnaup.examples.middleware.messagebody.Product;

/**
 * @author gonnaup
 * @version created at 2021/7/20 11:53
 */
public class ProductKafkaDeserializer implements Deserializer<Product> {
    @Override
    public Product deserialize(String topic, byte[] data) {
        if (data == null) {
            return null;
        }
        return JsonUtil.byteArrayToObject(data, Product.class);
    }
}
