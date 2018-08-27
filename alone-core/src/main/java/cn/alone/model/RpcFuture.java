package cn.alone.model;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author RojerAlone
 * @Date 2018-08-12 14:18
 *
 * response 的 future
 */
public class RpcFuture implements Future<RpcResponse> {

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    private RpcResponse response;

    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    public boolean isCancelled() {
        return false;
    }

    public boolean isDone() {
        return false;
    }

    public RpcResponse get() throws InterruptedException {
        countDownLatch.await();
        return response;
    }

    public RpcResponse get(long timeout, TimeUnit unit) throws InterruptedException {
        countDownLatch.await(timeout, unit);
        return response;
    }

    public void done(RpcResponse response) {
        this.response = response;
        countDownLatch.countDown();
    }
}
