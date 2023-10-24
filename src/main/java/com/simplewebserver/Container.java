package com.simplewebserver;

import com.simplewebserver.annotation.ServerAnnotation;
import com.simplewebserver.domain.ServerRequest;
import com.simplewebserver.domain.ServerResponse;
import com.simplewebserver.util.HttpUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class Container {

    private static final Map<String, Class> classMap;

    static {
        try {
            classMap = loadAllServer("com.simplewebserver.server");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerSocket server = new ServerSocket(8080);
        for (; ; ) {
            Socket accept = server.accept();
            RequestTask requestTask = new RequestTask(accept);
            requestTask.start();
        }

    }

    public static Map<String, Class> loadAllServer(String path) throws IOException, ClassNotFoundException {
        Map<String, Class> map = new HashMap<>();
        path = path.replaceAll("\\.", "/");
        Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources(path);
        while (resources.hasMoreElements()) {
            URL url = resources.nextElement();
            File file = new File(url.getFile());
            if (file.isDirectory()) {
                for (File f : file.listFiles()) {
                    if (f.getName().endsWith(".class")) {
                        String name = f.getAbsolutePath().replaceAll("\\\\", "/");
                        String substring = name.substring(name.lastIndexOf(path));
                        String clazz = substring.replace(".class", "").replaceAll("/", ".");
                        Class<?> aClass = Class.forName(clazz);
                        ServerAnnotation annotation = aClass.getAnnotation(ServerAnnotation.class);
                        String s = null;
                        if (annotation != null) {
                            if (!map.containsKey(annotation.value()))
                                map.put(annotation.value(), aClass);
                        }
                    }
                }
            }
        }
        return map;
    }

    public static Object handlerMapping(Map<String, Class> mapping, String path) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Class clazz = mapping.get(path);
        Object o = clazz.newInstance();
        return o;
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

                ServerRequest request = (ServerRequest) HttpUtil.buildRequest(content);
                ServerResponse response = (ServerResponse) HttpUtil.buildResponse(request);
                response.enableCors();
                if (request.isStatic()) {
                    HttpUtil.buildStaticResponse(request, response);
                } else {
                    Object o = handlerMapping(classMap, request.getHead("path"));
                    HttpUtil.execute(o, request, response);
//                    HttpUtil.buildStaticResponse(request, response);
                }
                os.write(response.toResult().getBytes());
            } catch (IOException | NoSuchMethodException | InstantiationException | IllegalAccessException |
                     InvocationTargetException e) {
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
