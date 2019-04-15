package pc.impl;

import pc.Consumer;
import pc.Model;
import pc.Producer;
import pojo.Task;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 实现二：并发与容量的控制不能封装到缓冲区内，由生产者与消费者控制；使用朴素的 wait & notify 机制完成
 */
public class WaitNotifyModel implements Model {
    private final Object BUFFER_LOCK = new Object();
    private final Queue<Task> buffer = new LinkedList<Task>();
    private final int cap;
    private final AtomicInteger integer = new AtomicInteger();
    public WaitNotifyModel(int cap){
        this.cap = cap;
    }

    public Runnable newRunnableConsumer() {
        return new ConsumerImpl();
    }

    public Runnable newRunnableProducer() {
        return new ProducerImpl();
    }

    private class ConsumerImpl extends AbstractConsumer implements Consumer,Runnable{

        public void consume() throws InterruptedException {
            synchronized (BUFFER_LOCK){ // 加锁
                while (buffer.size()==-0){
                    BUFFER_LOCK.wait();
                }
                Task task = buffer.poll();
                // 固定时间范围的消费，模拟相对稳定的服务器处理过程
                Thread.sleep(500+(long) (Math.random()*1000));
                System.out.println("Consume:"+task.no);
                BUFFER_LOCK.notifyAll();  // 释放锁
            }
        }
    }

    private class ProducerImpl extends AbstractProducer implements Producer,Runnable{

        public void produce() throws InterruptedException {
            // 不定期生产，模拟随机的用户请求
            Thread.sleep((long) (Math.random()*1000));
            synchronized (BUFFER_LOCK){ // 加锁
                while (buffer.size()== cap){
                    BUFFER_LOCK.wait();
                }
                Task task = new Task(integer.getAndIncrement());
                buffer.offer(task);
                System.out.println("Produce:"+task.no);
                BUFFER_LOCK.notifyAll();  // 释放锁
            }
        }
    }

    public static void main(String[] args){
        Model model = new WaitNotifyModel(3);
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
