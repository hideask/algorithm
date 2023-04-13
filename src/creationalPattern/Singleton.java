package creationalPattern;

/**
 * description: 单例模式（普通）
 * create user: songj
 * date : 2020/9/15 20:34
 */
public class Singleton {
    private static Singleton instance;

    private Singleton() {
        System.out.println("单例模式被创建");
    }

    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

    public void doSomething() {
        System.out.println("调用单例模式");
    }

    public static void main(String[] args) {
        Singleton.getInstance().doSomething();
        Singleton.getInstance().doSomething();
    }
}
