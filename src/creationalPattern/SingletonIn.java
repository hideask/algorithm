package creationalPattern;

/**
 * description:静态内部类模式
 * create user: songj
 * date : 2020/9/15 22:10
 */
public class SingletonIn {
    private SingletonIn() {
        System.out.println("创建单例模式");
    }

    private static class Singleton {
        static SingletonIn singleton = new SingletonIn();
    }

    public static SingletonIn getInstance() {
        return Singleton.singleton;
    }

    public void doSomething() {
        System.out.println("调用单例模式");
    }

    public static void main(String[] args) {
        SingletonIn.getInstance().doSomething();
        SingletonIn.getInstance().doSomething();
    }
}
