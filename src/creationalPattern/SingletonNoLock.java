package creationalPattern;

/**
 * description:无锁的线程安全单例模式
 * create user: songj
 * date : 2020/9/15 21:51
 */
public class SingletonNoLock {
    private static final SingletonNoLock instance = new SingletonNoLock();

    private SingletonNoLock() {
        System.out.println("创建单例模式");
    }

    public static synchronized SingletonNoLock getInstance() {
        return instance;
    }

    public void doSomething() {
        System.out.println("调用单例模式");
    }

    public static void main(String[] args) {
        SingletonNoLock.getInstance().doSomething();
        SingletonNoLock.getInstance().doSomething();
    }
}
