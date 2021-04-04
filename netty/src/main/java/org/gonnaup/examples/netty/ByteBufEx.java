package org.gonnaup.examples.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;

/**
 * ByteBuf示例
 * @author gonnaup
 * @version 2021/3/27 10:05
 */
@Slf4j
public class ByteBufEx {

    void writeReadEx() {
        ByteBufAllocator allocator = ByteBufAllocator.DEFAULT;
        log.info("ByteBufAllocator type {}", allocator.getClass().getSimpleName());
        ByteBuf byteBuf = allocator.buffer(9, 100);
        byteBuf.writeBytes(new byte[] {1, 2, 3, 4});
        log.info("可读字节数 {}", byteBuf.readableBytes());
        byteBuf.forEachByte(bt -> {
            System.out.println(bt);
            return true;
        });
    }

    public static void main(String[] args) {
        ByteBufEx byteBufEx = new ByteBufEx();
        byteBufEx.writeReadEx();
    }

}
