package cn.forbearance.mysql;

import cn.forbearance.mysql.executor.QueryExecutor;
import cn.forbearance.mysql.executor.UpdateExecutor;
import cn.forbearance.mysql.protocol.socket.SocketChannel;
import cn.forbearance.utils.LifeCycleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.LRUCache;

import java.lang.reflect.Constructor;

/**
 * @author cristina
 */
public class MysqlConnection extends AbstractBaseLifeCycle {

    private static final Logger log = LoggerFactory.getLogger(MysqlConnection.class);

    private SocketChannel channel;
    private Long threadId;
    private final Integer cacheSize = 10;

    /**
     * LRU：Least Recently Used（最少最近使用）
     */
    LRUCache<Object, Object> executorUpdateCache;
    LRUCache<Object, Object> executorQueryCache;

    public MysqlConnection(SocketChannel channel, Long threadId) {
        this.channel = channel;
        this.threadId = threadId;
    }

    public UpdateExecutor getUpdateExecutor() {
        Class<?> clazz = null;
        try {
            clazz = Class.forName(UpdateExecutor.class.getName());
            Constructor<?> constructor = clazz.getDeclaredConstructor(MysqlConnection.class);
            constructor.setAccessible(true);
            return (UpdateExecutor) constructor.newInstance(this);
        } catch (Exception e) {
            log.error(String.format("获取更新执行器失败 %s", e.getMessage()));
            throw new RuntimeException();
        }
    }

    public QueryExecutor getQueryExecutor() {
        Class<?> clazz = null;
        try {
            clazz = Class.forName(QueryExecutor.class.getName());
            Constructor<?> constructor = clazz.getDeclaredConstructor(MysqlConnection.class);
            constructor.setAccessible(true);
            return (QueryExecutor) constructor.newInstance(this);
        } catch (Exception e) {
            log.error(String.format("获取查询执行器失败 %s", e.getMessage()));
            throw new RuntimeException();
        }
    }

    @Override
    protected void doStart() {
        if (channel.isConnected()) {
            log.info("connection start ...");
        } else {
            throw new LifeCycleException("channel not open");
        }
    }

    @Override
    protected void doStop() {
        if (channel != null) {
            channel.close();
        }
    }

    public SocketChannel getChannel() {
        return channel;
    }

    public Long getThreadId() {
        return threadId;
    }

    public void setChannel(SocketChannel channel) {
        this.channel = channel;
    }

    public void setThreadId(Long threadId) {
        this.threadId = threadId;
    }
}
