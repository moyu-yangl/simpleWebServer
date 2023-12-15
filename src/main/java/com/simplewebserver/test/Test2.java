package com.simplewebserver.test;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import static com.simplewebserver.test.Test.SERVER_ADDRESS;

public class Test2 {
    public static void main(String[] args) {
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", SERVER_ADDRESS);
        try (
                SocketChannel socketChannel = SocketChannel.open();
        ) {

            socketChannel.configureBlocking(false);

            if (!socketChannel.connect(inetSocketAddress)) {
                while (!socketChannel.finishConnect()) {
                    System.out.println("客户端正在连接中，请耐心等待");
                }
            }
            ByteBuffer byteBuffer = ByteBuffer.wrap("mikechen的互联网架构".getBytes());
            socketChannel.write(byteBuffer);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
