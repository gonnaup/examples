package org.gonnaup.examples.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 基于netty的回显服务处理器
 * @author gonnaup
 * @version 2021/3/27 11:12
 */
@Slf4j
public class NettyEchoServer {

    public static void main(String[] args) {
        try {
            ServerStart.port(16800).startNettyServer(List.of(new NettyEchoServerHandler()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ChannelHandler.Sharable//可共享的handler
    static class NettyEchoServerHandler extends ChannelInboundHandlerAdapter {

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            ByteBuf buf = (ByteBuf) msg;
            log.info("msg type {}", buf.hasArray() ? "堆内存" : "直接内存");
            byte[] arr = new byte[buf.readableBytes()];
            buf.getBytes(0, arr);//getBytes不影响readIndex，readBytes方法readIndex将前进
            String message = new String(arr, StandardCharsets.UTF_8);
            log.info("收到消息 {}", message);
            ctx.writeAndFlush(msg);//已释放
//        super.channelRead(ctx, msg);
        }
    }

}

