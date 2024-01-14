package com.simplewebserver;

import com.simplewebserver.annotation.ServerAnnotation;
import com.simplewebserver.domain.Servlet;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class ServerBoot {

    public static final Map<String, Class> classMap;
//    private static final ThreadPoolExecutor HTTP_SERVER_POOL;

    static {
        try {
            classMap = loadAllServer("com.simplewebserver.server");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
//        HTTP_SERVER_POOL = new ThreadPoolExecutor(
//                20, 200, 10L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(200)
//        );
    }

//    public static void main(String[] args) throws IOException {
//        ServerSocket server = new ServerSocket(8080);
//        for (; ; ) {
//            Socket accept = server.accept();
//            ServerBoot.RequestTask requestTask = new ServerBoot.RequestTask(accept);
//            HTTP_SERVER_POOL.execute(requestTask);
//        }
//
//    }

    /**
     * 加载指定路径下的所有服务，要求：服务在路径下且拥有 {@ServerAnnotation } 注解
     *
     * @param path 加载路径
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
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

    /**
     * 请求路径映射后执行
     * 根据请求的路径，拿到要执行的服务，执行它的doGet方法
     *
     * @param mapping 映射
     * @param path    请求路径
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static Servlet handlerMapping(Map<String, Class> mapping, String path) throws InstantiationException, IllegalAccessException {
        Object o = null;
        Class clazz = mapping.get(path);
        if (clazz != null) {
            o = clazz.newInstance();
        }
        return (Servlet) o;
    }

    /**
     * 一次HTTP请求的执行过程
     */
//    public static class RequestTask implements Runnable {
//        private Socket socket;
//
//        public RequestTask(Socket socket) {
//            this.socket = socket;
//        }
//
//        @Override
//        public void run() {
//            InputStream is = null;
//            OutputStream os = null;
//            try {
//                is = socket.getInputStream();
//                int i = is.available();
//                while (i == 0) {
//                    i = is.available();
//                }
//                byte[] bytes = new byte[i];
//                int read = is.read(bytes);
//                String content = new String(bytes);
//                os = socket.getOutputStream();
//
//                ServerRequest0 request = (ServerRequest0) HttpUtil.buildRequest(content);
//                ServerResponse0 response = (ServerResponse0) HttpUtil.buildResponse(request);
//                response.enableCors();
//                if (request.isStatic()) {
//                    HttpUtil.buildStaticResponse(request, response);
//                } else {
//                    Object o = handlerMapping(classMap, request.getHead("path"));
//                    HttpUtil.execute(o, request, response);
//                }
//                os.write(response.toResult().getBytes());
//            } catch (Exception e) {
//                try {
//                    os.write(HttpUtil.errorRequest().getBytes());
//                } catch (IOException ex) {
//                    System.out.println(ex.getCause());
//                }
//            } finally {
//                try {
//                    is.close();
//                    os.flush();
//                    os.close();
//                    socket.close();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }
//    }

//    public static class ManyRequestTask implements Runnable {
//        private Socket socket;
//
//        public ManyRequestTask(Socket socket) {
//            this.socket = socket;
//        }
//
//        @Override
//        public void run() {
//            InputStream is = null;
//            OutputStream os = null;
//            try {
//                int count = 0;
//                while (true) {
//                    is = socket.getInputStream();
//                    int i = is.available();
//                    if (i == 0) {
//                        Thread.sleep(100L);
//                        count++;
//                        continue;
//                    }
//                    byte[] bytes = new byte[i];
//                    int read = is.read(bytes);
//                    String content = new String(bytes);
//                    os = socket.getOutputStream();
//
//                    ServerRequest0 request = (ServerRequest0) HttpUtil.buildRequest(content);
//                    ServerResponse0 response = (ServerResponse0) HttpUtil.buildResponse(request);
//                    response.enableCors();
//
//                    if (!request.getHead("Connection").equals("keep-alive")) {
//                        count = 200;
//                    }
//                    if (request.isStatic()) {
//                        HttpUtil.buildStaticResponse(request, response);
//                    } else {
//                        Object o = handlerMapping(classMap, request.getHead("path"));
//                        HttpUtil.execute(o, request, response);
//                    }
//                    os.write(response.toResult().getBytes());
//                    os.flush();
//                    if (count >= 200)
//                        break;
//                }
//            } catch (Exception e) {
//                try {
//                    os.write(HttpUtil.errorRequest().getBytes());
//                } catch (IOException ex) {
//                    System.out.println(ex.getCause());
//                }
//            } finally {
//                try {
//                    is.close();
//                    os.flush();
//                    os.close();
//                    socket.close();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }
//    }

}
