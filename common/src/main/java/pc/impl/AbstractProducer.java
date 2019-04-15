package pc.impl;

import pc.Producer;

/**
 * 生产者抽象类
 */
public abstract class AbstractProducer implements Producer,Runnable{
    public void run(){
        while (true){
            try {
                produce();
            }catch (InterruptedException e){
                e.printStackTrace();
                break;
            }
        }
    }
}
