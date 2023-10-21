package com.simplewebserver;

import com.simplewebserver.domain.Request;
import com.simplewebserver.util.HttpUtil;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Container {
    public static void main(String[] args) throws IOException, InterruptedException {
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
                byte[] bytes = new byte[i];
                int read = is.read(bytes);
                System.out.println(read);
                String content = new String(bytes);
                Request request = HttpUtil.requestBuild(content);
                os = socket.getOutputStream();
                File file = new File("src/main/resources/index.html");
                BufferedReader reader = new BufferedReader(new FileReader(file));
                StringBuffer sb = new StringBuffer();
                String line = null;

                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\r\n");
                }

                StringBuffer result = new StringBuffer();
                result.append("HTTP/1.1 200 ok \r\n");
                result.append("Content-Language: zh-CN \r\n");
                result.append("Content-Type: text/html;charset=UTF-8 \r\n");
                result.append("Content-Length: " + file.length() + "\r\n");
                result.append("\r\n" + sb.toString());
                os.write(result.toString().getBytes());

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
