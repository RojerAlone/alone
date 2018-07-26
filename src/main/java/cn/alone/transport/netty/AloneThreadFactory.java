package cn.alone.transport.netty;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by rojeralone on 2018-07-25
 */
public class AloneThreadFactory implements ThreadFactory {

    private static final String THREAD_PREFIX = "alone-pool-thread-";

    private static final AtomicLong THREAD_ID = new AtomicLong(1);

    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setName(THREAD_PREFIX + THREAD_ID.getAndAdd(1L));
        return thread;
    }

}
