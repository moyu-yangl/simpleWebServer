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
                while (i == 0) {
                    i = is.available();
                }
                byte[] bytes = new byte[i];
                int read = is.read(bytes);
                String content = new String(bytes);
                os = socket.getOutputStream();
                Request request = HttpUtil.buildRequest(content);
                String path = request.getHead("path");
                File file = new File("src/main/resources" + path);
                boolean flag = true;
                if (!file.exists()) {
                    flag = false;
                    file = new File("src/main/resources" + "/404.html");
                }
                
                BufferedReader reader = new BufferedReader(new FileReader(file));
                StringBuffer sb = new StringBuffer();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\r\n");
                }
                String result = null;
                if (flag) {
                    result = HttpUtil.buildStaticResponse(sb.toString(), file.length(), 200, "ok");
                } else {
                    result = HttpUtil.buildStaticResponse(sb.toString(), file.length(), 404, "not find");
                }
                os.write(result.getBytes());

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
