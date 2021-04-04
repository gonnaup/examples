package org.gonnaup.examples.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author gonnaup
 * @version 2021/3/21 16:51
 */
@Slf4j
public class ServerStart {

    private final int port;

    private ServerStart(int port) {
        this.port = port;
    }

    public static ServerStart port(int port) {
        return new ServerStart(port);
    }

    public void startNettyServer(List<ChannelHandler> handlers) throws Exception {
        ServerBootstrap bootstrap = new ServerBootstrap();
        EventLoopGroup boss = new NioEventLoopGroup(1);
        EventLoopGroup work = new NioEventLoopGroup(2);
        try {
            bootstrap.group(boss, work)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(port)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(handlers.toArray(new ChannelHandler[]{}));
                        }
                    });
            ChannelFuture bindFuture = bootstrap.bind().sync();//等待绑定成功
            log.info("服务器启动成功，监听端口：{}", bindFuture.channel().localAddress());
            ChannelFuture closeFuture = bindFuture.channel().closeFuture();
            closeFuture.sync();//等待服务器关闭
        } finally {
            work.shutdownGracefully();
            boss.shutdownGracefully();
        }
    }

}
