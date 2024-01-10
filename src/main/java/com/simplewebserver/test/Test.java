package com.simplewebserver.test;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

public class Test {
    public static final int BUFFER_SIZE = 1024 * 8;
    public static final int SERVER_ADDRESS = 9870;
    public static CountDownLatch cd;

    public static void main(String[] args) throws InterruptedException {
        cd = new CountDownLatch(2);
        buildServer();
        Thread.sleep(500L);
        buildClient();
        cd.await();
        System.out.println("end");
    }

    public static void buildServer() {
        new Thread(() -> {
            InetSocketAddress inetSocketAddress = new InetSocketAddress(SERVER_ADDRESS);
            try (
                    ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
                    Selector selector = Selector.open()
            ) {
                serverSocketChannel.socket().bind(inetSocketAddress);
                serverSocketChannel.configureBlocking(false);
                serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
                to:
                for (; selector.select() > 0; ) {
//                    selector.select(2000);
//                    Thread.sleep(10L);
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey con = iterator.next();
                        if (con.isAcceptable()) {
                            SocketChannel accept = serverSocketChannel.accept();
                            accept.configureBlocking(false);
                            System.out.println("client:" + accept.getRemoteAddress() + " is connect");
                            accept.register(selector, SelectionKey.OP_READ);
                        } else if (con.isReadable()) {
                            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                            SocketChannel channel = (SocketChannel) con.channel();
                            int len = 0;
                            while ((len = channel.read(byteBuffer)) > 0) {
                                if (new String(byteBuffer.array(), 0, len).equals("quit")) {
                                    break to;
                                }
                                System.out.println("client:" + channel.getLocalAddress() + " send " + new String(byteBuffer.array(), 0, len));
                            }
                        }
                        iterator.remove();
                    }
                }

            } catch (Exception e) {
                System.out.println("ERROR : " + e.getMessage());
                e.printStackTrace();
            }
            cd.countDown();
        }).start();
    }

    public static void buildClient() {

        new Thread(() -> {
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
//                while (true) {
                Thread.sleep(500L);
//                ByteBuffer byteBuffer = ByteBuffer.wrap("quit".getBytes());
                ByteBuffer byteBuffer = ByteBuffer.wrap("java nio test".getBytes());
//                byteBuffer.flip();
                socketChannel.write(byteBuffer);
//                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            cd.countDown();
        }).start();
    }

    public static class User {
        String name;
        int age;

        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }
}
