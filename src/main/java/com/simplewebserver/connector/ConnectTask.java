//package com.simplewebserver.connector;
//
//import com.simplewebserver.util.HttpUtil;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.net.Socket;
//
//public class ConnectTask implements Runnable {
//    private Socket socket;
//
//    public ConnectTask(Socket socket) {
//        this.socket = socket;
//    }
//
//    @Override
//    public void run() {
//        InputStream is = null;
//        OutputStream os = null;
//        try {
//            is = socket.getInputStream();
//            int i = is.available();
//            while (i == 0) {
//                i = is.available();
//            }
//            byte[] bytes = new byte[i];
//            int read = is.read(bytes);
//            String content = new String(bytes);
//            os = socket.getOutputStream();
//
//            ServerRequest0 request = (ServerRequest0) HttpUtil.buildRequest(content);
//            ServerResponse0 response = (ServerResponse0) HttpUtil.buildResponse(request);
//            response.enableCors();
//            if (request.isStatic()) {
//                HttpUtil.buildStaticResponse(request, response);
//            }
//            os.write(response.toResult().getBytes());
//            os.flush();
//        } catch (Exception e) {
//            e.printStackTrace();
//            try {
//                os.write(HttpUtil.errorRequest().getBytes());
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        } finally {
//            try {
//                is.close();
//                os.close();
//                socket.close();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
//
//}
