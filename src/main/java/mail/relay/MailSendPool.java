package mail.relay;

import java.util.concurrent.*;

// 邮件发送池类

class MailSendPool {
    private final ExecutorService executor;

    public MailSendPool(int corePoolSize, int maxPoolSize, long keepAliveTime, int queueCapacity) {
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(queueCapacity);

        /*
            corePoolSize：核心线程池大小，即线程池中最少的线程数。
            maxPoolSize：最大线程池大小，即线程池中最多的线程数。
            keepAliveTime：线程池中空闲线程的存活时间。
            queueCapacity：任务队列容量，它决定了当任务数量大于线程池最大线程数时，需要存储到任务队列里等待执行的任务数量。
            在当前的代码片段中，设置了线程池的拒绝策略为 ThreadPoolExecutor.CallerRunsPolicy()，它会将任务交回给调用线程来执行，这样可以避免任务被拒绝且不会阻塞后续的代码执行。

            这里提供了一组默认参数，您可以根据实际需求进行修改：
            int corePoolSize = 10;
            int maxPoolSize = 20;
            long keepAliveTime = 60L;
            int queueCapacity = 100;
            MailSendPool mailSendPool = new MailSendPool(corePoolSize, maxPoolSize, keepAliveTime, queueCapacity);
         */
        this.executor = new ThreadPoolExecutor(
                corePoolSize,
                maxPoolSize,
                keepAliveTime,
                TimeUnit.SECONDS,
                workQueue,
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
    }

    public void addMail(String host, int port, String username, String password, String from, String to, String subject, String content) {
        Runnable task = new MailSendTask(host, port, username, password, from, to, subject, content);
        executor.submit(task);
    }

    public void shutdown() {
        executor.shutdown();
    }
}