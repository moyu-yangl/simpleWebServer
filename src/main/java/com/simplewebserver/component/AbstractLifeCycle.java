package com.simplewebserver.component;

import com.simplewebserver.exception.LifeCycleException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


/**
 * <pre>
 * {@link LifeCycle} 的抽象实现
 *
 * 这里用了模板方法模式, 生命周期组件的总体流程由该类实现, 而具体的每个阶段干什么由具体的组件去实现.
 * </pre>
 *
 * @author lixiaohui
 */
public abstract class AbstractLifeCycle implements LifeCycle {

    /**
     * 这里需要保证state的可见性, 防止多个线程并发时出问题
     */
    protected volatile LifeCycleState state = LifeCycleState.NULL;
    private List<LifeCycleListener> lifeCycleListeners = new ArrayList<>();

    public AbstractLifeCycle() {
        addLifecycleListener(new LifeCycleLogger());
    }

    @Override
    public synchronized void init() throws LifeCycleException {
        if (state.compare(LifeCycleState.NULL) != 0) {// 不是初始前状态
            return;
        }
        setState(LifeCycleState.INITIALIZING);
        try {
            initInternal();
        } catch (LifeCycleException e) {
            setState(LifeCycleState.FAILED);
            throw e;
        }
        setState(LifeCycleState.INITIALIZED);
    }

    private void setState(LifeCycleState state, boolean fireEvent) {
        this.state = state;
        if (fireEvent) {
            fireLifecycleEvent(new LifeCycleEvent(this, this, state));
        }
    }

    private void setState(LifeCycleState state) {
        setState(state, true);
    }

    protected abstract void initInternal() throws LifeCycleException;

    @Override
    public synchronized void start() throws LifeCycleException {
        if (state.compare(LifeCycleState.INITIALIZED) != 0) { //不是已初始化
            init();
        }
        if (state.compare(LifeCycleState.INITIALIZED) != 0) {
            return;
        }
        setState(LifeCycleState.STARTING);
        try {
            startInternal();
        } catch (LifeCycleException e) {
            setState(LifeCycleState.FAILED);
            throw e;
        }
        setState(LifeCycleState.STARTED);
    }

    protected abstract void startInternal() throws LifeCycleException;

    @Override
    public synchronized void restart() throws LifeCycleException {
        if (state != LifeCycleState.STARTED) {
            throw new LifeCycleException("Illegal lifecycle state: " + state);
        }
        setState(LifeCycleState.RESTARTING);
        try {
            restartInternal();
        } catch (LifeCycleException e) {
            setState(LifeCycleState.FAILED);
        }
        setState(LifeCycleState.RESTARTED);
        setState(LifeCycleState.STARTED, false);
    }

    protected abstract void restartInternal() throws LifeCycleException;

    @Override
    public synchronized void destroy() throws LifeCycleException {
        if (state.compare(LifeCycleState.STARTED) != 0) { //不是已初始化
            return;
        }
        setState(LifeCycleState.DESTROYING);
        try {
            startInternal();
        } catch (LifeCycleException e) {
            setState(LifeCycleState.FAILED);
            throw e;
        }
        setState(LifeCycleState.DESTROYED);
    }

    protected abstract void destroyInternal() throws LifeCycleException;


    private void fireLifecycleEvent(LifeCycleEvent e) {
        fireLifecycleEvent(e, false);
    }

    private void fireLifecycleEvent0(LifeCycleEvent e) {
        for (LifeCycleListener l : lifeCycleListeners) {
            l.lifeCycleEvent(e);
        }
    }

    protected void fireLifecycleEvent(LifeCycleEvent e, boolean asyc) {
        if (!asyc) {
            fireLifecycleEvent0(e);
        } else { // 异步通知
            new Thread(new Runnable() {
                @Override
                public void run() {
                    fireLifecycleEvent0(e);
                }
            }).start();
        }
    }

    public void addLifecycleListener(LifeCycleListener l) {
        lifeCycleListeners.add(l);
    }

    public void removeLifecycleListener(LifeCycleListener l) {
        lifeCycleListeners.remove(l);
    }

    // 日志打印, 可忽略
    public class LifeCycleLogger implements LifeCycleListener {

        private final Logger logger = Logger.getLogger(String.valueOf(LifeCycleLogger.class));

        @Override
        public void lifeCycleEvent(LifeCycleEvent e) {
            logger.info(e.getState().name());
        }
    }
}