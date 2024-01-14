package com.simplewebserver.component;

import com.simplewebserver.exception.LifeCycleException;

// 默认实现所有的生命周期方法
public class Component extends AbstractLifeCycle {

    @Override
    protected void initInternal() throws LifeCycleException {

    }

    @Override
    protected void startInternal() throws LifeCycleException {

    }

    @Override
    protected void restartInternal() throws LifeCycleException {

    }

    @Override
    protected void destroyInternal() throws LifeCycleException {

    }
}
