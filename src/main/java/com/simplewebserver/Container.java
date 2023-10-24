package com.simplewebserver;

import com.simplewebserver.domain.Request;
import com.simplewebserver.domain.Response;
import com.simplewebserver.util.HttpUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Container {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8080);
        for (; ; ) {
            Socket accept = server.accept();
            RequestTask requestTask = new RequestTask(accept);
            requestTask.start();
        }

    }

    public static class RequestTask extends Thread {
        private Socket socket;

        public RequestTask(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            InputStream is = null;
            OutputStream os = null;
            try {
                is = socket.getInputStream();
                int i = is.available();
                while (i == 0) {
                    i = is.available();
                }
                byte[] bytes = new byte[i];
                int read = is.read(bytes);
                String content = new String(bytes);
                os = socket.getOutputStream();

                Request request = HttpUtil.buildRequest(content);
                Response response = HttpUtil.buildResponse(request);

                if (request.isStatic()) {
                    HttpUtil.buildStaticResponse(request, response);
                } else {
                    HttpUtil.execute(request, response);
                    HttpUtil.buildStaticResponse(request, response);
                }
                os.write(response.toResult().getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    is.close();
                    os.flush();
                    os.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
