package org.gonnaup.examples.netty;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.string.StringDecoder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * json数据解析
 * @author gonnaup
 * @version 2021/4/1 21:30
 */
@Slf4j
public class JsonDecoderEx {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Data
    static class Order {
        private Long id;

        private String productName;

        private double productPrice;

        private int number;

        private String detail;
    }

    //字符串转java对象解码器
    static class OrderJsonDecoder extends MessageToMessageDecoder<String> {
        @Override
        protected void decode(ChannelHandlerContext ctx, String msg, List<Object> out) throws Exception {
            Order order = objectMapper.readValue(msg, Order.class);
            out.add(order);
        }
    }

    //java对象处理器
    static class OrderHandler extends SimpleChannelInboundHandler<Order> {
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Order msg) {
            log.info("收到订单信息 {}", msg);
        }
    }

    public static void main(String[] args) throws JsonProcessingException, InterruptedException {
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel ch) {
                ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4))
                        .addLast(new StringDecoder(StandardCharsets.UTF_8))
                        .addLast(new OrderJsonDecoder())
                        .addLast(new OrderHandler());
            }
        });
        final Order order = new Order();
        order.setProductName("computer");
        order.setProductPrice(4399.0D);
        for (int i = 1; i <= 100; i++) {
            order.setId((long) i);
            order.setDetail(String.format("第%d批订单", i));
            String orderjson = objectMapper.writeValueAsString(order);
            byte[] orderjsonBytes = orderjson.getBytes(StandardCharsets.UTF_8);
            int length = orderjsonBytes.length;
            ByteBuf buffer = embeddedChannel.alloc().buffer();
            buffer.writeInt(length);
            buffer.writeBytes(orderjsonBytes);
            embeddedChannel.writeInbound(buffer);
        }
        TimeUnit.SECONDS.sleep(5);
    }

}
