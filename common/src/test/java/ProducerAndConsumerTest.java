import org.junit.Test;
import pc.Model;
import pc.impl.BlockingQueueModel;
import pc.impl.WaitNotifyModel;

public class ProducerAndConsumerTest {
    @Test
    public void test1(){
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

    @Test
    public void text2(){
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
