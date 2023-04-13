package JUC;

/**
 * 题⽬：三个售票员 卖出 30张票
 */
class Ticket {//资源类
    //票
    private int number = 30;

    public synchronized void saleTicket() {
        if (number > 0) {
            System.out.println(Thread.currentThread().getName() + "卖出第：" + (number--) + "还剩下：" + number);
        }
    }
}

public class SaleTicketDemo {
    public static void main(String[] args) {
        Ticket ticket = new Ticket();
        new Thread(() -> {
            for (int i = 1; i <= 30; i++)
                ticket.saleTicket();
        }, "A").start();
        new Thread(() -> {
            for (int i = 1; i <= 30; i++)
                ticket.saleTicket();
        }, "B").start();
        new Thread(() -> {
            for (int i = 1; i <= 30; i++)
                ticket.saleTicket();
        }, "C").start();
    }
}
