package creationalPattern;

/**
 * description:双重校验
 * create user: songj
 * date : 2020/9/15 21:40
 */
public class SingletonByLockDoubleCheck {
    private static SingletonByLockDoubleCheck instance;

    private SingletonByLockDoubleCheck() {
        System.out.println("单例模式被创建");
    }

    public static synchronized SingletonByLockDoubleCheck getInstance() throws InterruptedException {
        if (instance == null) {
            synchronized (SingletonByLockDoubleCheck.class) {
                System.out.println("线程" + Thread.currentThread().getName() + "执行中。。。");
                Thread.sleep(2000);
                if (instance == null) {
                    instance = new SingletonByLockDoubleCheck();
                }
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
                    SingletonByLockDoubleCheck.getInstance().doSomething();
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
                    SingletonByLockDoubleCheck.getInstance().doSomething();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t2");
        t1.start();
        t2.start();
    }
}
