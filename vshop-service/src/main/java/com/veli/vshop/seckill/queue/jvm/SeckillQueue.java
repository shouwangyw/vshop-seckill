package com.veli.vshop.seckill.queue.jvm;

import com.veli.vshop.seckill.dao.entity.TbSeckillOrder;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author yangwei
 */
public class SeckillQueue {
    /**
     * 队列大小
     */
    private static final int MAX_SIZE = 100;
    /**
     * 定义一个队列：用于多线程间下单的队列
     */
    private static final BlockingQueue<TbSeckillOrder> QUEUE = new LinkedBlockingQueue<>(MAX_SIZE);

    private SeckillQueue() {
    }

    /**
     * 静态内部类实现单例
     */
    private static class Holder {
        private static SeckillQueue INSTANCE = new SeckillQueue();
    }

    public static SeckillQueue getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * 生产入队
     * add(e) 队列未满时，返回true；队列满则抛出IllegalStateException(“Queue full”)异常——AbstractQueue
     * put(e) 队列未满时，直接插入没有返回值；队列满时会阻塞等待，一直等到队列未满时再插入。
     * offer(e) 队列未满时，返回true；队列满时返回false。非阻塞立即返回。
     * offer(e, time, unit) 设定等待的时间，如果在指定时间内还不能往队列中插入数据则返回false，插入成功返回true。
     */
    public boolean produce(TbSeckillOrder order) {
        return QUEUE.offer(order);
    }
    /**
     * 消费出队
     * poll() 获取并移除队首元素，在指定的时间内去轮询队列看有没有首元素有则返回，否者超时后返回null
     * take() 与带超时时间的poll类似不同在于take时候如果当前队列空了它会一直等待其他线程调用notEmpty.signal()才会被唤醒
     */
    public TbSeckillOrder consume() throws InterruptedException {
        return QUEUE.take();
    }
    /**
     * 获取队列大小
     */
    public static int getSize() {
        return QUEUE.size();
    }
}
