package creationalPattern;

import java.util.Arrays;

/**
 * description: 同步锁单例模式
 * create user: songj
 * date : 2020/9/15 21:05
 */
public class SingletonByLock {
    private static SingletonByLock instance;

    private SingletonByLock() {
        System.out.println("单例模式被创建");
    }

    public static synchronized SingletonByLock getInstance() throws InterruptedException {
        synchronized (SingletonByLock.class) {
            System.out.println("线程" + Thread.currentThread().getName() + "执行中。。。");
            Thread.sleep(2000);
            if (instance == null) {
                instance = new SingletonByLock();
            }
        }

        return instance;
    }

    public void doSomething() {
        System.out.println("调用单例模式");
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("执行线程1");
                    SingletonByLock.getInstance().doSomething();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t1");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("执行线程2");
                    SingletonByLock.getInstance().doSomething();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t2");
        t1.start();
        t2.start();
    }
}
