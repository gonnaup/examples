package org.gonnaup.examples.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * @author gonnaup
 * @version 2021/3/27 15:17
 */
@Slf4j
public class NettyEchoClient {
    static final int port = 16800;

    public static void main(String[] args) {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(port))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(new NettyEchoClientHandler());
                        }
                    });
            ChannelFuture connectFuture = bootstrap.connect();
            connectFuture.addListener(future -> {
                if (future.isSuccess()) {
                    log.info("连接成功");
                } else {
                    log.info("连接失败");
                }
            });
            connectFuture.sync();//阻塞，直到连接成功或失败
            Channel channel = connectFuture.channel();//获取channel
//            System.out.println("请输入发送内容(quit退出)");
//            for (;;) {
//                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//                String line = reader.readLine();
//                if ("quit".equalsIgnoreCase(line)) {
//                    break;
//                }
//                ByteBuf buffer = channel.alloc().buffer();
//                buffer.writeBytes(line.getBytes(StandardCharsets.UTF_8));
//                channel.writeAndFlush(buffer);
//            }

            /** 半包问题示例 **/
            String msg = "你好啊，我正在学netty";
            byte[] msgBytes = msg.getBytes(StandardCharsets.UTF_8);
            for (int i = 0; i < 1000; i++) {
                ByteBuf buffer = channel.alloc().buffer();
                buffer.writeBytes(msgBytes);
                channel.writeAndFlush(buffer);
            }
            TimeUnit.SECONDS.sleep(2);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }

    @ChannelHandler.Sharable
    static class NettyEchoClientHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            ByteBuf byteBuf = (ByteBuf) msg;
            byte[] arr = new byte[byteBuf.readableBytes()];
            byteBuf.getBytes(0, arr);
            log.info("收到回执消息 {}", new String(arr, StandardCharsets.UTF_8));
            byteBuf.release();//手动释放
            //调用父类入站方法，向后传递自动释放
//            super.channelRead(ctx, msg);
        }
    }

}
