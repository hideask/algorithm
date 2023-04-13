package JUC;

import java.util.concurrent.TimeUnit;

/**
 * description:LockTest
 * create user: songj
 * date : 2021/1/6 14:36
 */

/**
 * 1. 标准访问，请问先打印邮件还是短信？ 邮件
 * 2. 邮件⽅法暂停4秒钟，请问先打印邮件还是短信？ 邮件
 * 对象锁
 * ⼀个对象⾥⾯如果有多个synchronized⽅法，某⼀个时刻内，只要⼀个线程去调⽤其中
 * 的⼀个synchronized⽅法了，
 * 其他的线程都只能等待，换句话说，某⼀个时刻内，只能有唯⼀⼀个线程去访问这些
 * synchronized⽅法，
 * 锁的是当前对象this，被锁定后，其他的线程都不能进⼊到当前对象的其他的
 * synchronized⽅法
 * 3. 新增⼀个普通⽅法hello（），请问先打印邮件还是hello？ hello
 * 加个普通⽅法后发现和同步锁⽆关
 * 4. 两部⼿机，请问先打印邮件还是短信？ hello
 * 换成两个对象后，不是同⼀把锁了，情况⽴刻变化
 * 5. 两个静态同步⽅法，同⼀部⼿机，请问先打印邮件还是短信？ 邮件
 * 6. 两个静态同步⽅法，2部⼿机，请问先打印邮件还是短信？ 邮件
 * 全局锁
 * synchronized实现同步的基础：java中的每⼀个对象都可以作为锁。
 * 具体表现为⼀下3中形式。
 * 对于普通同步⽅法，锁是当前实例对象，锁的是当前对象this，
 * 对于同步⽅法块，锁的是synchronized括号⾥配置的对象。
 * ⼈⼯窗⼝排队购票(回顾)
 * 对于静态同步⽅法，锁是当前类的class对象
 * 7. 1个普通同步⽅法，1个静态同步⽅法，1部⼿机，请问先打印邮件还是短信？ 短信
 * 8. 1个普通同步⽅法，1个静态同步⽅法，2部⼿机，请问先打印邮件还是短信？ 短信
 * 当⼀个线程试图访问同步代码块时，它⾸先必须得到锁，退出或抛出异常时必须释放锁。
 * 也就是说如果⼀个实例对象的普通同步⽅法获取锁后，该实例对象的其他普通同步⽅法必
 * 须等待获取锁的⽅法释放锁后才能获取锁，
 * 可是别的实例对象的普通同步⽅法因为跟该实例对象的普通同步⽅法⽤的是不同的锁，
 * 所以⽆需等待该实例对象已获取锁的普通同步⽅法释放锁就可以获取他们⾃⼰的锁。
 * <p>
 * 所有的静态同步⽅法⽤的也是同⼀把锁--类对象本身，
 * 这两把锁(this/class)是两个不同的对象，所以静态同步⽅法与⾮静态同步⽅法之间是
 * 不会有静态条件的。
 * 但是⼀旦⼀个静态同步⽅法获取锁后，其他的静态同步⽅法都必须等待该⽅法释放锁后才
 * 能获取锁，
 * ⽽不管是同⼀个实例对象的静态同步⽅法之间，
 * 还是不同的实例对象的静态同步⽅法之间，只要它们同⼀个类的实例对象
 */
class Phone {
    public static synchronized void sendEmail() {
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (Exception e) {

        }
        System.out.println("发送邮件");
    }

    public synchronized void sendMsg() {
        System.out.println("发送短信");
    }

    public void hello() {
        System.out.println("hello");
    }
}

public class LockTest {
    public static void main(String[] args) {
        Phone phone = new Phone();
        Phone phone1 = new Phone();
        new Thread(() -> {
            phone.sendEmail();
        }, "A").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {

        }

        new Thread(() -> {
            phone1.sendMsg();
        }, "B").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {

        }

        new Thread(() -> {
            phone.hello();
        }, "B").start();
    }
}
