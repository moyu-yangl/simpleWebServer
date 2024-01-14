package com.simplewebserver.component;


import java.util.EventListener;

public interface LifeCycleListener extends EventListener {
    void lifeCycleEvent(LifeCycleEvent e);

}
