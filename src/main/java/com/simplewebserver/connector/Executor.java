//package com.simplewebserver.connector;
//
//import com.simplewebserver.component.Component;
//import com.simplewebserver.exception.LifeCycleException;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.concurrent.ArrayBlockingQueue;
//import java.util.concurrent.ThreadPoolExecutor;
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.atomic.AtomicInteger;
//
//public class Executor extends Component {
//    private static volatile Executor executorInstance;
//    private java.util.concurrent.Executor executor;
//    private int corePoolSize;
//    private int maximumPoolSize;
//    private int keepAliveTime;
//    private int queueSize;
//    private AtomicInteger ct;
//
//    public static Executor getInstance() {
//        if (executorInstance == null) {
//            synchronized (Executor.class) {
//                if (executorInstance == null) {
//                    executorInstance = new Executor();
//                }
//            }
//        }
//        return executorInstance;
//    }
//
//    @Override
//    protected void initInternal() throws LifeCycleException {
//        loadConfig("");
//        ct = new AtomicInteger(0);
//    }
//
//    public void execute(ConnectTask task) {
//        executor.execute(task);
//    }
//
//    private void loadConfig(String path) {
//        File config = new File(path);
//        if (!config.exists()) {
//            corePoolSize = 24;
//            maximumPoolSize = 200;
//            keepAliveTime = 60;
//            queueSize = 500;
//        } else {
//            Map<String, Integer> kvMap = new HashMap<>();
//            try (
//                    FileInputStream fis = new FileInputStream(config);
//            ) {
//                int available = fis.available();
//                byte[] bytes = new byte[available];
//                String configFile = new String(bytes);
//                String[] split = configFile.split("\n");
//                for (String s : split) {
//                    String[] kv = s.split(":");
//                    kvMap.put(kv[0], Integer.valueOf(kv[1]));
//                }
//                corePoolSize = kvMap.get("coreSize");
//                maximumPoolSize = kvMap.get("maxSize");
//                keepAliveTime = kvMap.get("keepAlive");
//                queueSize = kvMap.get("queueSize");
//
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//
//        }
//    }
//
//    @Override
//    protected void startInternal() throws LifeCycleException {
//        executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
//                keepAliveTime, TimeUnit.SECONDS,
//                new ArrayBlockingQueue<>(queueSize), r -> new Thread(r, "[SimpleServer]-" + ct.getAndIncrement()));
//    }
//
//    @Override
//    protected void restartInternal() throws LifeCycleException {
//        init();
//    }
//
//    @Override
//    protected void destroyInternal() throws LifeCycleException {
//        if (executor != null) {
//            ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executor;
//            threadPoolExecutor.shutdown();
//        }
//
//    }
//}
