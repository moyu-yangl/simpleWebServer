package com.simplewebserver;

import com.simplewebserver.component.Component;
import com.simplewebserver.exception.LifecycleException;

public class Boot implements Component {

    public static void main(String[] args) {
        Boot boot = new Boot();
        try {
            boot.init();
        } catch (LifecycleException e) {
            throw new RuntimeException(e);
        }
        try {
            boot.start();
        } catch (LifecycleException e) {
            throw new RuntimeException(e);
        }
    }


    private void createConnector() {

    }

    private void createProcessor() {

    }


    private void startConnector() {

    }

    private void startProcessor() {

    }

    @Override
    public void start() throws LifecycleException {
        Component.super.start();
    }

    @Override
    public void init() throws LifecycleException {
        Component.super.init();
    }


    @Override
    public void close() throws LifecycleException {

    }
    
}
