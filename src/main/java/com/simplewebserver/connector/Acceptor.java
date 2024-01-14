//package com.simplewebserver.connector;
//
//import com.simplewebserver.component.Component;
//import com.simplewebserver.exception.LifeCycleException;
//
//import java.net.ServerSocket;
//import java.net.Socket;
//
//public class Acceptor extends Component {
//    private volatile static Acceptor acceptor;
//    protected volatile boolean closeFlag = false;
//    protected Executor executor;
//
//    private Thread worker;
//
//    private Acceptor() {
//
//    }
//
//    public static Acceptor getInstance() {
//        if (acceptor == null) {
//            synchronized (Acceptor.class) {
//                if (acceptor == null) {
//                    acceptor = new Acceptor();
//                }
//            }
//        }
//        return acceptor;
//    }
//
//    @Override
//    protected void initInternal() throws LifeCycleException {
//        worker = new Thread(() -> {
//            try (
//                    ServerSocket server = new ServerSocket(8080)
//            ) {
//                for (; ; ) {
//                    if (closeFlag && !worker.isInterrupted()) {
//                        break;
//                    }
//                    Socket accept = server.accept();
//                    ConnectTask requestTask = new ConnectTask(accept);
//                    executor.execute(requestTask);
//                }
//            } catch (Exception e) {
////                System.out.println(e.getMessage());
//                e.printStackTrace();
//            }
//        }, "[SimpleServer-Acceptor]");
//    }
//
//    @Override
//    protected void startInternal() throws LifeCycleException {
//        run();
//    }
//
//    public void setExecutor(Executor executor) {
//        this.executor = executor;
//    }
//
//    public void run() {
//        worker.start();
//    }
//
////    @Override
////    protected void restartInternal() throws LifeCycleException {
////        super.restartInternal();
////    }
//
//    @Override
//    protected void destroyInternal() throws LifeCycleException {
//        if (worker.isAlive()) {
//            closeFlag = true;
//            worker.interrupt();
//        }
//    }
//}
