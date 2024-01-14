package com.simplewebserver;

import com.simplewebserver.component.Component;
import com.simplewebserver.exception.LifeCycleException;
import com.simplewebserver.network.endpoint.nio.NioEndpoint;

import java.util.Scanner;

public class Boot extends Component {

//    private Connector connector;

    public static void main(String[] args) {
        NioEndpoint nioEndpoint = new NioEndpoint();
        nioEndpoint.start(8080);
        Scanner scanner = new Scanner(System.in);
        String order;
        while (scanner.hasNext()) {
            order = scanner.next();
            if (order.equals("EXIT")) {
                nioEndpoint.close();
                System.exit(0);
            }
        }
//        Boot boot = new Boot();
//        try {
//            boot.init();
//        } catch (LifeCycleException e) {
//            throw new RuntimeException(e);
//        }
//        try {
//            boot.start();
//        } catch (LifeCycleException e) {
//            throw new RuntimeException(e);
//        }
    }

    @Override
    protected void initInternal() throws LifeCycleException {
        createConnector();
        createProcessor();
    }

    @Override
    protected void startInternal() throws LifeCycleException {
        startConnector();
        startProcessor();
    }

    private void createConnector() throws LifeCycleException {
//        connector = Connector.getInstance();
//        connector.init();

    }

    private void createProcessor() {

    }


    private void startConnector() throws LifeCycleException {
//        connector.start();
    }

    private void startProcessor() {

    }


}
