package org.gonnaup.examples.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 整数解码器示例
 *
 * @author gonnaup
 * @version 2021/3/27 16:17
 */
@Slf4j
public class IntegerDecoderEx {

    static class Byte2IntegerDecoder extends ByteToMessageDecoder {
        @Override
        protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
            while (in.readableBytes() > 4) {
                int i = in.readInt();
                log.info("解码出一个整数 {}", i);
                out.add(i);
            }
        }
    }

    static class IntegerHandler extends SimpleChannelInboundHandler<Integer> {
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Integer msg) {
            log.info("收到一个整数 {}", msg);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        EmbeddedChannel channel = new EmbeddedChannel(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel ch) {
                ch.pipeline().addLast(new Byte2IntegerDecoder()).addLast(new IntegerHandler());
            }
        });
        for (int i = 0; i < 10; i++) {
            ByteBuf buf = Unpooled.buffer();
            buf.writeInt(i);
            log.info("发送整数 {}", i);
            channel.writeInbound(buf);
        }
        channel.flush();
        TimeUnit.SECONDS.sleep(10);
    }
}
