package org.gonnaup.examples.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.ReplayingDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 解码出两个整数，然后相加作为解码结果
 * @author gonnaup
 * @version 2021/4/1 16:01
 */
@Slf4j
public class IntegerAddDecoderEx {

    static class IntegerAddDecoder extends ReplayingDecoder<IntegerAddDecoder.Status> {

        enum Status {PARSE_1, PARSE_2}

        private int first;

        private int second;

        public IntegerAddDecoder() {
            super(Status.PARSE_1);//初始状态
        }

        @Override
        protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
            //先判断解析状态
            switch (state()) {
                case PARSE_1:
                    //获取第一个整数
                    first = in.readInt();
                    //进入第二步并且设置"读断点指针"为当前的读取位置
                    checkpoint(Status.PARSE_2);
                    break;
                case PARSE_2:
                    second = in.readInt();
                    int sum = first + second;
                    log.info("输出数字 {}", sum);
                    out.add(sum);
                    checkpoint(Status.PARSE_1);
                    break;
                default: break;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        EmbeddedChannel channel = new EmbeddedChannel(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel ch) {
                ch.pipeline().addLast(new IntegerAddDecoder()).addLast(new IntegerDecoderEx.IntegerHandler());
            }
        });
        for (int i = 1; i < 10; i++) {
            ByteBuf buf = Unpooled.buffer();
            buf.writeInt(i);
            channel.writeInbound(buf);
        }
        channel.flush();
        TimeUnit.SECONDS.sleep(2);
    }

}
