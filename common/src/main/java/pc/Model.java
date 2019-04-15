package pc;

/**
 * 为模型定义抽象工厂方法
 */
public interface Model {
    Runnable newRunnableConsumer();
    Runnable newRunnableProducer();
}
