package org.gonnaup.examples.netty.nio;

import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

/**
 * @author gonnaup
 * @version 2021/1/28 21:04
 */
public class Example {

    @Slf4j
    static class SimpleNioServer {

        public static void main(String[] args) {
            try {
                startServer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public static void startServer() throws Exception {
            final Selector selector = Selector.open();//选择器
            final ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.bind(new InetSocketAddress(10234));
            log.info("服务器启动成功...");
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            while (selector.select() > 0) {
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    if (selectionKey.isAcceptable()) {
                        log.info("accept client, change to READ");
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        ByteBuffer byteBuffer = ByteBuffer.wrap("accepted".getBytes(StandardCharsets.UTF_8));
                        byteBuffer.flip();
                        socketChannel.write(byteBuffer);
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ);
                    }
                    if (selectionKey.isReadable()) {
                        log.info("begin read client data");
                        SocketChannel redableChannel = (SocketChannel) selectionKey.channel();
//                        ByteBuffer byteBuffer = ByteBuffer.wrap("accepted".getBytes(StandardCharsets.UTF_8));
//                        byteBuffer.flip();
//                        redableChannel.write(byteBuffer);
//                        byteBuffer.clear();
//                        redableChannel.shutdownOutput();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        int len = 0;
                        while ((len = redableChannel.read(buffer)) >= 0) {
                            buffer.flip();//翻转成可读状态
                            log.info("read data => {}", new String(buffer.array(), 0, len, StandardCharsets.UTF_8));
                            buffer.clear();//清理，变成写状态
                        }
                    }
                    iterator.remove();
                }
            }
        }
    }

    @Slf4j
    static class SimpleNioClient {

        public static void main(String[] args) {
            try {
                startClient();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public static void startClient() throws Exception {
            SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("localhost", 10234));
            socketChannel.configureBlocking(false);
            while (!socketChannel.finishConnect()) {
                TimeUnit.MILLISECONDS.sleep(100);
            }
//            Selector open = Selector.open();
//            socketChannel.register(open, SelectionKey.OP_READ);

            log.info("已连接成功");
            ByteBuffer byteBuffer = ByteBuffer.allocate(32);
            byteBuffer.put("我来了".getBytes(StandardCharsets.UTF_8)).flip();
            socketChannel.write(byteBuffer);
            TimeUnit.SECONDS.sleep(2);
            byteBuffer.clear();
            byteBuffer.put("想我了没".getBytes(StandardCharsets.UTF_8)).flip();
            socketChannel.write(byteBuffer);
            socketChannel.shutdownOutput();
            socketChannel.close();
        }
    }

}
