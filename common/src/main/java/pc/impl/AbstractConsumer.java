package pc.impl;

import pc.Consumer;

/**
 * 消费者抽象类
 */
public abstract class AbstractConsumer implements Consumer,Runnable{
    public void run(){
        while (true){
            try{
                consume();
            }catch (InterruptedException e){
                e.printStackTrace();
                break;
            }
        }
    }
}
