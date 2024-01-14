package com.simplewebserver.network.connector.nio;

import com.simplewebserver.network.endpoint.nio.NioEndpoint;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.channels.SocketChannel;


@Slf4j
public class NioAcceptor implements Runnable {
    private NioEndpoint nioEndpoint;

    public NioAcceptor(NioEndpoint nioEndpoint) {
        this.nioEndpoint = nioEndpoint;
    }

    @Override
    public void run() {
//        log.info("{} 开始监听", Thread.currentThread().getName());
        while (nioEndpoint.isRunning()) {
            SocketChannel client;
            try {
                client = nioEndpoint.accept();
                if (client == null) {
                    continue;
                }
                client.configureBlocking(false);
                log.info("Acceptor接收到连接请求 {}", client);
                nioEndpoint.registerToPoller(client);
                log.info("socketWrapper:{}", client);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
