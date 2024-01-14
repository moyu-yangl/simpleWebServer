//package com.simplewebserver.connector;
//
//import com.simplewebserver.component.Component;
//import com.simplewebserver.exception.LifeCycleException;
//
//public class Connector extends Component {
//    private static Connector connector;
//    private Acceptor acceptor;
//    private Executor executor;
//
//    private Connector() {
//
//    }
//
//    public static Connector getInstance() {
//        if (connector == null) {
//            synchronized (Connector.class) {
//                if (connector == null) {
//                    connector = new Connector();
//                }
//            }
//        }
//        return connector;
//    }
//
//    public void createAcceptor() {
//        acceptor = Acceptor.getInstance();
//    }
//
//    public void createExecutor() {
//        executor = Executor.getInstance();
//    }
//
//    @Override
//    protected void initInternal() throws LifeCycleException {
//        // todo 维护自身成员
//        createAcceptor();
//        createExecutor();
//        acceptor.setExecutor(executor);
//    }
//
//    @Override
//    protected void startInternal() throws LifeCycleException {
//        acceptor.start();
//        executor.start();
//    }
//
//    @Override
//    protected void restartInternal() throws LifeCycleException {
//        super.restartInternal();
//    }
//
//    @Override
//    protected void destroyInternal() throws LifeCycleException {
//        acceptor.destroy();
//        executor.destroy();
//    }
//}
