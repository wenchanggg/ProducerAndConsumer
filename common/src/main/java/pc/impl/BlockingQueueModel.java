package pc.impl;

import pc.Consumer;
import pc.Model;
import pc.Producer;
import pojo.Task;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 实现一：写法简单，核心思想：把并发和容量控制封装在缓冲区内
 */
public class BlockingQueueModel implements Model {
    private final BlockingQueue<Task> queue;
    private final AtomicInteger integerTackNo = new AtomicInteger();
    /**
     * 构造方法
     * @param cap
     */
    public BlockingQueueModel(int cap) {
        this.queue = new LinkedBlockingQueue<Task>(cap);
    }

    /**
     * 创建消费者Runnable
     * @return
     */
    public Runnable newRunnableConsumer() {
        return new ConsumerImpl();
    }

    /**
     * 创建生产者Runnable
     * @return
     */
    public Runnable newRunnableProducer() {
        return new ProducerImple();
    }

    // 生产者消费者实现类:
    private class ProducerImple extends AbstractProducer implements Producer,Runnable{

        public void produce() throws InterruptedException {
            // 不定期生产，模拟随机的用户请求
            Thread.sleep((long)(Math.random()*1000));
            Task task = new Task(integerTackNo.getAndIncrement());
            queue.put(task);
            System.out.println("Produce:"+task.no);
        }
    }

    private class ConsumerImpl extends AbstractConsumer implements Consumer,Runnable{

        public void consume() throws InterruptedException {
            Task task = queue.take();
            // 固定时间范围的消费，模拟相对稳定的服务处理过程
            Thread.sleep( 500+ (long)(Math.random()*500));
            System.out.println("Consume:"+task.no);
        }
    }

    /**
     * 测试
     * @param args
     */
    public static void main(String[] args){
        Model model = new BlockingQueueModel(3);
        // 两个消费者
        for (int i=0;i<2;i++){
            new Thread(model.newRunnableConsumer()).start();
        }
        // 五个生产者
        for (int i=0;i<5;i++){
            new Thread(model.newRunnableProducer()).start();
        }
    }
}
