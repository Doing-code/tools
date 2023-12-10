package cn.forbearance.mysql;

/**
 * 生命周期接口
 *
 * @author cristina
 */
public interface BaseLifeCycle {

    /**
     * 开始生命周期
     *
     * @throws Exception
     */
    void start() throws Exception;

    /**
     * 结束生命周期
     */
    public void stop();

    /**
     * 是否启动了生命周期
     *
     * @return
     */
    public boolean isStart();
}
