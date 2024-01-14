package com.simplewebserver.component;

import com.simplewebserver.exception.LifeCycleException;

public interface LifeCycle {
    void restart() throws LifeCycleException;

    void destroy() throws LifeCycleException;

    void start() throws LifeCycleException;

    void init() throws LifeCycleException;


    void addLifecycleListener(LifeCycleListener listener);

}
