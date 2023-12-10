package cn.forbearance.mysql;

/**
 * 生命周期基类
 *
 * @author cristina
 */
public abstract class AbstractBaseLifeCycle implements BaseLifeCycle {

    protected volatile boolean running = false;

    @Override
    public void start() {
        this.running = true;
        doStart();
    }

    @Override
    public void stop() {
        this.running = false;
        doStop();
    }

    @Override
    public boolean isStart() {
        return running;
    }

    /**
     * 开始生命周期
     */
    protected abstract void doStart();

    /**
     * 结束生命周期
     */
    protected abstract void doStop();
}
